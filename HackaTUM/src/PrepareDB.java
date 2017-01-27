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
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import javax.annotation.Resource;
import javax.naming.*;
import java.util.*;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/PrepareDB") 
	public class PrepareDB extends HttpServlet {
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
	    public PrepareDB() {
	        // TODO Auto-generated constructor stub
	    }
	    
	    
		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
					

	    	PrintWriter out = response.getWriter(); 
            response.setContentType("text/html");
            out.println("<html><body>");

        	//response.setContentType("application/json");
			
	        // connects to the databases
			Connection conn = null;
	        try {
				DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		        conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
		        if (conn!=null){

		             // create the database "test" if not already existent
		        	 try {
		                 Statement s = conn.createStatement();
		                 int myResult = s.executeUpdate("CREATE DATABASE IF NOT EXISTS test");
		                 System.out.println("Create Database: " + myResult);
		                 out.println("Create Database: " + myResult + "<br>");
		             } 
		             catch (SQLException e) {
		                 e.printStackTrace();
		             }
		        	 
		        	 // Create Table "Task"
		        	 try {
		                 System.out.println("Create Table 'Task': ");
		        		 
		                 Statement s = conn.createStatement();
		                 int myResult = s.executeUpdate("CREATE TABLE IF NOT EXISTS Task ("
		                		 		+ "ID INT(64) NOT NULL AUTO_INCREMENT, "
		                		 		+ "name TEXT, "
		                		 		+ "judgeTaskID TEXT, "
		                		 		+ "description TEXT, "
		                		 		+ "checkthy TEXT, "
		                		 		+ "defthy TEXT, "
		                		 		+ "contest TEXT, "
		                		 		+ "subthy TEXT, "
		                		 		+ "PRIMARY KEY (`ID`));");
		                 System.out.println("successfull");
		                 out.println("Create Table 'Task': " + myResult + "<br>");
		             } 
		             catch (SQLException e) {
		                 e.printStackTrace();
		                 out.println("Create Table 'Task': ERROR<br>");
		             }

		        	 // Fill "Task" with some content
		        	 try {
			            // constructs SQL statement
			            String sql = "INSERT INTO test.Task (name, judgeTaskID, description, defthy, checkthy, contest, subthy) values (?, ?, ?, ?, ?, ?, ?)";
			            PreparedStatement statement = conn.prepareStatement(sql);
			            statement.setString(1, "mod9");
			            statement.setString(2, "mod9");
			            statement.setString(3, "In this task you have to do some crazy stuff!");
			            statement.setString(4, "Definition");
			            statement.setString(5, "Check");
			            statement.setString(7, "Submission");
			            statement.setString(6, "helloworld");
			            // sends the statement to the database server
			            int row = statement.executeUpdate();
			            if (row > 0) {
			                out.println("successfully generated Task1<br>");
			            }
		        	 }
		             catch (SQLException e) {
		                 e.printStackTrace();
		                 out.println("Filling 'Task': ERROR<br>");
		             }
		        	 try {
				            // constructs SQL statement
		        		 	String sql = "INSERT INTO test.Task (name, judgeTaskID, description, defthy, checkthy, contest, subthy) values (?, ?, ?, ?, ?, ?, ?)";
				            PreparedStatement statement = conn.prepareStatement(sql);
				            statement.setString(1, "name2");
				            statement.setString(2, "trivial");
				            statement.setString(3, "Rev! In this task you have to do some real stuff!");
				            statement.setString(4, "Definition2");
				            statement.setString(5, "Check2");
				            statement.setString(7, "Submission");
				            statement.setString(6, "tutorial");
				            // sends the statement to the database server
				            int row = statement.executeUpdate();
				            if (row > 0) {
				                out.println("successfully generated Task2<br>");
				            }
			        	 }
			             catch (SQLException e) {
			                 e.printStackTrace();
			                 out.println("Filling 'Task': ERROR<br>");
			             }
		        	 

		        	 // Create Table "Submission"
		        	 try {
		                 System.out.println("Create Table 'Submission': ");
		        		 
		                 Statement s = conn.createStatement();
		                 int myResult = s.executeUpdate("CREATE TABLE IF NOT EXISTS Submission ("
		                		 		+ "ID INT(64) NOT NULL AUTO_INCREMENT, "
		                		 		+ "userID INT(64), "
		                		 		+ "taskID INT(64), "
		                		 		+ "content TEXT, "
		                		 		+ "time TIMESTAMP  DEFAULT CURRENT_TIMESTAMP, "
		                		 		+ "judgeSubmissionID TEXT, " 
		                		 		+ "PRIMARY KEY (`ID`));");
		                 System.out.println("successfull");
		                 out.println("Create Table 'Submission': " + myResult + "<br>");
		             } 
		             catch (SQLException e) {
		                 e.printStackTrace();
		                 out.println("Create Table 'Submission': ERROR<br>");
		             }

		        	 // Fill "Submission" with some content
		        	 try {
			            // constructs SQL statement
			            String sql = "INSERT INTO test.Submission (userID, taskID, judgeSubmissionID, content ) values (?, ?, ?, ?)";
			            PreparedStatement statement = conn.prepareStatement(sql);
			            statement.setString(1, "1");
			            statement.setString(2, "1");
			            statement.setString(3, "123");
			            statement.setString(4, "blabluabklkak");
			            // sends the statement to the database server
			            int row = statement.executeUpdate();
			            if (row > 0) {
			                out.println("successfully generated a Submission<br>");
			            }
		        	 }
		             catch (SQLException e) {
		                 e.printStackTrace();
		                 out.println("Filling 'Task': ERROR<br>");
		             }
		        	 
		        	 // Create Table "User"
		        	 try {
		                 System.out.println("Create Table 'User': ");
		        		 
		                 Statement s = conn.createStatement();
		                 int myResult = s.executeUpdate("CREATE TABLE IF NOT EXISTS User ("
		                		 		+ "ID INT(64) NOT NULL AUTO_INCREMENT, "
		                		 		+ "pwd TEXT, "
		                		 		+ "PRIMARY KEY (`ID`));");
		                 System.out.println("successfull");
		                 out.println("Create Table 'User': " + myResult + "<br>");
		             } 
		             catch (SQLException e) {
		                 e.printStackTrace();
		                 out.println("Create Table 'User': ERROR<br>");
		             }
		        	 
		        	 
		        	 // Create Table "Grading"
		        	 try {
		                 System.out.println("Create Table 'Grading': ");
		        		 
		                 Statement s = conn.createStatement();
		                 int myResult = s.executeUpdate("CREATE TABLE IF NOT EXISTS Grading ("
		                		 		+ "ID INT(64) NOT NULL AUTO_INCREMENT, "
		                		 		+ "submissionID INT(64), "
		                		 		+ "result INT(64), "
		                		 		+ "PRIMARY KEY (`ID`));");
		                 System.out.println("successfull");
		                 out.println("Create Table 'Grading': " + myResult + "<br>");
		             } 
		             catch (SQLException e) {
		                 e.printStackTrace();
		                 out.println("Create Table 'Grading': ERROR<br>");
		             }
		        	 
		        	  
		        	response.setStatus(HttpServletResponse.SC_OK);
		        }
		        
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            out.println("</body><html>");
			//getting the XML file and posting it to the index file
	        
	        
			//getServletContext().getRequestDispatcher("index.jsp").forward(request, response);

		}
}