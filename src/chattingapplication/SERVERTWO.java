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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
public class SERVERTWO {
    static ArrayList<Myfile> myfiles=new ArrayList<>();
    public static void main(String[] args) throws IOException{
        
    int fileid=0;
    JFrame jframe=new JFrame("TEHAN FILE RECEIVER");
    jframe.setSize(400,400);
    jframe.setBackground(Color.LIGHT_GRAY);
    jframe.setLayout(new BoxLayout(jframe.getContentPane(),BoxLayout.Y_AXIS));
    jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
    
    JPanel jpanel=new JPanel();
    jpanel.setLayout(new BoxLayout(jpanel,BoxLayout.Y_AXIS));
    
    
    JScrollPane jscrollpane=new JScrollPane(jpanel);
    jscrollpane.setVerticalScrollBarPolicy(jscrollpane.VERTICAL_SCROLLBAR_ALWAYS);
    
    
    JLabel jltitle=new JLabel("FILE RECEIVERED BY TEHAN ");
    jltitle.setFont(new Font("Arial",Font.BOLD,25));
    jltitle.setBorder(new EmptyBorder(20,0,10,0));
    jltitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    
    jframe.add(jltitle);
    jframe.add(jscrollpane);
    jframe.setVisible(true);
    
    
    
    ServerSocket serversocket=new ServerSocket(1599);
    
    while(true){
     
        try{
            Socket socket=serversocket.accept();
            DataInputStream datainputstream=new DataInputStream(socket.getInputStream());
            
            int filenamelength=datainputstream.readInt();
            
            if(filenamelength>0){
                byte[] filenamebyte=new byte[filenamelength];
                datainputstream.readFully(filenamebyte, 0, filenamebyte.length);
                String filename=new String(filenamebyte);
                
                int filecontentlength=datainputstream.readInt();
                
                
                if(filecontentlength>0){
                    byte[] filecontentbyte=new byte[filecontentlength];
                    datainputstream.readFully(filecontentbyte,0,filecontentlength);
                    
                    
                    
                    
                    JPanel jpfilerow=new JPanel();
                    jpfilerow.setLayout(new BoxLayout(jpfilerow,BoxLayout.Y_AXIS));
    
                    JLabel jlfilename=new JLabel(filename);
                    jlfilename.setFont(new Font("Arial",Font.BOLD,20));
                    jlfilename.setBorder(new EmptyBorder(10,0,10,0));
                    
                    
                    if(getFileExtension(filename).equalsIgnoreCase("txt")){
                        jpfilerow.setName(String.valueOf(fileid));
                        jpfilerow.addMouseListener(getMyMouseListener());
                        
                        jpfilerow.add(jlfilename);
                        jpanel.add(jpfilerow);
                        jframe.validate();
                    }else{
                        jpfilerow.setName(String.valueOf(fileid));
                        jpfilerow.addMouseListener(getMyMouseListener());
                        jpfilerow.add(jlfilename);
                        jpanel.add(jpfilerow);
                        jframe.validate();
                    }
                 
                    myfiles.add(new Myfile(fileid,filename,filecontentbyte,getFileExtension(filename)));
                }
                 
            }
        
        }catch(IOException ee){
            ee.printStackTrace();
        }
    }
    }
    
    public static MouseListener getMyMouseListener(){
    
        return new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {
                //To change body of generated methods, choose Tools | Templates
                JPanel jpanel=(JPanel)me.getSource();
                
                int fileid=Integer.parseInt(jpanel.getName());
                
                for(Myfile myfile:myfiles){
                    if(myfile.getId()==fileid){
                        JFrame jfpreview=createFrame(myfile.getName(),myfile.getData(),myfile.getFileExtension());
                        jfpreview.setVisible(true);
                    }
                }
                
            }

            @Override
            public void mousePressed(MouseEvent me) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                  //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                  //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent me) {
                 //To change body of generated methods, choose Tools | Templates.
            }
            
        };
            
    
    }
    
    public static JFrame createFrame(String filename,byte[] filedata,String fileextension){
        
        JFrame jframe=new JFrame("File Downloader");
        jframe.setSize(400,400);
        
        JPanel jpanel=new JPanel();
        jpanel.setLayout(new BoxLayout(jpanel,BoxLayout.Y_AXIS));
        
        
        JLabel jltitle=new JLabel("File Downloader");
        jltitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        jltitle.setFont(new Font("Arial",Font.BOLD,25));
        jltitle.setBorder(new EmptyBorder(20,0,10,0));
        
        JLabel jlprompt=new JLabel("Are You sure ? you want to download "+ filename);
        jlprompt.setFont(new Font("Arial",Font.BOLD,20));
        jlprompt.setBorder(new EmptyBorder(20,0,10,0));
        jlprompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton jbyes=new JButton("Yes");
        jbyes.setPreferredSize(new Dimension(150,75));
        jbyes.setFont(new Font("Arial",Font.BOLD,20));
        
        JButton jbno=new JButton("No");
        jbno.setPreferredSize(new Dimension(150,75));
        jbno.setFont(new Font("Arial",Font.BOLD,20));
        
        JLabel jlfilecontent=new JLabel();
        jlfilecontent.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        JPanel jpbutton=new JPanel();
        jpbutton.setBorder(new EmptyBorder(20,0,10,0));
        jpbutton.add(jbyes);
        jpbutton.add(jbno);
        
        if(fileextension.equalsIgnoreCase("txt")){
            jlfilecontent.setText("<html>"+ new String(filedata) +"</html>");
        }else{
            jlfilecontent.setIcon( new ImageIcon(filedata));
        }
        
        jbyes.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                File filetodownload=new File(filename);
                try{           
                    FileOutputStream fileoutputstream=new FileOutputStream(filetodownload);
                    fileoutputstream.write(filedata);
                    fileoutputstream.close();
                    jframe.dispose();
                  
                }catch(IOException error){
                    error.printStackTrace();
                }
            }
        });
        jbno.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ee){
                jframe.dispose();
            }

             
        });
        
        jpanel.add(jltitle);
        jpanel.add(jlprompt);
        jpanel.add(jlfilecontent);
        jpanel.add(jpbutton);
        
        
        jframe.add(jpanel);
        
        return jframe;
    }
    public static String getFileExtension(String filename){
        
        int i=filename.lastIndexOf('.');
        
        if(i>0){
            return filename.substring(i+1);
            
        }else{
            return "No Extension Found.";
        }
    }
    
    
    
}
