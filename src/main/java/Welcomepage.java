



import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;


public class Welcomepage extends JFrame implements ActionListener{
    
    JPanel button, card;
    JButton book, com, out, submit;
    CardLayout cardl;
    
    //the below are used for JDBC connection
    String url = "jdbc:mysql://localhost:3306/bookstore(the url of your table/schema)";
    String user = "root";
    String password = "****";
    
    
    
    
    Welcomepage(String name){
        
        // For setting the image at the top left corner of the frame     
        ImageIcon image = new ImageIcon(getClass().getResource("/logo.png"));
        this.setIconImage(image.getImage());
        
        // the code will flash the user name while entering the page
        JLabel user = new JLabel("Welcome " +name+ "!");
        user.setBounds(250,60,500,70);
        user.setForeground(Color.white);
        user.setFont(new Font("TimesRoman", Font.BOLD, 30));
        
        // Below are the codes for buttons.
        
        // The below button will display the books table inside the pannel.
        
        book = new JButton("Books");
        book.setBounds(30,12,250,120);
        book.setBackground(Color.LIGHT_GRAY);
        book.setFont(new Font("TimesRoman",Font.BOLD,20));
        book.setToolTipText("Click to view books enquiry");
        
        book.addActionListener(this);
        
        // The com button is the comments/suggestions button and by clickling that the suggestion box will appear.
        
        com = new JButton("Suggestions");
        com.setBounds(317,12,250,120); 
        com.setBackground(Color.LIGHT_GRAY);
        com.setFont(new Font("TimesRoman",Font.BOLD,20));
        com.setToolTipText("click to comment");
        
        com.addActionListener(this);
        
        /* The below is a pannel which has the two buttons located,
           the buttons are placed inside the pannel for neat UI.
        */ 
        
        button = new JPanel();
        button.setBounds(80,140,600,150);
        button.setBackground(Color.GRAY);     
        button.setLayout(null);
        button.add(book);
        button.add(com);
      
     
        cardl = new CardLayout();
        card = new JPanel(cardl);
        card.setBounds(40,320,700,400);
        card.setBackground(Color.DARK_GRAY);
  
        // Logout button for logging out of the window.
        
        out = new JButton("LogOut");
        out.setBounds(650,20,100,30);
        out.setBackground(Color.LIGHT_GRAY);
        out.setFont(new Font("TimesRoman",Font.BOLD,16));
        out.setToolTipText("click to Logout");
        
        out.addActionListener(this);
        
        // The below has the frame inclusions of what the frame has or holds.
        
        this.setSize(800,800);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.add(user);
        this.add(button);
        this.add(out);
        this.add(card);
        this.setVisible(true);
        
    }
    
    // This is for the action performed by clicking the button 
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()== book){
            card.add(bookspanel(), "Books");
            cardl.show(card,"Books");
        }
        else if(e.getSource()== com){
            card.add(suggestionspan(), "Comments");
            cardl.show(card, "Comments");
        }
        else if(e.getSource()== out){
            this.dispose();
            new Login();
        }
    }
    
   
    /* This class can run independently and the below is for the 
       solo run of the class, can be excluded by having a 
       seperate class to run the main function call.
    */ 
    
    public JPanel bookspanel(){
        
        
        JPanel pan = new JPanel(null);
        String[] col = {"Book_Id","title","author","price","stock"};
        DefaultTableModel mod = new DefaultTableModel(col,0){
            
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        JTable table = new JTable(mod);
        JScrollPane scr = new JScrollPane(table);
        scr.setBounds(30,24,640,340);
        
        try{
            Connection con = DriverManager.getConnection(url,user,password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM bookstore.books");
            
            while(rs.next()){
                mod.addRow(new Object[]{
                    rs.getInt("Book_Id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                });
            }
            
            con.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        
        pan.add(scr);
        
        return pan;
    }
    
    public JPanel suggestionspan(){
        
        JPanel pane = new JPanel(null);
        
        JLabel lab = new JLabel("Write your suggestions/comments :");
        lab.setBounds(40,40,300,30);
        lab.setForeground(Color.WHITE);
        lab.setFont(new Font("TimesRoman", Font.BOLD, 16));
        
        JTextArea area = new JTextArea();
        area.setBounds(40,90,600,150);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        
        submit = new JButton("Submit");
        submit.setBounds(550,270,90,30);
        submit.setBackground(Color.LIGHT_GRAY);
        submit.setToolTipText("Click to submit");
        
        submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String comment = area.getText().trim();
                if(!comment.isEmpty()){
                    try{
                        Connection con = DriverManager.getConnection(url,user,password);
                        String query = "INSERT INTO bookstore.comments (comment) VALUES (?)";
                        PreparedStatement pt = con.prepareStatement(query);
                        pt.setString(1, comment);
                        int res = pt.executeUpdate();
                        
                        if(res>0){
                            JOptionPane.showMessageDialog(pane, "Thank you for your suggestion");
                            area.setText("");
                        }else{
                            JOptionPane.showMessageDialog(pane, "Failed to submit");
                        }
                        
                        con.close();
                        
                    }
                    catch(SQLException ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(pane, "Database Error " + ex.getMessage());
                    }
                }
                else{
                    JOptionPane.showMessageDialog(pane, "Please write something");
                }
            }
        });
        
        pane.setBackground(Color.DARK_GRAY);
        pane.add(lab);
        pane.add(area);
        pane.add(submit);
        return pane;
    }
    
    public static void main(String[] args){
        
        Welcomepage wel = new Welcomepage("Customer");
    }
}
