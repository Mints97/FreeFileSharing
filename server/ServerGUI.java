import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

 
public class ServerGUI {
    
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;

   public ServerGUI(){
      prepareGUI();
   }

   public static void main(String[] args){
      ServerGUI  ServerGUI0 = new ServerGUI();      
      ServerGUI0.ServerGUI();
   }

   private void prepareGUI(){
      mainFrame = new JFrame("Java Swing Examples");
      mainFrame.setSize(400,400);
      mainFrame.setLayout(new GridLayout(3, 1));
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new JLabel("", JLabel.CENTER);        
      statusLabel = new JLabel("",JLabel.CENTER);    

      statusLabel.setSize(350,100);

      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);  
   }

   private void ServerGUI(){
      headerLabel.setText("Free File Sharing"); 

      JLabel  namelabel= new JLabel("Port (Only enter Integers) ", JLabel.RIGHT);
      
      final JTextField userText = new JTextField(6);
      

      JButton loginButton = new JButton("Go");
      loginButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {     
             
                String data = "Server "+ userText.getText();//server info
            statusLabel.setText(data);  
            
         }}); 

      controlPanel.add(namelabel);
      controlPanel.add(userText);
      controlPanel.add(loginButton);
      mainFrame.setVisible(true);  
   }
}
