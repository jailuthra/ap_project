import java.util.*;

class Publication {
    public final String key;
    public final String type;
    public ArrayList<String> authors;
    public String title;
    public String pages;
    public int year;
    public String volume; 
    public String journal_book;
    public String url;

    public Publication(String type, String key) {
        this.type = type;
        this.key = key;
        this.authors = new ArrayList<>();
    }

    public String toString() {
        return type + ": " + title + " " + year + " ";
    }
}
