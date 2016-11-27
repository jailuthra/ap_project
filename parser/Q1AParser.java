import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

class Q1AParser extends DefaultHandler {
    private String authorToSearch;
    private String content;
    private Publication pub = null;
    private HashMap<String, Author> authors;
    private static ArrayList<Publication> result = new ArrayList<>();
    private int count = 0;

    private Q1AParser(String author, HashMap<String, Author> authors) {
        this.authorToSearch = author;
        this.authors = authors;
    }

    public void startDocument() throws SAXException {
    }

    public void endDocument() throws SAXException {
        System.out.println(count);
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
                if (content.contains(this.authorToSearch)) {
                    Q1AParser.result.add(pub);
                }
            }
            
            if (qName.equals(pub.type)) {
                count += 1;
                pub = null;
            }
        }
    }

	public void characters(char[] ch, int start, int length){		
	    this.content = new String(ch, start, length);
	}

    //public static void main(String[] args) throws Exception {
    public static ArrayList<Publication>
        query(String author, HashMap<String, Author> authors)
    {
        try {
            result = new ArrayList<>();
            String fname = "/Users/darkapex/misc/dblp.xml";
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(new Q1AParser(author, authors));
            xmlReader.parse("file://" + fname);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
