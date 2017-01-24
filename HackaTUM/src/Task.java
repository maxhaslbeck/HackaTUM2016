
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 
import javax.sql.DataSource;
 
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 

import javax.annotation.Resource; 


/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/Task")
public class Task extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name ="jdbc/test")
	private DataSource dataSource;
	
    // database connection settings
    private String dbURL = LocalSettings.dbURL;
    private String dbUser = LocalSettings.dbUser;
    private String dbPass = LocalSettings.dbPass;
    
    
    /**
     * Default constructor. 
     */
    public Task() {
        // TODO Auto-generated constructor stub
    }
    
    
    private void printTask(PrintWriter out, Connection conn, String taskID) {
    	try {
    		
        	//out.println("Task with ID "+taskID+"<br>"); 
    		
    		if (conn!=null){    	
        	
	        	// Get all tipps for this task
	        	//PreparedStatement ps = conn.prepareStatement("select * from test.tipps where aufgabenid = ?");
	        	//ps.setString(1, id);
	        	PreparedStatement ps = conn.prepareStatement("select * from test.Task WHERE ID = ?");
	        	ps.setString(1, taskID);
	        	System.out.println(ps.toString());
	        	
	        	ResultSet result = ps.executeQuery();
	
	        	while (result.next()){
	        		out.println("<h1>" + result.getString(2)  + "</h1>");
	        		out.println("<h2>Description</h2>");
	        		out.println(result.getString(4) + "<br>"); 
	        		out.println("<h2>Definition.thy</h2>");
	        		out.println(result.getString(6) + "<br>"); 
	        		out.println("<h2>Check.thy</h2>");
	        		out.println(result.getString(5) + "<br>"); 
	        	}
	        }

        	out.println("</table>");
    		
    	} catch (SQLException e) { 
			e.printStackTrace();
			out.println("ERROR");
		}    	
    }
     
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  

		String taskID = request.getParameter("ID");
		System.out.println("taskID is: " +  taskID);

    	PrintWriter out = response.getWriter(); 
        response.setContentType("text/html");
        out.println("<html><body>");


        // connects to the database
		Connection conn = null;
        try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	        conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
	        
	        // print all Tasks: 
	        printTask(out, conn, taskID); 
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("ERROR");
		}
        
        out.println("<form action=\"Submission\" method=\"post\" enctype=\"multipart/form-data\">"
        					+ "Upload Submission: <br>"
        					+ "<input type=\"file\" name=\"submission\"> <br>"
        					+ "<input type=\"file\" multiple=\"\" name=\"code[]\"> <br>"
        					+ "<input type=\"hidden\" name=\"aufgabe\" value=\""+ taskID +"\"> <br>"
        					+ "Upload xml: <br>"
        					+ "<input type=\"submit\" value=\"submit\">"
        					+ "</form>");	
        

    	out.println("</html>");
    	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doGet(request, response);
		
	}
}