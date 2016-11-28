import java.util.*;
import java.io.*;

class Author implements Serializable {
    private String name;
    private String key;
    private ArrayList<String> names;

    public Author(String key) {
        this.key = key;
        this.names = new ArrayList<>();
    }

    public void addName(String name) {
        if (this.name == null) {
            this.name = name;
        }
        this.names.add(name);
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getNames() {
        return this.names;
    }

    public String toString() {
        String out = key + " ";
        for (int i = 0; i < names.size(); i++) {
            if (i == names.size() - 1) {
                out += name;
            } else {
                out += name + " aka ";
            }
        }
        return out;
    }
}
