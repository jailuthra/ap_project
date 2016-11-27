import java.util.*;
import java.io.*;

class Author implements Serializable {
    private String name;
    private String key;
    ArrayList<String> names;

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

    public static void serialWriteList(ArrayList<Author> alist, String fname) {
        try {
            FileOutputStream fos = new FileOutputStream(fname);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(alist);
            oos.close();
            fos.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Author> serialReadList(String fname) {
        ArrayList<Author> out = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(fname);
            ObjectInputStream ois = new ObjectInputStream(fis);
            out = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        } catch(IOException ioe){
            ioe.printStackTrace();
            System.exit(1);
        } catch(ClassNotFoundException c){
            System.out.println("Class not found");
            c.printStackTrace();
            System.exit(1);
        }
        return out;
    }
}
