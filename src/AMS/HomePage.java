package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class HomePage extends JFrame implements ActionListener{
    
    private final Font F_MENU = new Font("Arial", Font.BOLD, 16);
    private final Font F_ITEM = new Font("Arial", Font.PLAIN, 14);
    private final Font F_WELCOME = new Font("Arial", Font.BOLD, 48);
    private final Color BG_MENU = new Color(4, 1, 54);
    private final Color FG_MENU = Color.WHITE;
    private final Color FG_ACCENT = new Color(255, 193, 7);

    private String currentUsername;
    
    HomePage(String username){
        super("Hệ thống Hàng không Việt Nam (Airlines VN)");
        this.currentUsername = username;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocation(0,0);
        setLayout(new BorderLayout()); 
        getContentPane().setBackground(Color.WHITE);

        JLabel lWelcome = new JLabel("Chào mừng, " + username + "! Chúc bạn có một ngày mới tuyệt vời!.", SwingConstants.CENTER);
        lWelcome.setFont(F_WELCOME);
        lWelcome.setForeground(BG_MENU); 
        
        JPanel pCenter = new JPanel(new GridBagLayout());
        pCenter.setBackground(Color.WHITE);
        pCenter.add(lWelcome);
        add(pCenter, BorderLayout.CENTER);

        JMenuBar m1 = new JMenuBar();
        m1.setBackground(BG_MENU);
        
        JMenu me1 = new JMenu("Hồ sơ Hành khách");
        JMenuItem mi1 = new JMenuItem("Thêm Hồ sơ");
        JMenuItem mi2 = new JMenuItem("Xem Hồ sơ");
        me1.add(mi1);
        me1.add(mi2);
        
        JMenu me2 = new JMenu("Quản lý Hành khách");
        JMenuItem mi3 = new JMenuItem("Cập nhật Hồ sơ");
        me2.add(mi3);
        
        JMenu me3 = new JMenu("Chuyến bay của bạn");
        JMenuItem mi5 = new JMenuItem("Đặt vé máy bay");
        JMenuItem mi6 = new JMenuItem("Xem vé đã đặt");
        me3.add(mi5);
        me3.add(mi6);
        
        JMenu me4 = new JMenu("Chi tiết Chuyến bay");
        JMenuItem mi7 = new JMenuItem("Chi tiết Hành trình");
        JMenuItem mi8 = new JMenuItem("Khu vực Bay (Flight Zone)");
        me4.add(mi7);
        me4.add(mi8);
        
        JMenu me5 = new JMenu("Hủy vé");
        JMenuItem mi9 = new JMenuItem("Hủy vé máy bay");
        JMenuItem mi10 = new JMenuItem("Xem vé đã hủy");
        me5.add(mi9);
        me5.add(mi10);
        
        JMenu me6 = new JMenu("Thanh toán");
        JMenuItem mi11 = new JMenuItem("Kiểm tra Thanh toán");
        me6.add(mi11);
        
        JMenu me8 = new JMenu("Thoát/Đăng xuất");
        JMenuItem mi14 = new JMenuItem("Thoát chương trình");
        me8.add(mi14);
        
        m1.add(me1);
        m1.add(me2);
        m1.add(me3);
        m1.add(me4);
        m1.add(me5);
        m1.add(me6);
        m1.add(me8);
        
        JMenu[] menus = {me1, me2, me3, me4, me5, me6, me8};
        for (JMenu menu : menus) {
            menu.setFont(F_MENU);
            menu.setForeground(FG_MENU);
        }
        
        JMenuItem[] items = {mi1, mi2, mi3, mi5, mi6, mi7, mi8, mi9, mi10, mi11, mi14};
        for (JMenuItem item : items) {
            item.setFont(F_ITEM);
            item.setBackground(BG_MENU); 
            item.setForeground(FG_ACCENT); 
        }

        me8.setForeground(Color.RED); 
        mi14.setForeground(Color.RED);
        
        setJMenuBar(m1);

        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi5.addActionListener(this);
        mi6.addActionListener(this);
        mi7.addActionListener(this);
        mi8.addActionListener(this);
        mi9.addActionListener(this);
        mi10.addActionListener(this);
        mi11.addActionListener(this);
        mi14.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){
        String cmd =e.getActionCommand();
        
        if (cmd.equals("Thêm Hồ sơ")){
             new AddPassengerDetails();
        }
        else if(cmd.equals("Xem Hồ sơ")){
             new ViewPassenger().setVisible(true);
        }
        else if(cmd.equals("Cập nhật Hồ sơ")){
             new UpdatePassenger().setVisible(true);
        }
        else if(cmd.equals("Đặt vé máy bay")){
             new BookFlight(currentUsername).setVisible(true);
        }
        else if(cmd.equals("Xem vé đã đặt")){
             new ViewBookedFlight(currentUsername).setVisible(true);
        }
        else if(cmd.equals("Chi tiết Hành trình")){
             new FlightJourney();
        }
        else if(cmd.equals("Khu vực Bay (Flight Zone)")){
             new FlightZone().setVisible(true);
        }
        else if(cmd.equals("Hủy vé máy bay")){
             new CancelTicket(currentUsername).setVisible(true);
        }
        else if(cmd.equals("Xem vé đã hủy")){
             new ViewCancelTicket(currentUsername).setVisible(true);
        }
        else if(cmd.equals("Kiểm tra Thanh toán")){
             new CheckPaymentDetails().setVisible(true);
        }
        else if(cmd.equals("Thoát chương trình")){
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát khỏi ứng dụng?", "Xác nhận Thoát", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
    
    public static void main(String[]args){
        SwingUtilities.invokeLater(() -> new HomePage("UserEx").setVisible(true));
    }
}