

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
 

import javax.naming.*;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/Tipps")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataSource dataSource;
	
    // database connection settings
    private String dbURL = "jdbc:mysql://localhost:3306/test";
    private String dbUser = "root";
    private String dbPass = "BoostedMonkey";
    
    
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//Context initCtx = new InitialContext();

		//Context envCtx = (Context) initCtx.lookup("java:comp/env");

		//DataSource ds = (DataSource)

		//envCtx.lookup("jdbc/[YourResourceName]");
		request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
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
