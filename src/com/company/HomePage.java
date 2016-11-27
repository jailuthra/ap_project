package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by vasu on 28/11/16.
 */
public class HomePage extends JFrame implements ActionListener {

    JLabel Heading;
    JPanel jp;
    JPanel left;
    JPanel right;
    int numRows = 20;
    String[] colHeadings = {"Serial No","Authors","Title","Pages","Year","Volume","Journal/Book Title","url"};
    JTable dataset;
    JComboBox choiceBox;
    String[] choices = {"Query 1","Query 2","Query 3"};
    JPanel first;


    class q1 implements ActionListener{



        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    class q2 implements ActionListener{

        JLabel lb;
        JTextArea tarea;
        JButton search;
        JButton reset;
        JPanel p1;
        JPanel p2;

        public q2(){
            lb = new JLabel("No of Publications");
            tarea = new JTextArea("");
            search = new JButton("Search");
            reset = new JButton("Reset");
            p1 = new JPanel();
            p2 = new JPanel();
            p1.add(lb);
            p1.add(tarea);
            p2.add(search);
            p2.add(reset);
            left.add(p1);
            left.add(p2);
            add(left);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }


    class q3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }



    public HomePage(){
        preparegui();
    }

    public void preparegui(){
        setSize(800,800);
        jp = new JPanel();
        Heading = new JLabel("DBLP Query Engine",SwingConstants.CENTER);
        add(Heading, BorderLayout.NORTH);
        add(jp);
        choiceBox = new JComboBox(choices);
        left = new JPanel();
        left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS));
        right = new JPanel();
        first = new JPanel();
        jp.setLayout(new BoxLayout(jp,BoxLayout.X_AXIS));
        DefaultTableModel model = new DefaultTableModel(numRows,colHeadings.length);
        model.setColumnIdentifiers(colHeadings);
        dataset = new JTable(model);
        first.add(choiceBox);
        left.add(first);
        right.add(new JScrollPane(dataset),BorderLayout.CENTER);
        jp.add(left);
        jp.add(right);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String s = (String) choiceBox.getSelectedItem();
        switch(s) {
            case "Query 1": {
                System.out.println("1");
                HomePage.q1 q11 = new HomePage.q1();
                break;
            }
            case "Query 2":
                System.out.println("2");
                HomePage.q2 q22 = new HomePage.q2();
                break;
            case "Query 3":
                System.out.println("3");
                HomePage.q3 q33 = new HomePage.q3();
                break;
        }
    }

    public static void main(String[] args){
        HomePage p = new HomePage();
    }

}
