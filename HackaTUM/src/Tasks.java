
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
@WebServlet("/Tasks")
public class Tasks extends HttpServlet {
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
    public Tasks() {
        // TODO Auto-generated constructor stub
    }
    
    
    private void printTasks(PrintWriter out, Connection conn) {
    	try {

        	out.println("<h2>All Tasks:</h2>");
        	out.println("<table>"
        			+ "<tr>"
        			+ 	"<td>ID</td>"
        			+	"<td>judgeTaskID</td>" 
        			+ 	"<td>contest</td>"
        			+ 	"<td>name</td>"
        			+ 	"<td></td>"
        			+ "</tr>");
    		
    		if (conn!=null){    	
        	
	        	// Get all tipps for this task
	        	//PreparedStatement ps = conn.prepareStatement("select * from test.tipps where aufgabenid = ?");
	        	//ps.setString(1, id);
	        	PreparedStatement ps = conn.prepareStatement("select ID, name, judgeTaskID, contest from test.Task");
	        	
	        	ResultSet result = ps.executeQuery();
	
	        	while (result.next()){
	        		out.println("<tr>"
	            			+ 	"<td>" + result.getString(1) + "</td>"
	            			+ 	"<td>" + result.getString(3) + "</td>"
	            			+ 	"<td>" + result.getString(4) + "</td>"
	            			+	"<td><a href=\"Task?ID=" + result.getString(1) + "\">" + result.getString(2) + "</a></td>" 
	            			+	"<td><a href=\"EditTasks?ID=" + result.getString(1) + "\">edit</a></td>" 
	            			+ "</tr>");
	        	}
	        }

        	out.println("</table>");
        	out.println("<br><a href=\"EditTasks\">create new task</a>");
    		
    	} catch (SQLException e) { 
			e.printStackTrace();
			out.println("ERROR");
		}    	
    }
    
    
    private void printSubmissions(PrintWriter out, Connection conn) {
    	try {
    		
        	out.println("<h2>All Submissions:</h2>");
        	out.println("<table>"
        			+ "<tr>"
        			+ 	"<td>ID</td>"
        			+ 	"<td>userID</td>"
        			+ 	"<td>taskID</td>"
        			+	"<td>content</td>"
        			+	"<td>time</td>"
        			+	"<td>judgeSubmissionID</td>"
        			+	"<td>result</td>"
        			+ "</tr>");
    		
    		if (conn!=null){    	
        	
	        	// Get all tipps for this task
	        	//PreparedStatement ps = conn.prepareStatement("select * from test.tipps where aufgabenid = ?");
	        	//ps.setString(1, id);
	        	PreparedStatement ps = conn.prepareStatement("select * from test.Submission");
	        	
	        	ResultSet result = ps.executeQuery();
	
	        	while (result.next()){
	        		

	        		String res = "ERROR";
	        		try {
	        			res = TUMJudgeConnection.getjudging(result.getString(6));
	        			if(res.equals("correct"))
	        				res = "<span style=\"color: green\">accepted</span>";
	        		} catch (Exception e) {
	        			
	        		}
	        		
	        		out.println("<tr>"
	            			+ 	"<td>" + result.getString(1) + "</td>"
	            			+ 	"<td>" + result.getString(2) + "</td>"
	            			+	"<td>" + result.getString(3) + "</td>"
	            			+	"<td>" + result.getString(4) + "</td>"
	            			+	"<td>" + result.getString(5) + "</td>"	
	            			+	"<td>" + result.getString(6) + "</td>"	
	            			+	"<td>" + res + "</td>"	 
	            			+ "</tr>");
	        		
	        		
	        		
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
		  
		

    	PrintWriter out = response.getWriter(); 
        response.setContentType("text/html");
        out.println("<html><body>");


        // connects to the database
		Connection conn = null;
        try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	        conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
	        
	        // print all Tasks: 
	        printTasks(out, conn);
	        
	        out.println("<br><br><br>");
	        // print all Submissions: 
	        printSubmissions(out, conn);
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("ERROR");
		}

    	out.println("</html>");
    	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doGet(request, response);
		
	}
}
