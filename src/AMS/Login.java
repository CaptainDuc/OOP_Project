package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.SQLIntegrityConstraintViolationException;

public class Login extends JFrame implements ActionListener{
    JLabel l1, l2, l3,l4;
    JButton bt1, bt2;
    JPasswordField pf;
    JTextField tf;
    JFrame f;
    
    public Login(){
        f = new JFrame("Login Account");
        f.setBackground(Color.WHITE);
        f.setLayout(null);
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        l1 = new JLabel();
        l1.setBounds(0,0,580,350);
        l1.setLayout(null);
        
        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource(""));
        Image i1 = img.getImage().getScaledInstance(580,350, Image.SCALE_SMOOTH);
        ImageIcon img2 = new ImageIcon(i1);
        l1.setIcon(img2);
        
        l2 = new JLabel("Username");
        l2.setBounds(120,120,150,30);
        l2.setForeground(Color.BLACK);
        l2.setFont(new Font("Arial", Font.BOLD,20));
        l1.add(l2);
        f.add(l1);
        
        l3 = new JLabel("Login Account");
        l3.setBounds(190,30,500,50);
        l3.setForeground(Color.BLACK);
        l3.setFont(new Font("Arial", Font.BOLD,30));
        l1.add(l3);
        
        l4 = new JLabel("Password");
        l4.setBounds(120,170,150,30);
        l4.setForeground(Color.BLACK);
        l4.setFont(new Font("Arial", Font.BOLD,20));
        l1.add(l4);
        f.add(l1);
        
        tf = new JTextField();
        tf.setBounds(320,120,150,30);
        l1.add(tf);
        
        pf = new JPasswordField();
        pf.setBounds(320,170,150,30);
        l1.add(pf);
        
        bt1 = new JButton("Đăng nhập");
        bt1.setBackground(Color.BLACK);
        bt1.setForeground(Color.WHITE);
        bt1.setBounds(120,220,150,40);
        l1.add(bt1);
        
        bt2 = new JButton("Đăng ký");
        bt2.setBackground(Color.BLACK);
        bt2.setForeground(Color.WHITE);
        bt2.setBounds(320,220,150,40);
        l1.add(bt2);
        
        bt1.addActionListener(this);
        bt2.addActionListener(this);
        
        f.setVisible(true);
        f.setSize(580,350);
        f.setLocation(300,100);
    }

    private void closeResources(ResultSet rs, PreparedStatement pst, ConnectionClass obj) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException closeEx) {
            closeEx.printStackTrace();
        }
        try {
            if (pst != null) pst.close();
        } catch (SQLException closeEx) {
            closeEx.printStackTrace();
        }
        try {
            if (obj != null && obj.con != null) obj.con.close();
        } catch (SQLException closeEx) {
            closeEx.printStackTrace();
        }
    }
 
    public void actionPerformed(ActionEvent e) {
    ConnectionClass obj = null; 
    PreparedStatement pst = null;
    ResultSet rs = null;

    if(e.getSource() == bt1){
        String username = tf.getText();
        String pass = new String(pf.getPassword());
        
        if (username.isEmpty() || pass.isEmpty()) {
             JOptionPane.showMessageDialog(f, "Vui lòng nhập Tên đăng nhập và Mật khẩu.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
             return;
        }

        try{
            obj = new ConnectionClass("sa", "123456"); 
            
            if (obj.con == null) {
                JOptionPane.showMessageDialog(f, "Lỗi kết nối CSDL. Vui lòng kiểm tra Server.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String g = "SELECT * FROM signup WHERE username=? AND passwordd=?";
            pst = obj.con.prepareStatement(g);
            pst.setString(1, username);
            pst.setString(2, pass);
            
            rs = pst.executeQuery(); 
            
            if (rs.next()){
                String usernamee = tf.getText();
                new HomePage(usernamee).setVisible(true); 
                f.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(f,"Sai tên đăng nhập hoặc mật khẩu. Vui lòng thử lại.");
            }
            
        } catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(f, "Lỗi đăng nhập: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeResources(rs, pst, obj);
        }
    }

    if(e.getSource() == bt2){
        String username = tf.getText();
        String pass = new String(pf.getPassword());
        
        if (username.isEmpty() || pass.isEmpty()) {
             JOptionPane.showMessageDialog(f, "Vui lòng nhập tên đăng nhập và mật khẩu vào trường trước khi Đăng ký.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
             return;
        }

        String name = JOptionPane.showInputDialog(f, "Nhập họ và tên:"); 
        if (name == null || name.trim().isEmpty()) {
             JOptionPane.showMessageDialog(f, "Tên không được để trống.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
             return;
        }
        
        String phone = JOptionPane.showInputDialog(f, "Nhập số điện thoại:"); 
        if (phone == null || phone.trim().isEmpty()) {
             JOptionPane.showMessageDialog(f, "Số điện thoại không được để trống.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
             return;
        }

        try{
            obj = new ConnectionClass("sa", "123456");
            
            if (obj.con == null) {
                JOptionPane.showMessageDialog(f, "Lỗi kết nối CSDL. Vui lòng kiểm tra Server.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "INSERT INTO signup(username, namee, passwordd, phone) VALUES(?,?,?,?)";

            pst = obj.con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, name);
            pst.setString(3, pass);
            pst.setString(4, phone);

            int row = pst.executeUpdate();    
            if(row > 0){
                JOptionPane.showMessageDialog(f, "Đăng ký thành công!");
            } else {
                JOptionPane.showMessageDialog(f, "Đăng ký thất bại!");
            }

        } catch(SQLIntegrityConstraintViolationException ex) {
             JOptionPane.showMessageDialog(f, "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác.", "Lỗi Đăng ký", JOptionPane.ERROR_MESSAGE);
        } catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(f, "Lỗi khi đăng ký: "+ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeResources(null, pst, obj);
        }
    }
}
    public static void main(String[] args){
        new Login();
    }
    
}