import java.util.*;

class DBLPEngine {
    HashMap<String, Author> authorMap = null;
    
    private void loadAuthors() {
        try {
            authorMap = AuthorParser.getHashMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Publication> query1A(String author) {
        if (authorMap == null) {
            this.loadAuthors();
        }
        return Q1Parser.queryA(author, authorMap);
    }
    
    public ArrayList<Publication> query1B(String title) {
        if (authorMap == null) {
            this.loadAuthors();
        }
        ArrayList<String> tags = new ArrayList<>(Arrays.asList(title.split("\\s*(-|,|\\s)\\s*")));
        return Q1Parser.queryB(tags, authorMap);
    }

    public ArrayList<Author> query2(int k) {
        if (authorMap == null) {
            this.loadAuthors();
        }
        return Q2Parser.query(k, authorMap);
    }
    
    public static void main(String[] args) {
        DBLPEngine engine = new DBLPEngine();
        for (Author a: engine.query2(300)) {
            System.out.println(a);
        }
        //for (Publication pub: Publication.sortByYear(engine.query1A("Alexander Weber"))) {
            //System.out.println(pub + " by " + pub.getAuthors());
        //}
        //for (Publication pub: Publication.sortByRel(engine.query1B("finite state machines"))) {
            //System.out.println(pub + " by " + pub.getAuthors());
        //}
    }
}
