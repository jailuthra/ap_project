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
    public int relevance = 0;

    public String getAuthors() {
        return authors.toString().replace("[", "").replace("]", "");
    }

    public Publication(String type, String key) {
        this.type = type;
        this.key = key;
        this.authors = new ArrayList<>();
    }

    public void setRelevance(String content, String authorToSearch) {
        if (content.equals(authorToSearch)) {
            this.relevance += 2;
        } else if (content.startsWith(authorToSearch + " ")) {
            this.relevance += 1;
        }
    }


    public String toString() {
        return relevance + " " + title + " " + year + " ";
    }
}
