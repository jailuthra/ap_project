package com.company;

import java.util.*;

class DBLPEngine {
    private HashMap<String, Author> authorMap = null;
    private HashMap<String, ArrayList<Publication>> q1Acache = new HashMap<>();
    private HashMap<String, ArrayList<Publication>> q1Bcache = new HashMap<>();
    public static String fname = "/Users/darkapex/misc/dblp.xml";
    
    private void loadAuthors() {
        try {
            authorMap = AuthorParser.getHashMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Publication> query1A(String author) {
        if (!q1Acache.containsKey(author)) {
            if (authorMap == null) {
                this.loadAuthors();
            }
            q1Acache.put(author, Q1Parser.queryA(author, authorMap));
        }
        return q1Acache.get(author);
    }
    
    public ArrayList<Publication> query1B(String title) {
        if (!q1Bcache.containsKey(title)) {
            if (authorMap == null) {
                this.loadAuthors();
            }
            ArrayList<String> tags = new ArrayList<>(Arrays.asList(title.split("\\s*(-|,|\\s)\\s*")));
            q1Bcache.put(title, Q1Parser.queryB(tags, authorMap));
        }
        return q1Bcache.get(title);
    }

    public ArrayList<Author> query2(int k) {
        if (authorMap == null) {
            this.loadAuthors();
        }
        return Q2Parser.query(k, authorMap);
    }
    
    public static void main(String[] args) {
        DBLPEngine engine = new DBLPEngine();
        System.out.println(engine.query2(350).size());
        System.out.println(engine.query2(450).size());
        //for (Author a: engine.query2(300)) {
            //System.out.println(a);
        //}
        for (Publication pub: Publication.sortByYear(engine.query1A("Alexander Weber"))) {
            System.out.println(pub + " by " + pub.getAuthors());
        }
        for (Publication pub: Publication.sortByRel(engine.query1B("finite state machines"))) {
            System.out.println(pub + " by " + pub.getAuthors());
        }
    }
}
