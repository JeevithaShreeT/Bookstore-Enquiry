

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

public class Welcome extends JFrame implements ActionListener {
    
    JButton login,signin;
    
    
    Welcome(){
          
        // For setting the image at the top left corner of the frame        
        ImageIcon image = new ImageIcon(getClass().getResource("/logo.png"));
        this.setIconImage(image.getImage());
        
        Border border = BorderFactory.createLineBorder(Color.black, 5);
        
        
        //Image resized for the logo stamp on the frame
        Image i = image.getImage();
        Image resize = i.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon resized = new ImageIcon(resize);
        
        JLabel label = new JLabel();
        
        label.setText("Welcome!");
        
        // The Alignment for the logo and Welcome Label 
        
        label.setIcon(resized);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setForeground(Color.white);
        label.setFont(new Font("TimesRoman", Font.BOLD, 28));
        label.setBorder(border);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(135,110,300,300);
        
        
        // Below code are for the login and signin buttons
        
        login = new JButton("Login");
        signin = new JButton("Sign In");
        login.setBounds(185, 450, 200, 25);
        signin.setBounds(185, 500, 200, 25);
        login.setFont(new Font("TimesRoman",Font.BOLD,14));
        signin.setFont(new Font("TimesRoman",Font.BOLD,13));
        login.setForeground(Color.white);
        signin.setForeground(Color.white);
        login.setBackground(Color.DARK_GRAY);
        signin.setBackground(Color.DARK_GRAY);
        login.setToolTipText("Click to login");
        signin.setToolTipText("click to sign in");
        
        login.addActionListener(this);
        signin.addActionListener(this);
        
        // The below code has the frame inclusions along with frame creation code
        
        this.setTitle("Welcome");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,600);
        this.setResizable(false);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(0,0,0));
        this.add(label);
        this.add(login);
        this.add(signin);
        this.setVisible(true);
       
      
        
    }
     
    // This is for the action performed by clicking the button 
    @Override
    public void actionPerformed(ActionEvent e){
       if(e.getSource() == login){
           this.dispose();
           new Login();
       }
       else if(e.getSource() == signin){
           this.dispose();
           new Signin();
       }
    }
    
    /* This class can run independently and the below is for the 
       solo run of the class, can be excluded by having a 
       seperate class to run the main function call.
    */ 
    public static void main(String[] args){
        
        Welcome welcome = new Welcome();
    }
}
