import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

class Q1Parser extends DefaultHandler {
    private String authorToSearch;
    private ArrayList<String> tags;
    private ArrayList<String> relevant;
    private String content;
    private Publication pub = null;
    private HashMap<String, Author> authors;
    private char queryType;
    private static ArrayList<Publication> result = new ArrayList<>();

    private Q1Parser(String author, HashMap<String, Author> authors) {
        queryType = 'A';
        this.authorToSearch = author;
        this.authors = authors;
    }

    private Q1Parser(ArrayList<String> title_tags) {
        queryType = 'T';
        this.tags = title_tags;
    }

    public void startDocument() throws SAXException {
        relevant = this.relevantAuthors(authorToSearch);
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
        }
    }

    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        if (pub != null) {
            if (qName.equals("pages")) {
                pub.pages = content;
            } else if (qName.equals("title")) {
                pub.title = content;
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
            
            if (qName.equals(pub.type)) {
                pub = null;
            }
        }
    }

	public void characters(char[] ch, int start, int length){		
	    this.content = new String(ch, start, length);
	}

    public static ArrayList<Publication>
        queryA(String author, HashMap<String, Author> authors)
    {
        try {
            result = new ArrayList<>();
            String fname = "/Users/darkapex/misc/dblp.xml";
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(new Q1Parser(author, authors));
            xmlReader.parse("file://" + fname);
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
