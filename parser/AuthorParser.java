import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

class AuthorParser extends DefaultHandler {
    private String content;
    private Author author;
    private static HashMap<String, Author> authors = new HashMap<>(1800000);

    public void startDocument() throws SAXException {
    }

    public void endDocument() throws SAXException {
        //Author.serialWrite(authors, "authors.db");
        //System.out.println(Author.serialRead("authors.db"));
        System.out.println("Authors: " + authors.size());
    }

    public void startElement(String uri, String localName,
                             String qName, Attributes atts) throws SAXException {
        if (qName.equals("www")) {
            String key = atts.getValue("key");
            if (key != null && key.substring(0, 9).equals("homepages")) {
                author = new Author(atts.getValue("key"));
            }
        }
    }

    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        if (author != null) {
            if (qName.equals("author")) {
                author.addName(content);
            } else if (qName.equals("www")) {
                authors.put(author.getName(), author);
                author = null;
            }
        }
    }

	public void characters(char[] ch, int start, int length){		
	    this.content = new String(ch, start, length);
	}

    public static HashMap<String, Author> getHashMap() throws Exception
    {

        authors = new HashMap<>(1800000);
        String fname = "/Users/darkapex/misc/dblp.xml";
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(new AuthorParser());
        xmlReader.parse("file://" + fname);
        return authors;
    }
}
