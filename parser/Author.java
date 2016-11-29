import java.util.*;
import java.io.*;

class Author implements Serializable {
    private String name;
    private String key;
    private Set<String> names;
    public static boolean pubs_computed = false;
    private int nb_pubs;

    public Author(String key) {
        this.key = key;
        this.names = new HashSet<>();
        this.nb_pubs = 0;
    }

    public void addName(String name) {
        if (this.name == null) {
            this.name = name;
        }
        this.names.add(name);
    }

    public void incrementPubs() {
        if (!pubs_computed) {
            nb_pubs += 1;
        }
    }

    public boolean pubsMoreThan(int k) {
        if (pubs_computed) {
            return (nb_pubs > k);
        } else {
            throw new RuntimeException("Publications not computed");
        }
    }

    public String getName() {
        return this.name;
    }

    public Set<String> getNames() {
        return this.names;
    }

    public String toString() {
        String out = "";
        String[] list = this.names.toArray(new String[names.size()]);
        for (int i = 0; i < list.length; i++) {
            if (i == list.length - 1) {
                out += list[i];
            } else {
                out += list[i] + " aka ";
            }
        }
        return out;
    }
}
