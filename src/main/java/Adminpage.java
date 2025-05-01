




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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class Adminpage extends JFrame implements ActionListener{
    
    CardLayout cardl;
    JPanel buttonpan, card;
    JButton add,view,com,out,submit;
    JTextField books,author,pri,stk;
    
    //the below are used for JDBC connection
    
    String url = "jdbc:mysql://localhost:3306/bookstore(the url of your table/schema)";
    String user = "root";
    String password = "1234";
    
    Adminpage(String name){
        
        // For setting the image at the top left corner of the frame  
        
        ImageIcon image = new ImageIcon(getClass().getResource("/logo.png"));
        this.setIconImage(image.getImage());
        
        // the code will flash the admin name while entering the page
        JLabel label = new JLabel("Welcome "+name+"!");
        label.setBounds(250,60,500,70);
        label.setForeground(Color.white);
        label.setFont(new Font("TimesRoman", Font.BOLD, 30));
        
        //the view button is used to view books inside the database
        
        view = new JButton("View Books");
        view.setBounds(30,12,170,120);
        view.setBackground(Color.LIGHT_GRAY);
        view.setFont(new Font("TimesRoman", Font.BOLD, 18));
        view.setToolTipText("Click to view table");
        view.addActionListener(this);
        
        //The add is used to add new books to the table
        
        add = new JButton("Add Books");
        add.setBounds(210,12,170,120);
        add.setBackground(Color.LIGHT_GRAY);
        add.setFont(new Font("TimesRoman", Font.BOLD, 18));
        add.setToolTipText("Click to add books");
        add.addActionListener(this);
        
        //This button will display the comments/suggestions given by the customers
        
        com = new JButton("Comments");
        com.setBounds(390,12,170,120);
        com.setBackground(Color.LIGHT_GRAY);
        com.setFont(new Font("TimesRoman", Font.BOLD, 18));
        com.setToolTipText("Click to add books");
        com.addActionListener(this);
        
        //A pannel to hold the buttons
        
        buttonpan = new JPanel();
        buttonpan.setBounds(80,140,600,150);
        buttonpan.setBackground(Color.DARK_GRAY);
        buttonpan.setLayout(null);
        buttonpan.add(view);
        buttonpan.add(add);
        buttonpan.add(com);
        
        
        cardl = new CardLayout();
        card = new JPanel(cardl);
        card.setBounds(40,320,700,400);
        card.setBackground(Color.darkGray);
               
        
        //Logout button
        out = new JButton("LogOut");
        out.setBounds(650,20,100,30);
        out.setBackground(Color.LIGHT_GRAY);
        out.setFont(new Font("TimesRoman",Font.BOLD,16));
        out.setToolTipText("click to Logout");
        out.addActionListener(this);
        
        
        //Frame setting code
        this.setSize(800,800);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.add(label);
        this.add(buttonpan);
        this.add(card);
        this.add(out);
        this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource()== view){
            card.add(buttonview(),"Table");
            cardl.show(card,"Table");
        }
        else if(e.getSource()== com){
            card.add(buttoncomment(),"Comments");
            cardl.show(card, "Comments");
        }
        else if(e.getSource()== add){
            card.add(buttonadd(),"Add");
            cardl.show(card, "Add");
        }
        else if(e.getSource()== out){
            this.dispose();
            new Login();
        }
    }
    
    public JPanel buttonview(){
        
        //The below pannel is used for the display of the table
        JPanel pan = new JPanel(null);
        pan.setBackground(Color.DARK_GRAY);
        String[] col = {"Book_Id","title","author","price","stock"};
        DefaultTableModel mod = new DefaultTableModel(col,0);
        JTable table = new JTable(mod);
        JScrollPane src = new JScrollPane(table);
        src.setBounds(30,24,640,340);
        
        
        
        try{
            //Setting the JDBC connection to display the database or books table
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
        
        pan.add(src);
        return pan;
    }
    
    /* The function below is used to add a new book to the 
      table by reading the book name, author, price, stock.
    */
    public JPanel buttonadd(){
        
        //A pannel to have the labels and textfields
        JPanel pan = new JPanel(null);
        pan.setBackground(Color.darkGray);
        
        //book name label
        JLabel book = new JLabel("Book Name");
        book.setBounds(40,40,100,30);
        book.setForeground(Color.white);
        book.setFont(new Font("RomanTimes",Font.BOLD,16));
        
        //Textfield to enter the book name
        books = new JTextField();
        books.setBounds(40,80,300,30);
        
        //Author name label
        JLabel auth = new JLabel("Author");
        auth.setBounds(40,120,100,30);
        auth.setForeground(Color.white);
        auth.setFont(new Font("RomanTimes",Font.BOLD,16));
        
        //Textfield to enter the author name
        author = new JTextField();
        author.setBounds(40,160,300,30);
        
        //Price label
        JLabel price = new JLabel("Price");
        price.setBounds(40,210,100,30);
        price.setForeground(Color.white);
        price.setFont(new Font("RomanTimes",Font.BOLD,16));
        
        //Textfield to enter the price
        pri = new JTextField();
        pri.setBounds(40,250,110,30);
        
        //Stock label
        JLabel stock = new JLabel("Stock");
        stock.setBounds(200,210,100,30);
        stock.setForeground(Color.white);
        stock.setFont(new Font("RomanTimes",Font.BOLD,16));
        
        //textfield to enter the stock
        stk = new JTextField();
        stk.setBounds(200,250,110,30);
        
        //Submit/Add button
        submit = new JButton("Add");
        submit.setBounds(40,330,90,30);
        submit.setBackground(Color.LIGHT_GRAY);
        submit.setFont(new Font("TimesRoman",Font.BOLD,17));
        
        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ex){
                
               String book = books.getText();
               String auth = author.getText();
               
               if(!book.isEmpty() && !auth.isEmpty()){
                   try{
                       
                       double price = Double.parseDouble(pri.getText());
                       int stock = Integer.parseInt(stk.getText());
                       
                       Connection con = DriverManager.getConnection(url,user,password);
                       String query = "insert into bookstore.books (title,author,price,stock) values (?,?,?,?)";
                       PreparedStatement pt = con.prepareStatement(query);
                       pt.setString(1, book);
                       pt.setString(2, auth);
                       pt.setDouble(3, price);
                       pt.setInt(4, stock);
                       
                       int res = pt.executeUpdate();
                       
                       if(res>0){
                           JOptionPane.showMessageDialog(pan, "The book is added successfully");
                           books.setText("");
                           author.setText("");
                           pri.setText("");
                           stk.setText("");
                       }
                       else{
                           JOptionPane.showMessageDialog(pan, "Failed to add");
                       }
                       
                       con.close();
                   }
                   catch(NumberFormatException e){
                       e.printStackTrace();
                       JOptionPane.showMessageDialog(pan, "Format Error: "+e.getMessage());
                   }
                   catch(SQLException e){
                       e.printStackTrace();
                       JOptionPane.showMessageDialog(pan, "Data Error: "+e.getMessage());
                   }
                   
               }
               else{
                   JOptionPane.showMessageDialog(pan, "Please fill in the boxes");
               }
            }
        });
        
        pan.setLayout(null);
        pan.add(book);
        pan.add(books);
        pan.add(auth);
        pan.add(author);
        pan.add(price);
        pan.add(pri);
        pan.add(stock);
        pan.add(stk);
        pan.add(submit);
        
        return pan;
    }
    
    //The function used to display the comments table 
    public JPanel buttoncomment(){
        
        JPanel pane = new JPanel(null);
        pane.setBackground(Color.darkGray);
        String[] col = {"id","comment"};
        DefaultTableModel mod = new DefaultTableModel(col,0);
        JTable table = new JTable(mod);
        JScrollPane scr = new JScrollPane(table);
        scr.setBounds(30,24,640,340);
        
        try{
            Connection con = DriverManager.getConnection(url,user,password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from bookstore.comments");
            
            while(rs.next()){
                mod.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("comment")
                });
            }
            
            con.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        pane.add(scr);
        
        return pane;
    }
    
    /* This class can run independently and the below is for the 
       solo run of the class, can be excluded by having a 
       seperate class to run the main function call.
    */ 
    public static void main(String[] args){
        
        Adminpage ad = new Adminpage("Admin");
    }
}
