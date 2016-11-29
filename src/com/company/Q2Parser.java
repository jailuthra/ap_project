package com.company;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

class Q2Parser extends DefaultHandler {
    private ArrayList<String> relevant;
    private Publication pub = null;
    private HashMap<String, Author> authors;

    private StringBuilder contentBuf = new StringBuilder();
    private boolean bufupdate;
    private String content;

    private Q2Parser(HashMap<String, Author> authors) {
        this.authors = authors;
    }

    public void startDocument() throws SAXException {
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
                if (this.authors.containsKey(content)) {
                    this.authors.get(content).incrementPubs();
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

    private static ArrayList<Author>
        computeResult(HashMap<String, Author> authors, int k) {
        ArrayList<Author> ret = new ArrayList<>();
        for (Author a: authors.values()) {
            if (a.pubsMoreThan(k)) {
                ret.add(a);
            }
        }
        return ret;
    }

    public static ArrayList<Author>
        query(int k, HashMap<String, Author> authors)
    {
        try {
            if (!Author.pubs_computed) {
                String fname = DBLPEngine.fname;
                SAXParserFactory spf = SAXParserFactory.newInstance();
                spf.setNamespaceAware(true);
                SAXParser saxParser = spf.newSAXParser();
                InputSource is = new InputSource(new InputStreamReader(
                            new FileInputStream(new File(fname)), "UTF-8"));
                is.setEncoding("UTF-8");
                saxParser.parse(is, new Q2Parser(authors));
                Author.pubs_computed = true;
            }
            return Q2Parser.computeResult(authors, k);
        } catch (Exception e) {
            Author.pubs_computed = false;
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
