import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

class AuthorParser extends DefaultHandler {
    private StringBuilder contentBuf = new StringBuilder();
    private boolean bufupdate;
    private String content;
    private Author author;
    private static HashMap<String, Author> authors = new HashMap<>(1800000);

    public void startDocument() throws SAXException {
    }

    public void endDocument() throws SAXException {
        //for (String author: authors.keySet()) {
            //System.out.println(authors.get(author).getName());
        //}
        System.out.println("Authors: " + authors.size());
    }

    public void startElement(String uri, String localName,
                             String qName, Attributes atts) throws SAXException {
        if (qName.equals("www")) {
            String key = atts.getValue("key");
            if (key != null && key.substring(0, 9).equals("homepages")) {
                author = new Author(atts.getValue("key"));
            }
        } else if (author != null && qName.equals("author")) {
            this.bufupdate = true;
        }
    }

    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        if (author != null) {
            content = contentBuf.toString();
            if (qName.equals("author")) {
                author.addName(content);
                bufupdate = false;
                contentBuf.setLength(0);
            } else if (qName.equals("www")) {
                if (author.getName() != null) {
                    authors.put(author.getName(), author);
                }
                author = null;
            }
        }
    }

	public void characters(char[] ch, int start, int length){		
        if (length == 0) return;
        if (bufupdate) {
            contentBuf.append(ch, start, length);
        }
	}

    public static HashMap<String, Author> getHashMap() throws Exception
    {

        authors = new HashMap<>(1800000);
        String fname = "/Users/darkapex/misc/dblp.xml";
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        InputSource is = new InputSource(new InputStreamReader(
                    new FileInputStream(new File(fname)), "UTF-8"));
        is.setEncoding("UTF-8");
        saxParser.parse(is, new AuthorParser());
        return authors;
    }
}
