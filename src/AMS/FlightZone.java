package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class FlightZone extends JFrame {
    private JTable table;
    private JTextField t;
    Choice c1;

    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123456";

    FlightZone() {
        getContentPane().setBackground(new java.awt.Color(77, 157, 227));
        getContentPane().setFont(new Font("Arial", Font.BOLD, 18));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650); 
        setLocation(100, 50);
        setLayout(null);

        JLabel flightCode = new JLabel("Flight Code");
        flightCode.setFont(new Font("Arial", Font.BOLD, 18));
        flightCode.setBounds(100, 100, 150, 30);
        flightCode.setForeground(new Color(15, 11, 1));
        add(flightCode);

        JLabel flightDetails = new JLabel("Air Flight Information");
        flightDetails.setFont(new Font("Arial", Font.BOLD, 33));
        flightDetails.setBounds(250, 20, 570, 33);
        flightDetails.setForeground(new Color(15, 11, 1));
        add(flightDetails);

        table = new JTable();
        table.setBackground(Color.WHITE);
        table.setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(23, 250, 850, 350); 
        add(scrollPane);

        c1 = new Choice();
        c1.setBounds(290, 103, 200, 35);
        c1.setFont(new Font("Arial", Font.BOLD, 14));
        ConnectionClass o = null;
        ResultSet r = null;
        try {
            o = new ConnectionClass(DB_USER, DB_PASS);
            String s = "SELECT DISTINCT fcode FROM flight";
            r = o.stm.executeQuery(s);
            while (r.next()) {
                c1.add(r.getString("fcode"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(r, o);
        }
        add(c1);
        
        JButton b = new JButton("Show Details");
        b.setFont(new Font("Arial", Font.BOLD, 20));
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConnectionClass c = null;
                PreparedStatement pst = null;
                ResultSet r = null;
                try {
                    String code = c1.getSelectedItem();
                    if (code == null) {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn mã chuyến bay.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    c = new ConnectionClass(DB_USER, DB_PASS);
                    
                    String s1 = "SELECT fcode, fname, sourcee, destination, journey_date, journey_time, classname, price " +
                                "FROM flight WHERE fcode = ?";
                    pst = c.con.prepareStatement(s1);
                    pst.setString(1, code);
                    r = pst.executeQuery();
                    
                    table.setModel(DbUtils.resultSetToTableModel(r));
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi truy vấn CSDL: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                } finally {
                    closeResources(r, c, pst);
                }
            }
        });
        b.setBounds(560, 100, 220, 30);
        add(b);

        JLabel flight = new JLabel("Flight code");
        flight.setFont(new Font("Arial", Font.BOLD, 14));
        flight.setBounds(33, 220, 126, 16);
        flight.setForeground(new Color(15, 11, 1));
        add(flight);

        JLabel flightName = new JLabel("Flight Name");
        flightName.setFont(new Font("Arial", Font.BOLD, 14));
        flightName.setBounds(155, 220, 120, 16);
        flightName.setForeground(new Color(15, 11, 1));
        add(flightName);

        JLabel source = new JLabel("Source");
        source.setFont(new Font("Arial", Font.BOLD, 14));
        source.setBounds(275, 220, 104, 16);
        source.setForeground(new Color(15, 11, 1));
        add(source);

        JLabel destination = new JLabel("Destination");
        destination.setFont(new Font("Arial", Font.BOLD, 14));
        destination.setBounds(380, 220, 120, 16);
        destination.setForeground(new Color(15, 11, 1));
        add(destination);
        
        JLabel date = new JLabel("J. Date"); 
        date.setFont(new Font("Arial", Font.BOLD, 14));
        date.setBounds(497, 220, 70, 15);
        date.setForeground(new Color(15, 11, 1));
        add(date);

        JLabel time = new JLabel("J. Time"); 
        time.setFont(new Font("Arial", Font.BOLD, 14));
        time.setBounds(567, 220, 70, 15);
        time.setForeground(new Color(15, 11, 1));
        add(time);
        
        JLabel className = new JLabel("Class Name");
        className.setFont(new Font("Arial", Font.BOLD, 14));
        className.setBounds(650, 220, 120, 16);
        className.setForeground(new Color(15, 11, 1));
        add(className);

        JLabel price = new JLabel("Price");
        price.setFont(new Font("Arial", Font.BOLD, 14));
        price.setBounds(780, 220, 111, 16);
        price.setForeground(new Color(15, 11, 1));
        add(price);

        setVisible(true);
    }
    
    private void closeResources(ResultSet rs, ConnectionClass obj) {
        closeResources(rs, obj, null);
    }

    private void closeResources(ResultSet rs, ConnectionClass obj, PreparedStatement pst) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pst != null) pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (obj != null && obj.con != null) obj.con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    public static void main(String[] args) {
//        new FlightZone().setVisible(true);
//    }
}