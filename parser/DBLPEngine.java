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

    public ArrayList<Publication> query1(String author) {
        if (authorMap == null) {
            this.loadAuthors();
        }
        return Q1AParser.query(author, authorMap);
    }

    public static void main(String[] args) {
        DBLPEngine engine = new DBLPEngine();
        for (Publication pub: engine.query1("Alexander Weber")) {
            System.out.println(pub + " by " + pub.getAuthors());
        }
    }
}
