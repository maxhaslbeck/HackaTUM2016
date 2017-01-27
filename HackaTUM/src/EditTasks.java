
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
@WebServlet("/EditTasks")
public class EditTasks extends HttpServlet {
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
    public EditTasks() {
        // TODO Auto-generated constructor stub
    }

    

    private void printTaskEdit(PrintWriter out, Connection conn, String taskID) {
    	try {
    		
        	//out.println("Task with ID "+taskID+"<br>"); 
    		
    		if (conn!=null){    	
        	
	        	// Get all tipps for this task
	        	//PreparedStatement ps = conn.prepareStatement("select * from test.tipps where aufgabenid = ?");
	        	//ps.setString(1, id);
    			
    			String taskName = ""; 
    			String judgeTaskID = "";
    			String descr = "";
    			String def = "";
    			String check = "";
    			String contest = "";
    			String subm = "";
    			
    			if(taskID==null) {
    				// new task
    				taskID="none";
    				out.println("<h1>Create a new Task</h1>");
    			} else {
    				// edit task
		        	PreparedStatement ps = conn.prepareStatement("select * from test.Task WHERE ID = ?");
		        	ps.setString(1, taskID);
		        	System.out.println(ps.toString());
		        	
		        	ResultSet result = ps.executeQuery();	  
		        	while (result.next()){
		        		taskName = result.getString(2);
		        		judgeTaskID = result.getString(3);
		        		descr = result.getString(4);
		        		def = result.getString(6);
		        		check = result.getString(5); 
		        		contest = result.getString(7); 
		        		subm = result.getString(8); 
		        	}
    				out.println("<h1>Edit Task \""+ taskName +"\"</h1>");
    			}
		        	
    			
    			out.println("<form action=\"EditTasks\" method=\"post\">\n");
    			
        		out.println("Name:<br><input type=\"text\" name=\"name\" value=\""+ taskName +"\"><br>");
        		out.println("judgeTaskID/shortname:<br><input type=\"text\" name=\"judgeTaskID\" value=\""+ judgeTaskID +"\"><br>");
        		out.println("contest:<br><input type=\"text\" name=\"contest\" value=\""+ contest +"\"><br>");
        		out.println("<input type=\"hidden\" name=\"ID\" value=\""+ taskID +"\"> ");
        		out.println("descr:<br><textarea name=\"descr\" rows=\"10\" cols=\"100\">"+ descr +"</textarea><br>");
        		out.println("def:<br><textarea name=\"def\" rows=\"10\" cols=\"100\">"+ def +"</textarea><br>");
        		out.println("check:<br><textarea name=\"subm\" rows=\"10\" cols=\"100\">"+ subm +"</textarea><br>");
        		out.println("check:<br><textarea name=\"check\" rows=\"10\" cols=\"100\">"+ check +"</textarea><br>");
    			out.println("<input type=\"submit\" value =\"submit\">"
    					+ "</form>");

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
	         
	        printTaskEdit(out, conn, taskID); 
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("ERROR");
		}
         
        

    	out.println("</html>");
    	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String taskID = request.getParameter("ID");
		System.out.println("taskID is: " +  taskID);
		
		String name = request.getParameter("name");
		System.out.println("name is: " +  name);
		
		String judgeTaskID = request.getParameter("judgeTaskID");
		System.out.println("judgeTaskID is: " +  judgeTaskID);
		
		
		String descr = request.getParameter("descr");
		System.out.println("descr is: " +  descr);

		String def = request.getParameter("def");
		System.out.println("def is: " +  def);

		String check = request.getParameter("check");
		System.out.println("check is: " +  check);

		String contest = request.getParameter("contest");
		System.out.println("contest is: " +  contest);
		
		String subm = request.getParameter("subm");
		System.out.println("subm is: " +  subm);

		

    	PrintWriter out = response.getWriter(); 
        response.setContentType("text/html");
        out.println("<html><body>");
		
        // connects to the database
		Connection conn = null;
        try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	        conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
	          
	        if(taskID.equals("none")) {
	        	//create new
	        	System.out.println("create a new task");			            
	        	String sql = "INSERT INTO test.Task (name, judgeTaskID, description, defthy, checkthy, subthy, contest) values (?, ?, ?, ?, ?, ?, ?)";
	            PreparedStatement statement = conn.prepareStatement(sql);
	            statement.setString(1, name);
	            statement.setString(2, judgeTaskID);
	            statement.setString(3, descr);
	            statement.setString(4, def);
	            statement.setString(5, check);
	            statement.setString(6, subm);
	            statement.setString(7, contest);
	            // sends the statement to the database server
	            int row = statement.executeUpdate();
	            if (row > 0) {
	                out.println("successfully generated Task!<br>");
	            } 
	        } else {
	        	System.out.println("update Task");	            
	        	String sql = "UPDATE test.Task SET name = ?, judgeTaskID = ?, description = ?, defthy = ?, checkthy = ? , subthy = ?, contest = ? WHERE ID = ?";
	            PreparedStatement statement = conn.prepareStatement(sql);
	            statement.setString(1, name);
	            statement.setString(2, judgeTaskID);
	            statement.setString(3, descr);
	            statement.setString(4, def);
	            statement.setString(5, check);
	            statement.setString(6, subm);
	            statement.setString(7, contest);
	            statement.setString(8, taskID);
	            // sends the statement to the database server
	            int row = statement.executeUpdate();
	            if (row > 0) {
	                out.println("successfully updated Task!<br>");
	            } 
	        }
	        
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("ERROR");
		}
        
        
    	out.println("<br><a href=\"Tasks\">Back to Tasks</a>");

    	out.println("</html>");
		
		
		
	}
}