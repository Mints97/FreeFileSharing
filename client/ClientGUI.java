import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionListener;

// makes frame content
public class ClientGUI extends JFrame implements ActionListener
{
    private ClientData clientData = ClientData.getClientData();
    int x = 0;
    int y = 0;
    JPanel titlePanel, textPanel;
    JButton uploadButton, downloadButton, downButton, upButton; 
    JLabel serverLabel, text;
   // JCheckBox checkBox;
        public JPanel createContentPane ()
            {   
                JPanel firstPage = new JPanel();
                firstPage.setLayout(null);
                titlePanel = new JPanel();
                titlePanel.setLayout(null);
                titlePanel.setLocation(0, 0);
                titlePanel.setSize(500, 500);              
                uploadButton = new JButton("Search Upload");
                uploadButton.setLocation(0, 400);
                uploadButton.setSize(120, 30);
                uploadButton.addActionListener(this);
                titlePanel.add(uploadButton);
                downloadButton = new JButton("Search Download");
                downloadButton.setLocation(130, 400);
                downloadButton.setSize(200, 30);
                downloadButton.addActionListener(this);
                titlePanel.add(downloadButton); 
                upButton = new JButton("Upload Now");
                upButton.setLocation(0, 400);
                upButton.setSize(120, 30);
                upButton.addActionListener(this);
                downButton = new JButton("Download Now");
                downButton.setLocation(0, 400);
                downButton.setSize(120, 30);
                downButton.addActionListener(this);
                firstPage.add(titlePanel);
               
                for (int i =0; i < 10; i++)
                    {
                        final ServerData currentServer = clientData.getServer(i);
                        
                        if (currentServer == null) {
                            break;
                        }
                        
                        final JButton currentUpButton = new JButton("upload");
                        currentUpButton.setLocation(x, y);
                        currentUpButton.setSize(120, 30);
                        currentUpButton.addActionListener(new ActionListener(){
                          public void actionPerformed(ActionEvent e){
                             JFrame f = new JFrame("Upload");  
                             f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                             f.setSize(500, 500);
                             f.setVisible(true);
                             textPanel = new JPanel();
                             textPanel.setLayout(null);
                             textPanel.setLocation(0, 0);
                             textPanel.setSize(200, 200);
                             text = new JLabel("server information: " + currentServer.getName());
                             text.setLocation(0, 0);
                             text.setSize(120, 50);
                             text.setHorizontalAlignment(0);
                             text.setForeground(Color.black);
                           //  JCheckBox checkBox = new JCheckBox("Encrypt");
                           //  checkBox.setLocation(200,200);
                           //  checkBox.setSize(120,50);
                           //  textPanel.add(checkBox);
                             textPanel.add(upButton);             
                             textPanel.add(text);
                             f.add(textPanel);
                          }});
                        titlePanel.add(currentUpButton);
                        x=x+120;
                        //download buttons
                        JButton currentDownButton = new JButton("Download");
                        currentDownButton.setLocation(x, y);
                        currentDownButton.setSize(120, 30);
                        titlePanel.add(currentDownButton);
                        currentDownButton.addActionListener(new ActionListener(){
                          public void actionPerformed(ActionEvent e){
                             JFrame f = new JFrame("Download");  
                             f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                             f.setSize(500, 500);
                             f.setVisible(true);
                             textPanel = new JPanel();
                             textPanel.setLayout(null);
                             textPanel.setLocation(0, 0);
                             textPanel.setSize(200, 200);
                             text = new JLabel("server information" /*servername*/);
                             text.setLocation(0, 0);
                             text.setSize(120, 50);
                             text.setHorizontalAlignment(0);
                             text.setForeground(Color.black);
                           //  JCheckBox checkBox = new JCheckBox("Encrypt");
                           //  checkBox.setLocation(200,200);
                           //  checkBox.setSize(120,50);
                           //  textPanel.add(checkBox);
                             textPanel.add(downButton);             
                             textPanel.add(text);
                             f.add(textPanel);
                          }});
                        x=240;
                        serverLabel = new JLabel("server" /*server label here*/);
                        serverLabel.setLocation(x, y);
                        serverLabel.setSize(120, 30);
                        serverLabel.setHorizontalAlignment(0);
                        serverLabel.setForeground(Color.red);
                        titlePanel.add(serverLabel);
                        x=0;
                        y=y+30;       
                   }
                        firstPage.setOpaque(true);
                        return firstPage;
                }        
    //Events for Upload and Download   
    public void actionPerformed(ActionEvent e)
        { 
               if(e.getSource() == uploadButton)
            {
              // functions for upload from search ip
              JFrame f = new JFrame("Upload");  
              f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              f.setSize(500, 500);
              f.setVisible(true);
              textPanel = new JPanel();
              textPanel.setLayout(null);
              textPanel.setLocation(0, 0);
              textPanel.setSize(200, 200);
              text = new JLabel("server search" ,JLabel.RIGHT);
              text.setLocation(0, 0);
              text.setSize(120, 50);
              text.setHorizontalAlignment(0);
              text.setForeground(Color.black);           
              textPanel.add(text);
              
              
             
              

              JLabel porttext = new JLabel("enter port" ,JLabel.RIGHT);
              porttext.setLocation(200, 300);
              porttext.setSize(120, 50);
              
                
              JLabel fileidtext = new JLabel("enter ip" ,JLabel.RIGHT);
              fileidtext.setLocation(200, 400);
              fileidtext.setSize(120, 50);
              
                            //fix checkbox  
              
              final JTextField port = new JTextField(10);
              final JTextField fileid = new JTextField(10);
              port.setSize(200,50);
              port.setLocation(0,300);
              
              fileid.setSize(200,50);
              fileid.setLocation(0,400);
              
              upButton.setLocation(0,100);
              textPanel.add(upButton);
              
              textPanel.add(port);
              textPanel.add(fileid);
              
              textPanel.add(porttext);
              textPanel.add(fileidtext);

              f.add(textPanel);
              // functions for download from a search ip
              
           }
            if(e.getSource() == upButton)
              {
                 JOptionPane.showMessageDialog(null,"Uploaded!\nIP Adress: " + /*get ip address */ "\nPort:" +/* get port */ "\nKey: " /*get key*/ ,"Status", JOptionPane.PLAIN_MESSAGE);
                 setVisible(true);
                    // functions for upload
              }     
             if(e.getSource() == downloadButton)
            {
              // functions for upload from search ip
                            JFrame f = new JFrame("Download");  
              f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              f.setSize(500, 500);
              f.setVisible(true);
              textPanel = new JPanel();
              textPanel.setLayout(null);
              textPanel.setLocation(0, 0);
              textPanel.setSize(200, 200);
              text = new JLabel("server search" ,JLabel.RIGHT);
              text.setLocation(0, 0);
              text.setSize(120, 50);
              text.setHorizontalAlignment(0);
              text.setForeground(Color.black);           
              textPanel.add(text);
              
              JLabel iptext = new JLabel("enter ip:" ,JLabel.RIGHT);
              iptext.setLocation(200, 350);
              iptext.setSize(120, 50);
              

              JLabel porttext = new JLabel("enter port" ,JLabel.RIGHT);
              porttext.setLocation(200, 300);
              porttext.setSize(120, 50);
              
                
              JLabel fileidtext = new JLabel("enter file id" ,JLabel.RIGHT);
              fileidtext.setLocation(200, 400);
              fileidtext.setSize(120, 50);
              
                            //fix checkbox  
              final JTextField ip = new JTextField(10);
              final JTextField port = new JTextField(10);
              final JTextField fileid = new JTextField(10);
              port.setSize(200,50);
              port.setLocation(0,300);
              ip.setSize(200,50);
              ip.setLocation(0,350);
              fileid.setSize(200,50);
              fileid.setLocation(0,400);
              JButton loginButton = new JButton("Search");
              loginButton.setLocation(100,100);
              textPanel.add(loginButton);
              textPanel.add(ip);
              textPanel.add(port);
              textPanel.add(fileid);
              textPanel.add(iptext);
              textPanel.add(porttext);
              textPanel.add(fileidtext);
              
              loginButton.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {   if(e.getSource() == downloadButton)
            {
              // functions for upload from search ip
              JFrame f = new JFrame("Download");  
              f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              f.setSize(500, 500);
              f.setVisible(true);
              textPanel = new JPanel();
              textPanel.setLayout(null);
              textPanel.setLocation(0, 0);
              textPanel.setSize(200, 200);
              text = new JLabel("server search" ,JLabel.RIGHT);
              text.setLocation(0, 0);
              text.setSize(120, 50);
              text.setHorizontalAlignment(0);
              text.setForeground(Color.black);           
              textPanel.add(text);
              
              
              JLabel iptext = new JLabel("enter ip:" ,JLabel.RIGHT);
              iptext.setLocation(200, 350);
              iptext.setSize(120, 50);
              

              JLabel porttext = new JLabel("enter port" ,JLabel.RIGHT);
              porttext.setLocation(200, 300);
              porttext.setSize(120, 50);
              
                
              JLabel fileidtext = new JLabel("enter file id" ,JLabel.RIGHT);
              fileidtext.setLocation(200, 400);
              fileidtext.setSize(120, 50);
              
                            //fix checkbox  
              final JTextField ip = new JTextField(10);
              final JTextField port = new JTextField(10);
              final JTextField fileid = new JTextField(10);
              port.setSize(200,50);
              port.setLocation(0,300);
              ip.setSize(200,50);
              ip.setLocation(0,350);
              fileid.setSize(200,50);
              fileid.setLocation(0,400);
              
              downButton.setLocation(0,100);
              textPanel.add(downButton);
              textPanel.add(ip);
              textPanel.add(port);
              textPanel.add(fileid);
              textPanel.add(iptext);
              textPanel.add(porttext);
              textPanel.add(fileidtext);

              f.add(textPanel);
              // functions for download from a search ip
            }
             if(e.getSource() == downButton)
              {
                 JOptionPane.showMessageDialog(null,"Downloaded!\nIP Adress: " + /*get ip address */ "\nPort:" +/* get port */ "\nKey: " /*get key*/ ,"Status", JOptionPane.PLAIN_MESSAGE);
                 setVisible(true);
                    // functions for upload
              }  
      }
//Makes Frame
    private static void createAndShowGUI() 
      {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Free File Sharing");
        //Create and set up the content pane.
        ClientGUI demo = new ClientGUI();
        frame.setContentPane(demo.createContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
      }
//run and show frame
    public static void main(String[] args) 
      {
        SwingUtilities.invokeLater(new Runnable()
             {
                 public void run() 
                     {
                       createAndShowGUI();
                     }
             });
      }  
}

    
