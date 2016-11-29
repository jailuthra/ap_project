package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by vasu on 28/11/16.
 */
public class HomePage extends JFrame implements ActionListener {
    DBLPEngine engine;

    int nextValue;
    HomePage.q1 q11;
    HomePage.q2 q22;
    HomePage.q3 q33;
    JLabel Heading;
    JPanel jp;
    JPanel left;
    JLabel count;
    JTextField totalCount;
    JPanel right;
    JPanel last;
    int numRows = 20;
    String[] q1colHeadings = {"Serial No","Authors","Title","Pages","Year","Volume","Journal/Book Title","url"};
    JTable q1dataset;
    String[] q2colHeadings = {"Authors"};
    JTable q2dataset;
    String[] q3colHeadings = {"Author","Prediction"};
    JTable q3dataset;
    JPanel dataset1,dataset2,dataset3,dataset4;
    JComboBox choiceBox;
    String[] choices = {"Query 1","Query 2","Query 3"};
    JPanel first;
    JButton next;

    String[] choices2 = {"Search by Author Name","Search by Title Tag"};
    JComboBox q1choiceBox;
    JLabel q1lb1,q1lb2,q1lb3;
    JTextField q1jt1,q1jt2,q1jt3,q1jt4;
    JButton q1search,q1reset;
    JRadioButton q1rb1,q1rb2;
    ButtonGroup q1bg1;
    JPanel q1p1,q1p2,q1p3,q1p4,q1p5,q1p6,q1p7;
    String q1input1,q1input2,q1input3,q1input4;


    JLabel q2lb;
    JTextField q2tarea;
    JButton q2search;
    JButton q2reset;
    JPanel q2p1;
    JPanel q2p2;
    String q2input;


    JLabel q3lb1,q3lb2,q3lb3,q3lb4,q3lb5,q3lb6;
    JTextField q3tf1,q3tf2,q3tf3,q3tf4,q3tf5,q3tf6;
    JButton q3search;
    JButton q3reset;
    JPanel q3p1,q3p2,q3p3,q3p4,q3p5,q3p6,q3p7;
    String q3input1,q3input2,q3input3,q3input4,q3input5,q3input6;


    class q1 implements ActionListener{
        private ArrayList<Publication> q1results;

        public q1(){
            //nextValue=0;
            q1jt1.setText("");
            q1jt2.setText("");
            q1jt3.setText("");
            q1jt4.setText("");
            q1bg1.clearSelection();
            q1rb1.addActionListener(this);
            q1rb2.addActionListener(this);
            q1choiceBox.setActionCommand("q1choiceBox");
            q1choiceBox.addActionListener(this);
            q1search.setActionCommand("search");
            q1search.setEnabled(true);
            q1search.addActionListener(this);
            next.setEnabled(true);
            next.setActionCommand("next");
            next.addActionListener(this);
            q1reset.setActionCommand("reset");
            q1reset.setEnabled(true);
            q1reset.addActionListener(this);
        }

