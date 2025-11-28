package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class FlightJourney extends JFrame implements ActionListener {

    JFrame f;
    JLabel l1, l2, l3;
    JButton b, b2;
    Choice c1, c2;

    private static final String DB_USER = "sa"; 
    private static final String DB_PASS = "123456"; 

    FlightJourney() {
        f = new JFrame("Select Source & Destination");
        f.setBackground(Color.GREEN);
        f.setLayout(null);


        l2 = new JLabel("Source");
        l2.setVisible(true);
        l2.setBounds(40, 60, 150, 30);
        l2.setForeground(Color.BLACK);
        Font F1 = new Font("Arial", Font.BOLD, 21);
        l2.setFont(F1);
        f.add(l2);

        l3 = new JLabel("Destination");
        l3.setVisible(true);
        l3.setBounds(40, 120, 150, 30);
        l3.setForeground(Color.BLACK);
        l3.setFont(F1);
        f.add(l3);

        c1 = new Choice();
        c1.setBounds(240, 60, 190, 25);
        
        ConnectionClass o1 = null;
        ResultSet r1 = null;
        try {
            o1 = new ConnectionClass(DB_USER, DB_PASS);
            String q = "SELECT DISTINCT sourcee FROM flight";
            r1 = o1.stm.executeQuery(q);
            while (r1.next()) {
                String source = r1.getString("sourcee");
                c1.add(source);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(r1, o1);
        }

        c2 = new Choice();
        c2.setBounds(240, 120, 190, 25);
        
        ConnectionClass o2 = null;
        ResultSet r2 = null;
        try {
            o2 = new ConnectionClass(DB_USER, DB_PASS);
            String q = "SELECT DISTINCT destination FROM flight";
            r2 = o2.stm.executeQuery(q);
            while (r2.next()) {
                c2.add(r2.getString("destination"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
             closeResources(r2, o2);
        }
        
        f.add(c1);
        f.add(c2);
        c1.setFont(F1);
        c2.setFont(F1);

        b = new JButton("Search");
        b.setBounds(140, 185, 100, 30);
        b.addActionListener(this);
        f.add(b);

        b2 = new JButton("Close");
        b2.setBounds(260, 185, 100, 30);
        b2.addActionListener(this);
        b2.setBackground(Color.red);
        b2.setForeground(Color.white);
        f.add(b2);

        f.setSize(500, 270);
        f.setLocation(450, 250);
        f.setVisible(true);
    }
    
    private void closeResources(ResultSet rs, ConnectionClass obj) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (obj != null && obj.con != null) obj.con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b2) {
            f.setVisible(false);
        } 
        if (e.getSource() == b) {
            f.setVisible(false);
            new FlightJourneyDetails(c1.getSelectedItem(), c2.getSelectedItem()); 
        }
    }

//    public static void main(String[] args) {
//        new FlightJourney();
//    }
}