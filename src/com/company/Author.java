package com.company;

/**
 * \author Jai Luthra   2015043
 * \author Vasu Agarwal 2015113
 */

import java.util.*;
import java.io.*;

/** @brief Author class */ 
public class Author implements Serializable {
    private String name;
    private String key;
    private Set<String> names;
    public Map<Integer, Integer> pubs_in_year; ///< Hash table Year : No of Publications
    public Set<Publication> pubs = new HashSet<>();
    public static boolean pubs_computed = false;
    private int nb_pubs;

    public Author(String key) {
        this.key = key;
        this.names = new HashSet<>();
        this.pubs_in_year = new HashMap<>();
        this.nb_pubs = 0;
    }

    /** Add a name to author object. */
    public void addName(String name) {
        if (this.name == null) {
            this.name = name;
        }
        this.names.add(name);
    }

    /** Increment number of publications. */
    public void incrementPubs() {
        if (!pubs_computed) {
            nb_pubs += 1;
        }
    }

    /** Check if number of publications is more than k.
     * \param k number to check with
     */
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

    /** Increment number of publications in given year.
     * \param year Given year
     */
    public void incrementPubYear(int year) {
        if (pubs_in_year.containsKey(year)) {
            pubs_in_year.put(year, pubs_in_year.get(year) + 1);
        } else {
            pubs_in_year.put(year, 1);
        }
    }

    /** Get number of publications of given year.
     * \param year Given year
     */
    public int getYearVal(int year) {
        if (!pubs_in_year.containsKey(year)) {
            return 0;
        } else {
            return pubs_in_year.get(year);
        }
    }

    /** Predict number of publications for given year.
     * \param year Given year
     */
    public int predict(int year) {
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
        return (sum / no_of_years);
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
