

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.Part;

import java.net.HttpURLConnection;

/**
 * Servlet implementation class FileUploader
 */
@WebServlet("/Submission")
@MultipartConfig(maxFileSize = 16177215) 
public class Submission extends HttpServlet {
	private static final long serialVersionUID = 1L;


    // database connection settings
    private String dbURL = LocalSettings.dbURL;
    private String dbUser = LocalSettings.dbUser;
    private String dbPass = LocalSettings.dbPass;
     
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // gets values of text fields
    	Enumeration<String> parameterNames = request.getParameterNames();
    	while (parameterNames.hasMoreElements()) {
    	    String key = (String) parameterNames.nextElement();
    	    String val = request.getParameter(key);
    	    System.out.println("A= <"+key+"> Value<"+val+">");
    	}
        String aufgabe = request.getParameter("aufgabe");
        String code = request.getParameter("code[0]");
        System.out.println("code[0]:"+ code);
        String code2 = request.getParameter("code");
        System.out.println("code:"+ code2);
        String[] codes = request.getParameterValues("code[]");
        if(codes==null) {
        	System.out.println("codes is empty");
        } else {
        	System.out.println("code:"+ codes[0]);
        }
        java.util.Collection<Part> 	f = request.getParts();
        Iterator<Part> i = f.iterator();
        while (i.hasNext())
          {
        	System.out.println("-----");
        	Part name = (Part) i.next();
            System.out.println(name.getName());
            System.out.println(name.getSize());
            System.out.println(name.getHeaderNames());
          }
        
        
        InputStream inputStream = null; // input stream of the upload file
         
        // obtains the upload file part in this multipart request
        Part filePart = request.getPart("submission");
        if (filePart != null) {
            // prints out some information for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
             
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }
         
        try {

        	String langid = "";
        	String contest = "";
        	String shortname = "";
        	
			String submissionnumber = TUMJudgeConnection.submit("",shortname,langid,contest).trim();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        Connection conn = null; // connection to the database
        String message = "";  // message will be sent back to client
         
        try {
            // connects to the database
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
 
            // constructs SQL statement
            String sql = "INSERT INTO test.Submission (taskID, userID, judgeSubmissionID, content) values (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, aufgabe);
            statement.setString(2, "1");
            statement.setString(3, "blablub"); 
             
            if (inputStream != null) {
                // fetches input stream of the upload file for the blob column
                statement.setBlob(4, inputStream);
            }              
            
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
            getServletContext().getRequestDispatcher("/Tasks").forward(request, response);
        }
    }
}