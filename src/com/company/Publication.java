package com.company;

/**
 * \author Jai Luthra   2015043
 * \author Vasu Agarwal 2015113
 */

import java.util.*;

/** @brief Publication class */
public class Publication {
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

    /** Get similarity between a list of tags and this publication's title.
     * \param tags ArrayList of title tags
     * \return integer value denoting similarity
     */
    public int getSimilarity(ArrayList<String> tags) {
        ArrayList<String> t = new ArrayList<>(Arrays.asList(title.split("\\s*(-|,|\\s)\\s*")));
        int out = 0;
        for (String tag: t) {
            for (String tag2: tags) {
                if (tag.equalsIgnoreCase(tag2)) {
                    out += 1;
                }
            }
        }
        return out;
    }

    /** Set relevance of publication wrt author name.
     * \param content String read from xml file
     * \param authorToSearch Search term (Author)
     */
    public void setRelevance(String content, String authorToSearch) {
        if (content.equalsIgnoreCase(authorToSearch)) {
            this.relevance += 2;
        } else if (content.toLowerCase().startsWith(
                    authorToSearch.toLowerCase() + " ")) {
            this.relevance += 1;
        }
    }

    public String toString() {
        return relevance + " " + title + " " + year + " ";
    }

    /** Filter an ArrayList of publications by start and end year. */
    public static ArrayList<Publication> filterYear(ArrayList<Publication> l1, int y1, int y2) {
        ArrayList<Publication> out = new ArrayList<>();
        for (Publication pub: l1) {
            if (pub.year <= y2 && pub.year >= y1) {
                out.add(pub);
            }
        }
        return out;
    }

    /** Filter an ArrayList of publications by since given year. */
    public static ArrayList<Publication> filterYear(ArrayList<Publication> l1, int y1) {
        ArrayList<Publication> out = new ArrayList<>();
        for (Publication pub: l1) {
            if (pub.year >= y1) {
                out.add(pub);
            }
        }
        return out;
    }

    /** Sort an ArrayList of publications by year (descending order). */
    public static ArrayList<Publication> sortByYear(ArrayList<Publication> l1) {
        Collections.sort(l1, new YearComparator());
        return l1;
    }

    /** Sort an ArrayList of publications by relevance (descending order). */
    public static ArrayList<Publication> sortByRel(ArrayList<Publication> l1) {
        Collections.sort(l1, new RelevanceComparator());
        return l1;
    }

    private static class YearComparator implements Comparator<Publication> {
        public int compare(Publication o1, Publication o2) {
            return o2.year - o1.year;
        }

        public boolean equals(Publication o1, Publication o2) {
            return o1.year == o2.year;
        }
    }

    private static class RelevanceComparator implements Comparator<Publication> {
        public int compare(Publication o1, Publication o2) {
            return o2.relevance - o1.relevance;
        }

        public boolean equals(Publication o1, Publication o2) {
            return o1.relevance == o2.relevance;
        }
    }
}
