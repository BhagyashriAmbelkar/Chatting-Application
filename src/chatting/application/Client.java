
package chatting.application;

import javax.swing.*; 
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Client implements ActionListener {
    
    
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f =new JFrame();
    static DataOutputStream dout;

    
    Client(){
        
        f.setLayout(null);
        
        JPanel p1 = new JPanel(); //use to create panel above white frame
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0, 0, 500, 70);
        p1.setLayout(null);//we are setting it manually
        f.add(p1);
        
        //to get arrow image icon on the panel
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        //for arrow icon
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        
        //for adding click event when we click on arrow system should close
        back.addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent ae){
             System.exit(0);
         }
    });
        //for profile image
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        //for profile icon
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);
        
        //for video icon
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        //for profile icon
        video.setBounds(250, 20, 30, 30);
        p1.add(video);
        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        //for profile icon
        phone.setBounds(300, 20, 35, 30);
        p1.add(phone);
        
        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 28, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        //for profile icon
        morevert .setBounds(350, 20, 10, 28);
        p1.add(morevert );
        
        
        JLabel name = new JLabel("Bunty");
        name.setBounds(110, 15, 100, 20);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);
        
        
        JLabel status = new JLabel("Online");
        status.setBounds(110, 35, 100, 20);
        status.setForeground(Color.white);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(status);
        
        //for creating panel under green panel
        a1 = new JPanel();
        a1.setBounds(5,75,390,480);
        f.add(a1);
        
        //for creating text field
        text = new JTextField();
        text.setBounds(5,560,300,35);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN,16));
        f.add(text);
        
        JButton send = new JButton("Send");
        send.setBounds(310, 560, 80, 35);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN,16));
        f.add(send);
        
        f.setLocation(700,50);
        f.getContentPane().setBackground(Color.WHITE);
        f.setSize(400,600); //for frame
        f.setUndecorated(true);
        
        f.setVisible(true);
    }
    //to display message on the frame
    public void actionPerformed(ActionEvent ae){
        try{
        String out = text.getText();
        
        JPanel p2 = formatLabel(out);
        
        
        a1.setLayout(new BorderLayout());
        
        JPanel right =new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        
        a1.add(vertical,BorderLayout.PAGE_START);
       
        dout.writeUTF(out);
        text.setText("");//to clear the textfield after sending one message
        
    //used to reload the frame after clicking on send button
        f.repaint();
        f.invalidate();
        f.validate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width:150px\">" + out + "</html>");
        output.setFont(new Font("Arial",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        
        return panel;
    
    
    }
    public static void main(String[]args){
        new Client();
        
        try{
        Socket s = new Socket("127.0.0.1",6000);//make connection between server and socket
        DataInputStream din =new DataInputStream(s.getInputStream());
         dout = new DataOutputStream(s.getOutputStream());
         
         while(true){
            a1.setLayout(new BorderLayout());
            String msg = din.readUTF();
            JPanel panel = formatLabel(msg);
                    
            JPanel left = new JPanel(new BorderLayout());
            left.add(panel,BorderLayout.LINE_START);
            vertical.add(left);
            
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical,BorderLayout.PAGE_START);
            f.validate();
                }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