        private void updateTable(JTable table, int start) {
            if (q1results != null) {
                for (int row = start; row < start + 20 && row < q1results.size(); row++) {
                    Publication pub = q1results.get(row);
                    table.setValueAt(row + 1, row - start, 0);
                    table.setValueAt(pub.getAuthors(), row - start, 1);
                    table.setValueAt(pub.title, row - start, 2);
                    table.setValueAt(pub.pages, row - start, 3);
                    table.setValueAt(pub.year, row - start, 4);
                    table.setValueAt(pub.volume, row - start, 5);
                    table.setValueAt(pub.journal_book, row - start, 6);
                    table.setValueAt(pub.url, row - start, 7);
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if("search".equals(actionEvent.getActionCommand())){
                nextValue = 0;
                q1input1 = q1jt1.getText();
                q1input2 = q1jt2.getText();
                q1input3 = q1jt3.getText();
                q1input4 = q1jt4.getText();
                System.out.println("search " + q1input1);
                if(q1input1.equals("")){
                    JOptionPane.showMessageDialog(null,"Enter Author/Title name");
                }
                else if(!q1input1.equals("") && q1input2.equals("") && q1input3.equals("") && q1input4.equals("")){
                    if(q1choiceBox.getSelectedIndex()==0){
                        q1results = engine.query1A(q1input1);
                        System.out.println("Recieved results: "  + q1results.size());
                        if(!q1rb2.isSelected()){

                        } else if(!q1rb1.isSelected() && q1rb2.isSelected()){
                            //sort by relevance
                        }
                        updateTable(q1dataset, 0);
                        totalCount.setText(String.valueOf(q1results.size()));
                    } else if(q1choiceBox.getSelectedIndex()==1){
                        if(!q1rb1.isSelected() && !q1rb2.isSelected()){
                            q1results = engine.query1B(q1input1);
                            System.out.println("Recieved results: "  + q1results.size());
                            updateTable(q1dataset, 0);
                            totalCount.setText(String.valueOf(q1results.size()));
                        } else if(q1rb1.isSelected() && !q1rb2.isSelected()){
                            //sort by year
                        } else if(!q1rb1.isSelected() && q1rb2.isSelected()){
                            //sort by relevance
                        }
                    }
                }
                else if(!q1input1.equals("") && !q1input2.equals("") && !q1input3.equals("") && !q1input4.equals("")){
                    JOptionPane.showMessageDialog(null,"Invalid input format");
                    q1jt2.setText("");
                    q1jt3.setText("");
                    q1jt4.setText("");
                }
                else if(!q1input1.equals("") && !q1input2.equals("") && q1input3.equals("") && q1input4.equals("")){
                    if(!q1input2.matches("[0-9]+")){
                        JOptionPane.showMessageDialog(null,"Enter valid input");
                        q1jt2.setText("");
                        q1jt3.setText("");
                        q1jt4.setText("");

                    }
                    else {
                        // only since year textfield is enabled
                        //run code;
                        if(q1choiceBox.getSelectedIndex()==0){
                            if(!q1rb1.isSelected() && !q1rb2.isSelected()){
                                // no radio button selected
                            } else if(q1rb1.isSelected() && !q1rb2.isSelected()){
                                // sort by year
                            } else if(!q1rb1.isSelected() && q1rb2.isSelected()){
                                // sort by relevance
                            }
                        }
                        else if(q1choiceBox.getSelectedIndex()==1){
                            if(!q1rb1.isSelected() && !q1rb2.isSelected()){
                                // no radio button selected
                            } else if(q1rb1.isSelected() && !q1rb2.isSelected()){
                                // sort by year
                            } else if(!q1rb1.isSelected() && q1rb2.isSelected()){
                                // sort by relevance
                            }
                        }
                    }
                }
                else if(!q1input1.equals("") && !q1input2.equals("") && !q1input3.equals("") && q1input4.equals("")){
                    JOptionPane.showMessageDialog(null,"Invalid input format");
                    q1jt2.setText("");
                    q1jt3.setText("");
                    q1jt4.setText("");
                }
                else if(!q1input1.equals("") && !q1input2.equals("") && q1input3.equals("") && !q1input4.equals("")){
                    JOptionPane.showMessageDialog(null,"Invalid input format");
                    q1jt2.setText("");
                    q1jt3.setText("");
                    q1jt4.setText("");
                }
                else if(q1input2.equals("") && !q1input3.equals("") && q1input4.equals("")){
                    JOptionPane.showMessageDialog(null,"Enter proper range");
                    q1jt2.setText("");
                    q1jt3.setText("");
                    q1jt4.setText("");
                }
                else if(!q1input1.equals("") && q1input2.equals("") && q1input3.equals("") && !q1input4.equals("")){
                    JOptionPane.showMessageDialog(null,"Enter proper range");
                    q1jt2.setText("");
                    q1jt3.setText("");
                    q1jt4.setText("");
                }
                else if(!q1input1.equals("") && q1input2.equals("") && !q1input3.equals("") && !q1input4.equals("")){
                    if(!q1input3.matches("[0-9]+") || !q1input4.matches("[0-9]+")){
                        JOptionPane.showMessageDialog(null,"Enter valid input");
                        q1jt2.setText("");
                        q1jt3.setText("");
                        q1jt4.setText("");
                    }
                    else{
                        // custom range code
                        if(q1choiceBox.getSelectedIndex()==0){
                            if(!q1rb1.isSelected() && !q1rb2.isSelected()){
                                // no radio button selected
                            } else if(q1rb1.isSelected() && !q1rb2.isSelected()){
                                // sort by year
                            } else if(!q1rb1.isSelected() && q1rb2.isSelected()){
                                // sort by relevance
                            }
                        }
                        else if(q1choiceBox.getSelectedIndex()==1){
                            if(!q1rb1.isSelected() && !q1rb2.isSelected()){
                                // no radio button selected
                            } else if(q1rb1.isSelected() && !q1rb2.isSelected()){
                                // sort by year
                            } else if(!q1rb1.isSelected() && q1rb2.isSelected()){
                                // sort by relevance
                            }
                        }
                    }
                }
            }
            else if("reset".equals(actionEvent.getActionCommand())){
                System.out.println("reset");
                q1jt1.setText("");
                q1jt2.setText("");
                q1jt3.setText("");
                q1jt4.setText("");
                q1rb1.setSelected(false);
                q1rb2.setSelected(false);
                q1bg1.clearSelection();
            }
            else if("next".equals(actionEvent.getActionCommand())){
                System.out.println("Im in next");
                for(int i=0;i<q1dataset.getRowCount();i++){
                    for(int j=0;j<q1dataset.getColumnCount();j++){
                        q1dataset.setValueAt("",i,j);
                    }
                }
                updateTable(q1dataset,nextValue+20);
                nextValue+=20;
            }
        }
    }

    class q2 implements ActionListener{
        private ArrayList<Author> q2results;

        private void updateTable(JTable table, int start) {
            if (q2results != null) {
                for (int row = start; row < start + 20 && row < q2results.size(); row++) {
                    Author aut = q2results.get(row);
                    table.setValueAt(aut.toString(), row - start, 0);
                }
            }
        }



        public q2(){
            next.setEnabled(true);
            next.setActionCommand("next");
            next.addActionListener(this);
            nextValue=0;
            q2tarea.setText("");
            q2search.setActionCommand("search");
            q2search.addActionListener(this);
            q2search.setEnabled(true);
            q2reset.setActionCommand("reset");
            q2reset.addActionListener(this);
            q2reset.setEnabled(true);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if("search".equals(actionEvent.getActionCommand())){
                nextValue = 0;
                q2input = q2tarea.getText();
                if(q2input.equals("")){
                    JOptionPane.showMessageDialog(null,"Enter number of Publications");
                }
                else if(!q2input.matches("[0-9]+")){
                    JOptionPane.showMessageDialog(null,"Enter numeric input");
                    q2tarea.setText("");
                }
                else{
                    //enter code here
                }
            }
            else if("reset".equals(actionEvent.getActionCommand())){
                q2tarea.setText("");
            }
            else if("next".equals(actionEvent.getActionCommand())){
                for(int i=0;i<q2dataset.getRowCount();i++){
                    for(int j=0;j<q2dataset.getColumnCount();j++){
                        q2dataset.setValueAt("",i,j);
                    }
                }
                updateTable(q2dataset,nextValue+20);
                nextValue+=20;
            }
        }
    }


    class q3 implements ActionListener{


        public q3(){
            next.setEnabled(false);
            q3tf1.setText("");
            q3tf2.setText("");
            q3tf3.setText("");
            q3tf4.setText("");
            q3tf5.setText("");
            q3tf6.setText("");
            q3search.setActionCommand("search");
            q3search.addActionListener(this);
            q3search.setEnabled(true);
            q3reset.setActionCommand("reset");
            q3reset.addActionListener(this);
            q3reset.setEnabled(true);
        }
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if("search".equals(actionEvent.getActionCommand())){
                nextValue = 0;
                q3input1 = q3tf1.getText();
                q3input2 = q3tf2.getText();
                q3input3 = q3tf3.getText();
                q3input4 = q3tf4.getText();
                q3input5 = q3tf5.getText();
                q3input6 = q3tf6.getText();
                if(q3input1.equals("") || q3input2.equals("") || q3input3.equals("") || q3input4.equals("") || q3input5.equals("") || q3input6.equals("")){
                    JOptionPane.showMessageDialog(null,"Input cannot be empty");
                    q3tf1.setText("");
                    q3tf2.setText("");
                    q3tf3.setText("");
                    q3tf4.setText("");
                    q3tf5.setText("");
                    q3tf6.setText("");
                }
                else if(!q3input1.matches("[0-9]+")){
                    JOptionPane.showMessageDialog(null,"Enter numeric input");
                    q3tf1.setText("");
                }
                else if(!q3input1.matches("[0-9]+") || !q3input1.contains("[a-zA-Z]+") || !q3input1.matches("[0-9]+") || !q3input1.contains("[a-zA-Z]+") || !q3input1.matches("[0-9]+") || !q3input1.contains("[a-zA-Z]+") || !q3input1.matches("[0-9]+") || !q3input1.contains("[a-zA-Z]+") || !q3input1.matches("[0-9]+") || !q3input1.contains("[a-zA-Z]+")){
                    JOptionPane.showMessageDialog(null,"Enter valid input");
                    q3tf1.setText("");
                    q3tf2.setText("");
                    q3tf3.setText("");
                    q3tf4.setText("");
                    q3tf5.setText("");
                    q3tf6.setText("");
                }
                else {
                    //enter code here
                }
            }
            else if("reset".equals(actionEvent.getActionCommand())){
                q3tf1.setText("");
                q3tf2.setText("");
                q3tf3.setText("");
                q3tf4.setText("");
                q3tf5.setText("");
                q3tf6.setText("");
            }

        }
    }



