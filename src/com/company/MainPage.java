package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by vasu on 17/11/16.
 */
public class MainPage extends JFrame implements ActionListener {

    class q1 implements ActionListener{
        String[] comboChoices = {"Search by Author name","Search by title tags"};
        JComboBox box2;
        JLabel lb1,lb2,lb3;
        JTextField tf1,tf2,tf3,tf4;
        JButton search;
        JButton reset;
        JRadioButton rb1,rb2;
        ButtonGroup bg;

        public q1(){
            preparegui();
        }

        public void preparegui(){
            setLayout(null);
            lb1 = new JLabel("Name/Title tags");
            lb2 = new JLabel("Since year");
            lb3 = new JLabel("Custom Range");
            tf1 = new JTextField();
            tf2 = new JTextField();
            tf3 = new JTextField();
            tf4 = new JTextField();
            box2 = new JComboBox(comboChoices);
            rb1 = new JRadioButton("Sort by year");
            rb2 = new JRadioButton("Sort by relevance");
            bg = new ButtonGroup();
            bg.add(rb1);
            bg.add(rb2);
            search = new JButton("Search");
            reset = new JButton("reset");
            add(lb1);add(lb2);add(lb3);add(tf1);add(tf2);add(tf3);add(tf4);
            add(search);add(reset);add(box2);add(rb1);add(rb2);
            box2.setEnabled(true);
            search.setEnabled(true);
            reset.setEnabled(true);
            search.setActionCommand("search");
            reset.setActionCommand("reset");
            search.addActionListener(this);
            reset.addActionListener(this);
        }


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if("reset".equals(actionEvent.getActionCommand())){
                tf1.setText("");
                tf2.setText("");
                tf3.setText("");
                tf4.setText("");
            }
            else if("search".equals(actionEvent.getActionCommand())){
                //main code here
            }
        }
    }

    class q2 implements ActionListener{
        JLabel lb;
        JTextField tf1;
        JButton search;
        JButton reset;

        public q2(){
            preparegui();
        }

        public void preparegui(){
            setLayout(null);
            lb = new JLabel("No of publications");
            tf1 = new JTextField();
            search = new JButton("Search");
            reset = new JButton("Reset");
            add(lb);
            add(tf1);
            add(search);
            add(reset);
            search.setEnabled(true);
            reset.setEnabled(true);
            search.setActionCommand("search");
            reset.setActionCommand("reset");
            search.addActionListener(this);
            reset.addActionListener(this);
        }


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if("search".equals(actionEvent.getActionCommand())){
                //main code here;
            }else if("reset".equals(actionEvent.getActionCommand())){
                tf1.setText("");
            }
        }
    }

    class q3 implements ActionListener{
        JLabel lb1,lb2,lb3,lb4,lb5,lb6;
        JTextArea jt1,jt2,jt3,jt4,jt5,jt6;
        JButton search;
        JButton reset;

        public q3(){
            preparegui();
        }

        public void preparegui(){
            lb1 = new JLabel("");
            lb2 = new JLabel("");
            lb3 = new JLabel("");
            lb4 = new JLabel("");
            lb5 = new JLabel("");
            lb6 = new JLabel("");
            jt1 = new JTextArea();
            jt2 = new JTextArea();
            jt3 = new JTextArea();
            jt4 = new JTextArea();
            jt5 = new JTextArea();
            jt6 = new JTextArea();
            search = new JButton("Search");
            reset = new JButton("Reset");
            add(lb1);add(lb2);add(lb3);add(lb4);add(lb5);add(lb6);
            add(jt1);add(jt2);add(jt3);add(jt4);add(jt5);add(jt6);
            add(search);add(reset);
            search.setEnabled(true);
            reset.setEnabled(true);
            search.addActionListener(this);
            reset.addActionListener(this);
            search.setActionCommand("search");
            reset.setActionCommand("reset");
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent){
            if("search".equals(actionEvent.getActionCommand())){
                //main code here;
            }else if("reset".equals(actionEvent.getActionCommand())){
                jt1.setText("");
                jt2.setText("");
                jt3.setText("");
                jt4.setText("");
                jt5.setText("");
                jt6.setText("");
            }
        }
    }


    JLabel heading;
    String[] choices = {"Query 1", "Query 2", "Query 3"};
    JComboBox choiceBox;
    JTable dataset;
    JButton next;
    int numRows = 20;
    String[] colHeadings = {"Name","Title","Year"};

    public MainPage(){
        GUI();
    }

    public void GUI(){
        setSize(800,600);
        setLayout(null);
        heading = new JLabel("DBLP Query Engine");
        heading.setBounds(50,0,300,100);
        add(heading);
        choiceBox = new JComboBox(choices);
        choiceBox.setBounds(100,100,100,20);
        add(choiceBox);
        next = new JButton("Next");
        next.setFont(new Font("Times New Roman",Font.BOLD,14));
        next.setBounds(100,500,100,20);
        add(next);
        DefaultTableModel model = new DefaultTableModel(numRows,colHeadings.length);
        model.setColumnIdentifiers(colHeadings);
        dataset = new JTable(model);
        dataset.setBounds(350,100,400,320);
        add(dataset);
        choiceBox.setEnabled(true);
        next.setEnabled(true);
        choiceBox.setActionCommand("choiceBox");
        next.setActionCommand("next");
        choiceBox.addActionListener(this);
        next.addActionListener(this);
        setVisible(true);

    }



    public static void main(String[] args){
        MainPage m = new MainPage();
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String s = (String) choiceBox.getSelectedItem();
        switch(s) {
            case "Query 1":
            {
                q1 q11 = new q1();
                break;
            }
            case "Query 2":
                System.out.println("2");
                q2 q22 = new q2();
                break;
            case "Query 3":
                q3 q33 = new q3();
                break;
        }
    }
}
