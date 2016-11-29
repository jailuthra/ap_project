package com.company;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

class Q3Parser extends DefaultHandler {
    private Publication pub = null;
    private HashMap<String, Author> authorMap;
    private String[] authors;
    private Set<String> relevant;

    private StringBuilder contentBuf = new StringBuilder();
    private boolean bufupdate;
    private String content;

    private Q3Parser(String[] authors, HashMap<String, Author> authorMap) {
        this.authors = authors;
        this.authorMap = authorMap;
        this.relevant = this.relevantAuthors();
    }

    public void startDocument() throws SAXException {
        for (int i = 0; i < authors.length; i++) {
            authorMap.get(authors[i]).pubs_in_year = new HashMap<>();
        }
    }

    public void endDocument() throws SAXException {
        for (int i = 0; i < authors.length; i++) {
            Author a = authorMap.get(authors[i]);
            for (Publication pub: a.pubs) {
                a.incrementPubYear(pub.year);
            }
        }
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
                for (String a: this.relevant) {
                    if (content.equals(a)) {
                        authorMap.get(a).addPub(pub);
                        break;
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

    public static int[] query(String[] author_names, int year,
            HashMap<String, Author> authorMap) {
        try {
            for (int i = 0; i < author_names.length; i++) {
                if (!authorMap.containsKey(author_names[i])) {
                    return null;
                }
            }
            String fname = DBLPEngine.fname;
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            InputSource is = new InputSource(new InputStreamReader(
                        new FileInputStream(new File(fname)), "UTF-8"));
            is.setEncoding("UTF-8");
            saxParser.parse(is, new Q3Parser(author_names, authorMap));
            int[] predictions = new int [10];
            for (int i = 0; i < 5; i++) {
                predictions[i] = authorMap.get(author_names[i]).predict(year);
            }
            for (int i = 0; i < 5; i++) {
                predictions[5 + i] = authorMap.get(author_names[i]).getYearVal(year);
            }
            return predictions;
        } catch (Exception e) {
            Author.pubs_computed = false;
            e.printStackTrace();
            return null;
        }
    }

    private Set<String> relevantAuthors() {
        Set<String> ret = new HashSet<>();
        for (String a: Arrays.asList(authors)) {
            ret.addAll(authorMap.get(a).getNames());
        }
        return ret;
    }
}
