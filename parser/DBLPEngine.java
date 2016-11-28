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
        ArrayList<String> tags = new ArrayList<>(Arrays.asList(title.split(" -,")));
        return Q1Parser.queryB(tags, authorMap);
    }
    
    public static void main(String[] args) {
        DBLPEngine engine = new DBLPEngine();
        for (Publication pub: engine.query1A("Alexander Weber")) {
            System.out.println(pub + " by " + pub.getAuthors());
        }
        //for (Publication pub: engine.query1B("finite state machines")) {
            //System.out.println(pub + " by " + pub.getAuthors());
        //}
    }
}