    public HomePage(){
        this.engine = new DBLPEngine();
        preparegui();
        preparegui1();
        preparegui2();
        preparegui3();
    }

    @SuppressWarnings("unchecked")
    public void preparegui1(){
        q1choiceBox = new JComboBox(choices2);
        q1lb1 = new JLabel("Name/Title tags");
        q1lb2 = new JLabel("Since Year");
        q1lb3 = new JLabel("Custom Range");
        q1jt1 = new JTextField("",15);
        q1jt2 = new JTextField("",4);
        q1jt3 = new JTextField("",4);
        q1jt4 = new JTextField("",4);
        q1rb1 = new JRadioButton("Sort by Year");
        q1rb2 = new JRadioButton("Sort by Relevance");
        q1search = new JButton("Search");
        q1reset = new JButton("Reset");
        q1bg1 = new ButtonGroup();
        q1p1 = new JPanel();
        q1p2 = new JPanel();
        q1p3 = new JPanel();
        q1p4 = new JPanel();
        q1p5 = new JPanel();
        q1p6 = new JPanel();
        q1p7 = new JPanel();
        q1p1.add(q1choiceBox);
        q1p2.add(q1lb1);q1p2.add(q1jt1);
        q1p3.add(q1lb2);q1p3.add(q1jt2);
        q1p4.add(q1lb3);q1p4.add(q1jt3);q1p4.add(q1jt4);
        q1p5.add(q1rb1);
        q1p6.add(q1rb2);
        q1bg1.add(q1rb1);q1bg1.add(q1rb2);
        q1p7.add(q1search);q1p7.add(q1reset);
        left.add(q1p1);q1p1.setVisible(false);
        left.add(q1p2);q1p2.setVisible(false);
        left.add(q1p3);q1p3.setVisible(false);
        left.add(q1p4);q1p4.setVisible(false);
        left.add(q1p5);q1p5.setVisible(false);
        left.add(q1p6);q1p6.setVisible(false);
        left.add(q1p7);q1p7.setVisible(false);
    }

