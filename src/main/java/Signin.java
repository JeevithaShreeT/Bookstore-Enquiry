




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

public class Signin extends JFrame implements ActionListener {
    
    JTextField usname, pass, mail, role;
    JButton sign, back;
    
    //the below are used for JDBC connection
    String url = "jdbc:mysql://localhost:3306/bookstore(the url of your table/schema)";
    String user = "root";
    String password = "****";
    
    Signin(){
        
        // For setting the image at the top left corner of the frame     
        ImageIcon image = new ImageIcon(getClass().getResource("/logo.png"));
        this.setIconImage(image.getImage());
        
        //The label for the signin heading/header
        
        JLabel label = new JLabel();
        label.setText("Sign In");
        label.setBounds(185, 1, 110,110);
        label.setFont(new Font("TimesRoman", Font.BOLD, 22));
        label.setForeground(Color.white);
        
        // for the label usesrName
        JLabel user = new JLabel();
        user.setText("UserName");
        user.setBounds(30,80,75,25);
        user.setFont(new Font("TimesRoman", Font.BOLD, 16));
        user.setForeground(Color.WHITE);
        
        // for the textfield to type in the user name
        usname = new JTextField();
        usname.setBounds(30,120,350,30);
        usname.setFont(new Font("TimesRoman",Font.BOLD,14));
        
        //the lable for email
        JLabel email = new JLabel();
        email.setText("Email");
        email.setBounds(30,175,75,25);
        email.setFont(new Font("TimesRoman", Font.BOLD, 16));
        email.setForeground(Color.WHITE);
        
        //the textfield to type the email
        mail = new JTextField();
        mail.setBounds(30,210,350,30);
        mail.setFont(new Font("TimesRoman",Font.BOLD,14));
        
        //password label
        JLabel pw = new JLabel();
        pw.setText("PassWord");
        pw.setBounds(30,260,75,25);
        pw.setFont(new Font("TimesRoman", Font.BOLD, 16));
        pw.setForeground(Color.WHITE);
        
        //password textfield to type the password
        pass = new JTextField();
        pass.setBounds(30,290,350,30);
        pass.setFont(new Font("TimesRoman",Font.BOLD,14));
        
        //role label
        JLabel roles = new JLabel();
        roles.setText("Role");
        roles.setBounds(30,340,75,25);
        roles.setFont(new Font("TimesRoman", Font.BOLD, 16));
        roles.setForeground(Color.WHITE);
        
        //textfield to mention the role of the user
        role = new JTextField();
        role.setBounds(30,370,350,30);
        role.setFont(new Font("TimesRoman",Font.BOLD,14));
        
        //code for signin button
        sign = new JButton("SignIn");
        sign.setBounds(110,450,200,35);
        sign.setBackground(Color.GRAY);
        sign.setForeground(Color.WHITE);
        sign.setFont(new Font("TimesRoman",Font.BOLD,17));
        sign.setToolTipText("Click to Sign in");
        
        sign.addActionListener(this);
        
        //The pannel to bear the three labels and textfiels along with the signin button
        JPanel pan = new JPanel();
        pan.setBounds(110, 75, 450, 530);
        pan.setBackground(Color.DARK_GRAY);
        pan.setLayout(null);
        pan.add(label);
        pan.add(user);
        pan.add(usname);
        pan.add(email);
        pan.add(mail);
        pan.add(pw);
        pan.add(pass);
        pan.add(roles);
        pan.add(role);
        pan.add(sign);
        
       //code for logout button 
       back = new JButton("back");
       back.setBounds(20,20,70,30);
       back.setBackground(Color.GRAY);
       back.setForeground(Color.WHITE);
       back.setFont(new Font("TimesRoman",Font.BOLD,16));
       back.setToolTipText("Click to go back");
        
        
       back.addActionListener(this);
        
       //setting of the frame and its inclusions
       
        this.setTitle("Signin");
        this.setSize(700,700);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.black);
        this.setLayout(null);
        this.add(pan);
        this.add(back);
        this.setVisible(true);
        
       
    }
    
    //Actions performed for the buttons
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == back){
            this.dispose();
            new Welcome();
        }
        else if(e.getSource() == sign){
            String name = usname.getText();
            String email = mail.getText();
            String pw = pass.getText();
            String ro = role.getText();
            
            //checking the textfields are filled
            if(!name.isEmpty() && !email.isEmpty() && !pw.isEmpty() && !ro.isEmpty()){
               try{
                   
                   //establishing the connection to the database using JDBC
                   
                   Connection con = DriverManager.getConnection(url,user,password);
                   
                   //Aquery to check for the exixting user while signin
                   
                   String checkquery = "Select * from bookstore.users where Email = ? or Name = ?";
                   PreparedStatement ptsmt = con.prepareStatement(checkquery);
                   ptsmt.setString(1, email);
                   ptsmt.setString(2, name);
                   
                   ResultSet rst = ptsmt.executeQuery();
                   
                   if(rst.next()){
                       JOptionPane.showMessageDialog(this, "User already exists");
                   }
                   else{
                   //code to insert new user or new signin.
                   
                   String query = "insert into bookstore.users (Name,Email,password,role) values (?,?,?,?)";
                   if(ro.equalsIgnoreCase("admin") || ro.equalsIgnoreCase("customer")){
                        PreparedStatement pt = con.prepareStatement(query);
                        pt.setString(1,name);
                        pt.setString(2,email);
                        pt.setString(3, pw);
                        pt.setString(4, ro);
                   
                   
                        int res = pt.executeUpdate();
                   
                        //the below is to direct the user accoding to their role page
                        if(res>0){
                       
                           if(ro.equalsIgnoreCase("admin")){
                           this.dispose();
                           new Adminpage(name);
                        }
                        else if(ro.equalsIgnoreCase("customer")){
                           this.dispose();
                           new Welcomepage(name);
                       }
                       
                   }
               }   
                   //A dialog box to intimate wrong role typed.
                   else{
                       JOptionPane.showMessageDialog(this, "Role must be admin or customer");
                   }
                  }
                   con.close();
               }
               catch(SQLException ex){
                   ex.printStackTrace();
                   JOptionPane.showMessageDialog(this, "Data Error: "+ex.getMessage());
               }
        }
            //A dilog box to fill the boxes if leftout.
            else{
                JOptionPane.showMessageDialog(this, "Please fill in the boxes");
            }
     
        }
    }
    
    /* This class can run independently and the below is for the 
       solo run of the class, can be excluded by having a 
       seperate class to run the main function call.
    */ 
    
    public static void main(String[] args){
        
        Signin sign = new Signin();
              
    }
}
