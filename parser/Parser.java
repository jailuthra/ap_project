import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

class Parser extends DefaultHandler {
    private String content;
    private Author author;
    private ArrayList<Author> authorList = new ArrayList<>();

    public void startDocument() throws SAXException {
    }

    public void endDocument() throws SAXException {
        //Author.serialWriteList(authorList, "authors.db");
        //System.out.println(Author.serialReadList("authors.db"));
        System.out.println(authorList);
    }

    public void startElement(String uri,
                             String localName,
                             String qName, 
                             Attributes atts) throws SAXException {
        if (qName.equals("www")) {
            author = new Author(atts.getValue("key"));
        }
    }

    public void endElement(String uri,
                           String localName,
                           String qName) throws SAXException {
        if (author != null && qName.equals("author")) {
            author.addName(content);
        } else if (qName.equals("www")) {
            authorList.add(author);
            author = null;
        }
    }

	public void characters(char[] ch, int start, int length){		
	    this.content = new String(ch, start, length);
	}

    public static void main(String[] args) throws Exception {
        String fname = "/Users/darkapex/misc/dblp.xml";
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(new Parser());
        xmlReader.parse("file://" + fname);
    }
}
