package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class CheckPaymentDetails extends JFrame {
    JTextField textField;
    JTable table;
    JLabel sector;
    JLabel FlightCode, Capacity, Classname, Classcode, label;
    
    private static final String DB_USER = "sa"; 
    private static final String DB_PASS = "123456"; 

    public CheckPaymentDetails() {
        initialize();
    }

    private void initialize() {
        setTitle("Payment Details");
        getContentPane().setBackground(Color.WHITE);
        setSize(960, 590);
        setLocation(40, 20);
        setLayout(null);

        
        JLabel Fcode = new JLabel("Username");
        Fcode.setFont(new Font("Arial", Font.BOLD, 16));
        Fcode.setBounds(190, 160, 150, 26);
        add(Fcode);

        textField = new JTextField();
        textField.setBounds(300, 160, 150, 26);
        textField.setFont(new Font("Arial", Font.BOLD, 14));
        add(textField);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(93, 297, 766, 87);
        add(scrollPane);

        JButton Show = new JButton("Show");
        Show.setFont(new Font("Arial", Font.BOLD, 14));
        Show.setBackground(Color.BLACK);
        Show.setForeground(Color.WHITE);
        Show.setBounds(500, 160, 150, 26);
        add(Show);

        sector = new JLabel("Check Payment Details");
        sector.setForeground(Color.BLUE);
        sector.setFont(new Font("Arial", Font.BOLD, 33));
        sector.setBounds(291, 17, 800, 39);
        add(sector);

        FlightCode = new JLabel("Ticket ID");
        FlightCode.setFont(new Font("Arial", Font.BOLD, 14));
        FlightCode.setBounds(117, 262, 108, 26);
        add(FlightCode);

        Capacity = new JLabel("Price");
        Capacity.setFont(new Font("Arial", Font.BOLD, 14));
        Capacity.setBounds(237, 268, 38, 14);
        add(Capacity);

        Classcode = new JLabel("Journey Date");
        Classcode.setFont(new Font("Arial", Font.BOLD, 14));
        Classcode.setBounds(362, 264, 101, 24);
        add(Classcode);

        Classname = new JLabel("Journey Time");
        Classname.setFont(new Font("Arial", Font.BOLD, 14));
        Classname.setBounds(485, 268, 110, 14);
        add(Classname);

        JLabel card = new JLabel("Username");
        card.setFont(new Font("Arial", Font.BOLD, 14));
        card.setBounds(620, 269, 101, 19);
        add(card);

        JLabel phone = new JLabel("Status");
        phone.setFont(new Font("Arial", Font.BOLD, 14));
        phone.setBounds(752, 264, 86, 24);
        add(phone);

        label = new JLabel("");
        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource(""));
        Image img1 = img.getImage().getScaledInstance(960, 590, Image.SCALE_SMOOTH);
        ImageIcon ic1 = new ImageIcon(img1);
        label.setIcon(ic1);
        label.setBounds(0, 0, 960, 590);
        add(label);
        
        Show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                String u = textField.getText();
                
                if (u.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập Username.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                ConnectionClass o = null;
                PreparedStatement pst = null;
                ResultSet rs = null;
                try {
                    o = new ConnectionClass(DB_USER, DB_PASS);
                    
                    String s = "SELECT tid, price, journey_date, journey_time, username, statuss " +
                               "FROM bookedFlight WHERE username = ? AND statuss = 'success'";
                    
                    pst = o.con.prepareStatement(s);
                    pst.setString(1, u);
                    rs = pst.executeQuery();
                    
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    table.setFont(new Font("Arial", Font.BOLD, 14));
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi truy vấn cơ sở dữ liệu: " + e.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
                } finally {
                    closeResources(rs, o, pst);
                }
            }
        });
        
        setVisible(true);
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
//        new CheckPaymentDetails();
//    }
}