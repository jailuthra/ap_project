package com.company;

import java.util.*;
import java.io.*;

class Author implements Serializable {
    private String name;
    private String key;
    private Set<String> names;
    public Map<Integer, Integer> pubs_in_year;
    public Set<Publication> pubs = new HashSet<>();
    public static boolean pubs_computed = false;
    private int nb_pubs;

    public Author(String key) {
        this.key = key;
        this.names = new HashSet<>();
        this.pubs_in_year = new HashMap<>();
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

    public void addPub(Publication pub) {
        this.pubs.add(pub);
    }

    public void incrementPubYear(int year) {
        if (pubs_in_year.containsKey(year)) {
            pubs_in_year.put(year, pubs_in_year.get(year) + 1);
        } else {
            pubs_in_year.put(year, 1);
        }
    }

    public int getYearVal(int year) {
        if (pubs_in_year.containsKey(year)) {
            return 0;
        } else {
            return pubs_in_year.get(year);
        }
    }

    public int predict(int year) {
        System.out.printf("Author: %s ", this);
        System.out.println(pubs_in_year);
        ArrayList<Integer> keys = new ArrayList<Integer>(pubs_in_year.keySet());
        Collections.sort(keys);
        if (year - 1 > keys.get(keys.size() - 1)) {
            return 0;
        }
        int sum = 0;
        int max_year = 0;
        for (int key: keys) {
            if (key < year) {
                sum += pubs_in_year.get(key);
                max_year = key;
            }
        }
        int no_of_years = max_year - keys.get(0);
        return (sum / no_of_years) * (year - max_year);
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
