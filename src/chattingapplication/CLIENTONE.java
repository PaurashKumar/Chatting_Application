/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chattingapplication;

/**
 *
 * @author paurash
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.io.File;
public class CLIENTONE extends JFrame{
    
    JPanel p1;
    JTextField t1;
    JButton b1,b2;
    static JTextArea a1;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    final File[] filetosend=new File[1];
    CLIENTONE(){
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
        
        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("chattingapplication/MYicons/Tehan1.jpeg"));
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
        
        JLabel l3=new JLabel("Tehan");
        l3.setBounds(85,15,100,20);
        l3.setForeground(Color.white);
        p1.add(l3);
        
        JLabel l4=new JLabel("Active Now");
        l4.setBounds(85,30,100,20);
        l4.setFont(new Font("SAN_SERIF",Font.BOLD,10));
        l4.setForeground(Color.white);
        p1.add(l4);
        
        a1=new JTextArea();
        a1.setBounds(5,75,440,570);
        a1.setBackground(Color.LIGHT_GRAY);
        a1.setFont(new Font("SAN_SARIF",Font.PLAIN,16));
        a1.setLineWrap( true);
        a1.setWrapStyleWord(true);
        a1.setEditable(false);
        add(a1);
        
        b1=new JButton("CHOOSE FILE");
        b1.setBounds(40,650, 160,40);
        b1.setBackground( new Color(7,94,84));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("SAN_SERIF",Font.PLAIN,18));
        
        add(b1);
        
        b2=new JButton("SEND FILE");
        b2.setBounds(250,650,160,40);
        b2.setBackground(new Color(7,94,84));
        b2.setForeground(Color.white);
        b2.setFont(new Font("SAN_SARIF",Font.PLAIN,18));
        add(b2);
        
        JLabel FN=new JLabel("Choose A File to Send");
        FN.setBounds(100, 400, 300, 40);
        FN.setFont(new Font("SAN_SERIF",Font.BOLD,20));
        FN.setForeground(Color.BLACK);
        a1.add(FN);
        
                
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JFileChooser jfilechoose=new JFileChooser();
                jfilechoose.setDialogTitle("Choose A File To Send");
                
                if(jfilechoose.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                    filetosend[0]=jfilechoose.getSelectedFile();
                    FN.setText("Your Selected File Is : "+ filetosend[0].getName());
                }
            }
            
        });
        b2.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                if(filetosend[0]==null){
                    FN.setText("Please Select A File First");
                }else{
                    try{
                    FileInputStream fileinputstream=new FileInputStream(filetosend[0].getAbsolutePath());
                    Socket socket=new Socket("localhost",1598);
                    
                    DataOutputStream dataoutputstream=new DataOutputStream(socket.getOutputStream());
                    
                    String filename=filetosend[0].getName();
                    byte[] filenamebytes=filename.getBytes();
                    
                    byte[] filecontentbytes=new byte[(int) filetosend[0].length()];
                    fileinputstream.read(filecontentbytes);
                    
                    
                    dataoutputstream.writeInt(filenamebytes.length);
                    dataoutputstream.write(filenamebytes);
                    
                    dataoutputstream.writeInt(filecontentbytes.length);
                    dataoutputstream.write(filecontentbytes);
                    
                    }catch(IOException error){
                        error.printStackTrace();
                    }
                }
            }
        
        
        });
        
        
        setLayout(null);
        setSize(450,700);
        setLocation(850,50);
        setUndecorated(true);
        setVisible(true);
    }
    public static void main(String[] args){
        new CLIENTONE().setVisible(true);  
    }
}
