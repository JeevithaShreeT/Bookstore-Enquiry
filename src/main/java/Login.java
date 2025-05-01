


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener{
    
    
    JTextField text;
    JTextField textpw;
    JButton loginbut;
    JButton back;
    
    //the below are used for JDBC connection
    String url = "jdbc:mysql://localhost:3306/bookstore(the url of your table/schema)";
    String user = "root";
    String password = "****";
    
    Login(){
      
        // For setting the image at the top left corner of the frame 
        
        ImageIcon image = new ImageIcon(getClass().getResource("/logo.png"));
        this.setIconImage(image.getImage());
        
        //Login label
        JLabel label = new JLabel();
        label.setText("Login");
        label.setForeground(Color.white);
        label.setFont(new Font("TimesRoman", Font.BOLD, 24));
        label.setBounds(145,2,110,110);
        
        //email label
        JLabel email = new JLabel();
        email.setText("Email");
        email.setForeground(Color.white);
        email.setFont(new Font("TimesRoman",Font.BOLD,16));
        email.setBounds(30,50,75,75);
        
        //password label
        JLabel pw = new JLabel();
        pw.setText("Password");
        pw.setForeground(Color.white);
        pw.setFont(new Font("TimesRoman", Font.BOLD, 16));
        pw.setBounds(30,140,75,75);
        
        //textfield to enter email
        text = new JTextField();
        text.setBounds(30,110,280,30);
        text.setFont(new Font("TimesRoman",Font.BOLD,14));
        
        //textfield to enter password
        textpw = new JTextField();
        textpw.setBounds(30,200,280,30);
        textpw.setFont(new Font("TimesRoman",Font.BOLD,14));
        
        //Login button
        loginbut = new JButton("Login");
        loginbut.setBounds(30, 280, 280,30);
        loginbut.setBackground(Color.GRAY);
        loginbut.setFont(new Font("TimesRoman", Font.BOLD, 15));
        loginbut.setForeground(Color.white);
        loginbut.setToolTipText("Click to Login");
        
        loginbut.addActionListener(this);

        //The panel holds the labels and tetfield above
        
       JPanel pan = new JPanel();
       pan.setBackground(Color.DARK_GRAY);
       pan.setBounds(127,100,350,350);
       pan.setLayout(null);
       pan.add(label);
       pan.add(email);
       pan.add(pw);
       pan.add(text);
       pan.add(textpw); 
       pan.add(loginbut);
 
       //Logout button
       back = new JButton("back");
       back.setBounds(20,20,70,30);
       back.setBackground(Color.GRAY);
       back.setForeground(Color.WHITE);
       back.setFont(new Font("TimesRoman",Font.BOLD,16));
       back.setToolTipText("Click to go back");
       
       
       back.addActionListener(this);
       
       //Frame setting
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(650,650);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.BLACK);
        this.setLayout(null);
        this.add(pan);
        this.add(back);
        this.setVisible(true);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()== back){
            this.dispose();
            new Welcome();
        }
        else if(e.getSource()==loginbut){
            String mail = text.getText();
            String pass = textpw.getText();
            
            if(!mail.isEmpty() && !pass.isEmpty()){
                try{
                    //Establishing the JDBC connection and checking the user already existing in db.
                    
                   Connection con = DriverManager.getConnection(url,user,password);
                   String query = "select name,role from bookstore.users where Email = ? and password = ?";
                   PreparedStatement pt = con.prepareCall(query);
                   pt.setString(1,mail);
                   pt.setString(2,pass);
                   
                   ResultSet rs = pt.executeQuery();
                   boolean match = false;
                   
                   while(rs.next()){
                       match = true;
                       String name = rs.getString("name");
                       String role = rs.getString("role");
                       
                     //redirecting the page according to the user role
                       
                       if(role.equalsIgnoreCase("admin")){
                           this.dispose();
                           new Adminpage(name);
                       }
                       else if(role.equalsIgnoreCase("customer")){
                           this.dispose();
                           new Welcomepage(name);
                       }
                       else{
                           JOptionPane.showMessageDialog(this, "Invalid role assigned");
                       }
                   }
                   
                   if(!match){
                       JOptionPane.showMessageDialog(this, "Invalid Credentials");
                   }
                   con.close();
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Data Error: "+ex.getMessage());
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Please fill in the credentials");
            }
        }
        
    }
    
    
    /* This class can run independently and the below is for the 
       solo run of the class, can be excluded by having a 
       seperate class to run the main function call.
    */ 
    
    public static void main(String[] args){
        
        Login login = new Login();
        
    }
}
