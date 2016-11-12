

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.naming.*;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/Tipps")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataSource dataSource;
	
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
		doGet(request, response);
		
		String tippText = request.getParameter("tipptext");
		String aufgabe = request.getParameter("aufgabe");
		
		if (tippText.isEmpty() && aufgabe.isEmpty()) {
			request.setAttribute("error", "Error. No input.");
			
			request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
		}
		else {
			
		}
		}
			
	}

}
