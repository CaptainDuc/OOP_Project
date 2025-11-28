package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
public class HomePage extends JFrame implements ActionListener{
    private String currentUsername;
    Font f,f1,f2;
    JLabel l1,l2;
    
    
    HomePage(String username){
        super("Airlines VN Home Page");
        setLocation(0,0);
        setSize(1550,800);
        f =new Font ("Arial",Font.BOLD,20);
        f2 =new Font ("Time New Roman",Font.BOLD,35);
        f1 =new Font ("Gadugi",Font.BOLD,18);
          
        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource(""));
        Image img1 = img.getImage().getScaledInstance(620, 470, Image.SCALE_SMOOTH);
        ImageIcon ic1 = new ImageIcon(img1);
        l1 = new JLabel(ic1); 
          
        JMenuBar m1 = new JMenuBar();  
        JMenu me1 = new JMenu("Passenger Profile");
        JMenuItem mi1 = new JMenuItem("Add Passenger Profile");
        JMenuItem mi2 = new JMenuItem("View Passenger Profile");
        me1.add(mi1);
        me1.add(mi2);
        m1.add(me1);
        JMenu me2 = new JMenu("Manager Passenger");
        JMenuItem mi3 = new JMenuItem("Update Passenger Profile");
        me2.add(mi3);
        m1.add(me2);
        JMenu me3 = new JMenu("Your Flight");
        JMenuItem mi5 = new JMenuItem("Book Flight");
        JMenuItem mi6 = new JMenuItem("View Booked Flight");
        me3.add(mi5);
        me3.add(mi6);
        m1.add(me3);
         JMenu me4 = new JMenu("Flight Details");
        JMenuItem mi7 = new JMenuItem("Journey Details");
        JMenuItem mi8 = new JMenuItem("Flight Zone");
        me4.add(mi7);
        me4.add(mi8);
        m1.add(me4);
         JMenu me5 = new JMenu("Cancellation");
        JMenuItem mi9 = new JMenuItem("Cancel Ticket");
        JMenuItem mi10 = new JMenuItem("View Cancelled Ticket");
        me5.add(mi9);
        me5.add(mi10);
        m1.add(me5);
        JMenu me6 = new JMenu("Bill");
        JMenuItem mi11 = new JMenuItem("Check Payment");
        me6.add(mi11);
        m1.add(me6);
        JMenu me8 = new JMenu("Logout");
        JMenuItem mi14 = new JMenuItem("Exit");
        me8.add(mi14);
        m1.add(me8);
        
        me1.setFont(f);
        me2.setFont(f);
        me3.setFont(f);
        me4.setFont(f);
        me5.setFont(f);
        me6.setFont(f);
        me8.setFont(f);
        
        mi1.setFont(f1);
        mi2.setFont(f1);
        mi3.setFont(f1);
       
        mi5.setFont(f1);
        mi6.setFont(f1);
        mi7.setFont(f1);
        mi8.setFont(f1);
        mi9.setFont(f1);
        mi10.setFont(f1);
        mi11.setFont(f1);
        mi14.setFont(f1);
        
        m1.setBackground(new java.awt.Color(4,1,54));
        
        me1.setForeground(Color.GRAY);
        me2.setForeground(Color.GRAY);
        me3.setForeground(Color.GRAY);
        me4.setForeground(Color.GRAY);
        me5.setForeground(Color.GRAY);
        me6.setForeground(Color.GRAY);
        me8.setForeground(Color.RED);
        
        
       mi1.setBackground(Color.BLACK);
       mi2.setBackground(Color.BLACK);
       mi3.setBackground(Color.BLACK);
       mi5.setBackground(Color.BLACK);
       mi6.setBackground(Color.BLACK);
       mi7.setBackground(Color.BLACK);
       mi8.setBackground(Color.BLACK);
       mi9.setBackground(Color.BLACK);
       mi10.setBackground(Color.BLACK);
       mi11.setBackground(Color.BLACK);
       mi14.setBackground(Color.BLACK);
       
       mi1.setForeground(Color.YELLOW);
       mi2.setForeground(Color.YELLOW);
       mi3.setForeground(Color.YELLOW);
       mi5.setForeground(Color.YELLOW);
       mi6.setForeground(Color.YELLOW);
       mi7.setForeground(Color.YELLOW);
       mi8.setForeground(Color.YELLOW);
       mi9.setForeground(Color.YELLOW);
       mi10.setForeground(Color.YELLOW);
       mi11.setForeground(Color.YELLOW);
       mi14.setForeground(Color.YELLOW);
        
    
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
        
        this.currentUsername = username;
        
        
        setJMenuBar(m1);
        add(l1);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
         
    }
    public void actionPerformed(ActionEvent e){
        String cmd =e.getActionCommand();
        if (cmd.equals("Add Passenger Profile")){
            new AddPassengerDetails();
        }
        else if(cmd.equals("View Passenger Profile")){
            new ViewPassenger().setVisible(true);
        }
        else if(cmd.equals("Update Passenger Profile")){
            new UpdatePassenger().setVisible(true);
        }
        else if(cmd.equals("Book Flight")){
            new BookFlight(currentUsername).setVisible(true);
        }
        else if(cmd.equals("View Booked Flight")){
            new ViewBookedFlight(currentUsername).setVisible(true);
        }
        else if(cmd.equals("Journey Details")){
            new FlightJourney().setVisible(true);
        }
        else if(cmd.equals("Flight Zone")){
            new FlightZone().setVisible(true);
        }
        else if(cmd.equals("Cancel Ticket")){
            new CancelTicket(currentUsername).setVisible(true);
        }
        else if(cmd.equals("View Cancelled Ticket")){
            new ViewCancelTicket(currentUsername).setVisible(true);
        }
        else if(cmd.equals("Check Payment")){
            new CheckPaymentDetails().setVisible(true);
        }
        else if(cmd.equals("Exit")){
            System.exit(0);
        }
    }
//    public static void main(String[]args){
//        new HomePage(UserEx).setVisible(true);
//    }
}
