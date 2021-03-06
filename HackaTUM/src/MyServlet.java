
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.annotation.Resource;
import javax.naming.*;
import java.util.*;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/Tipps")
public class MyServlet extends HttpServlet {
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
    public MyServlet() {
        // TODO Auto-generated constructor stub
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get aufgaben id
		String id = request.getParameter("aufgabe");
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());

    	PrintWriter out = response.getWriter();
    	
        // connects to the database
		Connection conn = null;
        try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	        conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
	        if (conn!=null){
	        	
	        	// Get all tipps for this task
	        	PreparedStatement ps = conn.prepareStatement("select * from test.tipps where aufgabenid = ?");
	        	ps.setString(1, id);
	        	
	        	ResultSet result = ps.executeQuery();

	        	response.setContentType("application/json");
	        	out.println("{\"tipps\":[");
	        	while (result.next()){
	        		out.println("{");  
	        		out.println(getJsonLine("tippid", result.getString(1)));
	        		out.print(",");
	        		out.println(getJsonLine("taskid", result.getString(2)));
	        		out.print(",");
	        		out.println(getJsonLine("holeid", result.getString(3)));
	        		out.print(",");
	        		out.println(getJsonLine("rank", result.getString(4)));
	        		out.print(",");
	        		out.println(getJsonLine("cost", result.getString(5)));
	        		out.print(",");
	        		out.println(getJsonLine("text", result.getString(6)));
	        		out.print("}");
	        	}
	        	out.println("]}");
	        	response.setStatus(2);


	        }
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("ERROR");
		}
        
		//getting the XML file and posting it to the index file
		String xml = "<h1>Try sth</h1>";
		request.setAttribute("xmlString", "<h1>Try sth</h1>");
		//getServletContext().getRequestDispatcher("index.jsp").forward(request, response);

	}
	public String getJsonLine(String key, String value){
		return  "\"" +  key + "\": \"" + value + "\"";
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

		
		String tippText = request.getParameter("tipptext");
		String aufgabe = request.getParameter("aufgabe");
		
		// if logged in && input acceptable
		
		if (tippText.isEmpty() && aufgabe.isEmpty()) {
			request.setAttribute("Message", "Error. No input.");
			
			request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
		}
		// post to server
		else {
			
			Connection conn = null;
			String message = "";  // message will be sent back to client
	         
	        try {
	            // connects to the database
	            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
	 
	            // constructs SQL statement
	            String sql = "INSERT INTO test.tipps (taskid, cost, text) values (?, ?, ?)";
	            PreparedStatement statement = conn.prepareStatement(sql);
	            statement.setString(1, aufgabe);
	            statement.setString(2, "1");
	            statement.setString(3, tippText);
	            
	            // sends the statement to the database server
	            int row = statement.executeUpdate();
	            if (row > 0) {
	                message = "File uploaded and saved into database";
	            }
	        } catch (SQLException ex) {
	            message = "ERROR: " + ex.getMessage();
	            ex.printStackTrace();
	        } finally {
	            if (conn != null) {
	                // closes the database connection
	                try {
	                    conn.close();
	                } catch (SQLException ex) {
	                    ex.printStackTrace();
	                }
	            }
	            // sets the message in request scope
	            request.setAttribute("Message", message);
	             
	            // forwards to the message page
	            getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
	        }
		}	
	}
}
