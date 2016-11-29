import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

class Q1Parser extends DefaultHandler {
    private String authorToSearch;
    private ArrayList<String> tags;
    private ArrayList<String> relevant;
    private Publication pub = null;
    private HashMap<String, Author> authors;
    private char queryType;
    private static ArrayList<Publication> result = new ArrayList<>();

    private StringBuilder contentBuf = new StringBuilder();
    private boolean bufupdate;
    private String content;

    private Q1Parser(String author, HashMap<String, Author> authors) {
        queryType = 'A';
        this.authorToSearch = author;
        this.authors = authors;
    }

    private Q1Parser(ArrayList<String> tags, HashMap<String, Author> authors) {
        queryType = 'T';
        this.tags = tags;
        this.authors = authors;
    }

    public void startDocument() throws SAXException {
        if (queryType == 'A') {
            relevant = this.relevantAuthors(authorToSearch);
        }
    }

    public void endDocument() throws SAXException {
    }

    public void startElement(String uri, String localName,
                             String qName, Attributes atts) throws SAXException {
        if (qName.equals("article") || qName.equals("inproceedings") ||
            qName.equals("proceedings") || qName.equals("book") ||
            qName.equals("incollection") || qName.equals("phdthesis") ||
            qName.equals("mastersthesis")) {
            pub = new Publication(qName, atts.getValue("key")); 
        } else if (pub != null) {
            if (qName.equals("pages") || qName.equals("title") ||
                qName.equals("year") || qName.equals("volume") ||
                qName.equals("volume") || qName.equals("journal") ||
                qName.equals("booktitle") || qName.equals("url") ||
                qName.equals("author")) {
                this.bufupdate = true;
            }
        }
    }

    /* TODO: Add Author object instead of String in publication */
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        if (pub != null) {
            content = contentBuf.toString();
            if (qName.equals("pages")) {
                pub.pages = content;
            } else if (qName.equals("title")) {
                pub.title = content;
                if (this.queryType == 'T') {
                    int sim = pub.getSimilarity(tags);
                    if (sim >= 2) {
                        Q1Parser.result.add(pub);
                        pub.relevance = sim;
                    }
                }
            } else if (qName.equals("year")) {
                pub.year = Integer.valueOf(content);
            } else if (qName.equals("volume")) {
                pub.volume = content;
            } else if (qName.equals("journal") || qName.equals("booktitle")) {
                pub.journal_book = content;
            } else if (qName.equals("url")) {
                pub.url = content;
            } else if (qName.equals("author")) {
                pub.authors.add(content);
                if (this.queryType == 'A') {
                    for (String relevantAuthor: this.relevant) {
                        if (content.equalsIgnoreCase(relevantAuthor)) {
                            Q1Parser.result.add(pub);
                            pub.setRelevance(content, authorToSearch);
                        }
                    }
                }
            }

            if (qName.equals("pages") || qName.equals("title") ||
                qName.equals("year") || qName.equals("volume") ||
                qName.equals("volume") || qName.equals("journal") ||
                qName.equals("booktitle") || qName.equals("url") ||
                qName.equals("author")) {
                this.bufupdate = false;
                contentBuf.setLength(0);
            }
            
            if (qName.equals(pub.type)) {
                pub = null;
            }
        }
    }

	public void characters(char[] ch, int start, int length){		
        if (length == 0) return;
        if (bufupdate) {
            contentBuf.append(ch, start, length);
        }
	}

    public static ArrayList<Publication>
        queryB(ArrayList<String> tags, HashMap<String, Author> authors)
    {
        try {
            result = new ArrayList<>();
            if (tags.size() == 0) {
                throw new Exception("Empty search");
            }
            String fname = "/Users/darkapex/misc/dblp.xml";
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            InputSource is = new InputSource(new InputStreamReader(
                        new FileInputStream(new File(fname)), "UTF-8"));
            is.setEncoding("UTF-8");
            saxParser.parse(is, new Q1Parser(tags, authors));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static ArrayList<Publication>
        queryA(String author, HashMap<String, Author> authors)
    {
        try {
            result = new ArrayList<>();
            if (author.equals("")) {
                throw new Exception("Empty search");
            }
            String fname = "/Users/darkapex/misc/dblp.xml";
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            InputSource is = new InputSource(new InputStreamReader(
                        new FileInputStream(new File(fname)), "UTF-8"));
            is.setEncoding("UTF-8");
            saxParser.parse(is, new Q1Parser(author, authors));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private ArrayList<String> relevantAuthors(String author) {
        ArrayList<String> ret = new ArrayList<>();
        for (String a: authors.keySet()) {
            if (a != null && a.contains(author)) {
                //System.out.println(a);
                ret.addAll(authors.get(a).getNames());
            }
        }
        return ret;
    }
}