    public void preparegui2(){
        q2lb = new JLabel("Number of publications");
        q2tarea = new JTextField("",10);
        q2search = new JButton("Search");
        q2reset = new JButton("Reset");
        q2p1 = new JPanel();
        q2p2 = new JPanel();
        q2p1.add(q2lb);
        q2p1.add(q2tarea);
        q2p2.add(q2search);
        q2p2.add(q2reset);
        left.add(q2p1);
        left.add(q2p2);
        q2p1.setVisible(false);
        q2p2.setVisible(false);
    }

    public void preparegui3(){
        q3lb1 = new JLabel("Year");
        q3lb2 = new JLabel("Author 1");
        q3lb3 = new JLabel("Author 2");
        q3lb4 = new JLabel("Author 3");
        q3lb5 = new JLabel("Author 4");
        q3lb6 = new JLabel("Author 5");
        q3tf1 = new JTextField("",10);
        q3tf2 = new JTextField("",15);
        q3tf3 = new JTextField("",15);
        q3tf4 = new JTextField("",15);
        q3tf5 = new JTextField("",15);
        q3tf6 = new JTextField("",15);
        q3p1 = new JPanel();
        q3p2 = new JPanel();
        q3p3 = new JPanel();
        q3p4 = new JPanel();
        q3p5 = new JPanel();
        q3p6 = new JPanel();
        q3p7 = new JPanel();
        q3search = new JButton("Search");
        q3reset = new JButton("Reset");
        q3p1.add(q3lb1);q3p1.add(q3tf1);
        q3p2.add(q3lb2);q3p2.add(q3tf2);
        q3p3.add(q3lb3);q3p3.add(q3tf3);
        q3p4.add(q3lb4);q3p4.add(q3tf4);
        q3p5.add(q3lb5);q3p5.add(q3tf5);
        q3p6.add(q3lb6);q3p6.add(q3tf6);
        q3p7.add(q3search);q3p7.add(q3reset);
        left.add(q3p1);q3p1.setVisible(false);
        left.add(q3p2);q3p2.setVisible(false);
        left.add(q3p3);q3p3.setVisible(false);
        left.add(q3p4);q3p4.setVisible(false);
        left.add(q3p5);q3p5.setVisible(false);
        left.add(q3p6);q3p6.setVisible(false);
        left.add(q3p7);q3p7.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    public void preparegui(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800,550);
        count = new JLabel("Total count of records: ");
        totalCount = new JTextField("",5);
        last = new JPanel();
        last.add(count);
        last.add(totalCount);
        dataset1 = new JPanel();
        dataset2 = new JPanel();
        dataset3 = new JPanel();
        dataset4 = new JPanel();
        jp = new JPanel();
        next = new JButton("Next");
        Heading = new JLabel("DBLP Query Engine",SwingConstants.CENTER);
        Heading.setFont(new Font("Times New Roman",Font.PLAIN,26));
        add(Heading, BorderLayout.NORTH);
        add(jp);
        choiceBox = new JComboBox(choices);
        left = new JPanel();
        left.setPreferredSize(new Dimension(300,200));
        left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS));
        right = new JPanel();
        right.setLayout(new BoxLayout(right,BoxLayout.Y_AXIS));
        first = new JPanel();
        jp.setLayout(new BoxLayout(jp,BoxLayout.X_AXIS));
        DefaultTableModel model1 = new DefaultTableModel(numRows,q1colHeadings.length);
        model1.setColumnIdentifiers(q1colHeadings);
        q1dataset = new JTable(model1);
        DefaultTableModel model2 = new DefaultTableModel(numRows,q2colHeadings.length);
        model2.setColumnIdentifiers(q2colHeadings);
        q2dataset = new JTable(model2);
        DefaultTableModel model3 = new DefaultTableModel(numRows,q3colHeadings.length);
        model3.setColumnIdentifiers(q3colHeadings);
        q3dataset = new JTable(model3);
        q1dataset.getColumnModel().getColumn(1).setPreferredWidth(250);
        q1dataset.getColumnModel().getColumn(2).setPreferredWidth(250);
        choiceBox.setActionCommand("choiceBox");
        choiceBox.addActionListener(this);
        first.add(choiceBox);
        left.add(first);
        dataset1.add(new JScrollPane(q1dataset),BorderLayout.CENTER);
        dataset2.add(new JScrollPane(q2dataset),BorderLayout.CENTER);
        dataset3.add(new JScrollPane(q3dataset),BorderLayout.CENTER);
        dataset4.add(next);
        dataset2.setVisible(false);
        dataset3.setVisible(false);
        right.add(dataset1);
        right.add(dataset2);
        right.add(dataset3);
        right.add(dataset4);
        right.add(last);
        jp.add(left);
        jp.add(right);
        next.addActionListener(this);
        next.setActionCommand("next");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String s = (String) choiceBox.getSelectedItem();
        switch(s) {
            case "Query 1": {
                q1p1.setVisible(true);
                q1p2.setVisible(true);
                q1p3.setVisible(true);
                q1p4.setVisible(true);
                q1p5.setVisible(true);
                q1p6.setVisible(true);
                q1p7.setVisible(true);
                q2p1.setVisible(false);
                q2p2.setVisible(false);
                q3p1.setVisible(false);
                q3p2.setVisible(false);
                q3p3.setVisible(false);
                q3p4.setVisible(false);
                q3p5.setVisible(false);
                q3p6.setVisible(false);
                q3p7.setVisible(false);
                dataset1.setVisible(true);
                dataset2.setVisible(false);
                dataset3.setVisible(false);
                if (q11 == null) {
                    q11 = new HomePage.q1();
                }
                break;
            }
            case "Query 2":
                q1p1.setVisible(false);
                q1p2.setVisible(false);
                q1p3.setVisible(false);
                q1p4.setVisible(false);
                q1p5.setVisible(false);
                q1p6.setVisible(false);
                q1p7.setVisible(false);
                q2p1.setVisible(true);
                q2p2.setVisible(true);
                q3p1.setVisible(false);
                q3p2.setVisible(false);
                q3p3.setVisible(false);
                q3p4.setVisible(false);
                q3p5.setVisible(false);
                q3p6.setVisible(false);
                q3p7.setVisible(false);
                dataset1.setVisible(false);
                dataset2.setVisible(true);
                dataset3.setVisible(false);
                if (q22 == null) {
                    q22 = new HomePage.q2();
                }
                break;
            case "Query 3":
                q1p1.setVisible(false);
                q1p2.setVisible(false);
                q1p3.setVisible(false);
                q1p4.setVisible(false);
                q1p5.setVisible(false);
                q1p6.setVisible(false);
                q1p7.setVisible(false);
                q2p1.setVisible(false);
                q2p2.setVisible(false);
                q3p1.setVisible(true);
                q3p2.setVisible(true);
                q3p3.setVisible(true);
                q3p4.setVisible(true);
                q3p5.setVisible(true);
                q3p6.setVisible(true);
                q3p7.setVisible(true);
                dataset1.setVisible(false);
                dataset2.setVisible(false);
                dataset3.setVisible(true);
                if (q33 == null) {
                    q33 = new HomePage.q3();
                }
                break;
        }
    }

    public static void main(String[] args){
        HomePage p = new HomePage();
    }

}
