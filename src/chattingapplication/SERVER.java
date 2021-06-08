package chattingapplication;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class SERVER extends JFrame implements ActionListener{
    
    
    JPanel p1;
    JTextField t1;
    JButton b1;
    static JTextArea a1;
    static ServerSocket skt;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    boolean typing;
    
    
    SERVER(){
        p1=new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        add(p1);
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("chattingapplication/MYicons/leftarrow.jpeg"));
        Image i2=i1.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel l1=new JLabel(i3);
        l1.setBounds(5,17, 30,30);     
        p1.add(l1);
        l1.addMouseListener(new MouseAdapter(){
           @Override
            public void mouseClicked(MouseEvent ae){
            System.exit(0);
            }
        });
        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("chattingapplication/MYicons/11.png"));
        Image i5=i4.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel l2=new JLabel(i6);
        l2.setBounds(50,17,30,30);     
        p1.add(l2);
        
        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("chattingapplication/MYicons/video.jpeg"));
        Image i8=i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel l5=new JLabel(i9);
        l5.setBounds(300,17,30,30);     
        p1.add(l5);
        
        
        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("chattingapplication/MYicons/call.jpeg"));
        Image i11=i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);
        JLabel l6=new JLabel(i12);
        l6.setBounds(345,17,30,30);     
        p1.add(l6);
        
        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("chattingapplication/MYicons/3dot.jpeg"));
        Image i14=i13.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);
        JLabel l7=new JLabel(i15);
        l7.setBounds(390,17,30,30);     
        p1.add(l7);
        
        JLabel l3=new JLabel("Paurash");
        l3.setBounds(85,15,100,20);
        l3.setForeground(Color.white);
        p1.add(l3);
        
        JLabel l4=new JLabel("Active Now");
        l4.setBounds(85,30,100,20);
        l4.setFont(new Font("SAN_SERIF",Font.BOLD,10));
        l4.setForeground(Color.white);
        p1.add(l4);
        
        Timer t=new Timer(1,new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                if(!typing){
                l4.setText("Active Now");
                }
            }
        });
        t.setInitialDelay(2000);
        
        t1 =new JTextField();
        t1.setBounds(5, 650,310,40);
        t1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        add(t1);
       
        t1.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
            l4.setText("typing....");
            t.stop();
            typing=true;
            }
            public void keyReleased(KeyEvent ke){
                typing=false;
                if(!t.isRunning()){
                t.start();
            }
            }
        });
        
        a1=new JTextArea();
        a1.setBounds(5,75,440,570);
        a1.setBackground(Color.white);
        a1.setFont(new Font("SAN_SARIF",Font.PLAIN,16));
        a1.setLineWrap( true);
        a1.setWrapStyleWord(true);
        a1.setEditable(false);
        
        add(a1);
        
        b1=new JButton("sent");
        b1.setBounds(320,650, 120,40);
        b1.setBackground( new Color(7,94,84));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("SAN_SERIF",Font.PLAIN,18));
        b1.addActionListener(this);

        add(b1);
        
         
        
        
        setLayout(null);
        setSize(450,700);
        setLocation(200,50);
        setUndecorated(true);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
       try{
           String out=t1.getText();
            a1.setText(a1.getText()+"\n\t\t\t"+out);
            dout.writeUTF(out);
            t1.setText("");
       }catch(Exception e){}
        
    }
    public static void main(String args[]){
        int i=0;
        new SERVER().setVisible(true);
        String msginput="";
        try{
            skt =new ServerSocket(7001);
            s= skt.accept();
            din=new DataInputStream(s.getInputStream());
            dout=new DataOutputStream(s.getOutputStream());
            while(i<5)
            {
            msginput=din.readUTF();
            a1.setText(a1.getText()+"\n"+msginput);
            }
            skt.close();
            s.close();   
        }catch(Exception e){}
    }
}
