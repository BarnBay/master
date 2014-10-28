package barnbay_server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class Hello
 */
public class Hello extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Hello() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx
					.lookup("java:comp/env/jdbc/DefaultDB");
			Connection con = ds.getConnection();
			//PreparedStatement pstmt = con
             //       .prepareStatement("SELECT NO FROM TEST");
			
			Statement s = con.createStatement();
            //s.execute("DROP TABLE TEST2");
            //s.execute("CREATE TABLE TEST2 (NUMBER CHAR)");
            s.execute("INSERT INTO TEST2 VALUES('1')");
            s.execute("INSERT INTO TEST2 VALUES('2')");
            s.execute("INSERT INTO TEST2 VALUES('3')");
            ResultSet rs = s.executeQuery("SELECT 'number' FROM TEST2");
            String s1 = "";
            if(rs.next()){
            	s1 = rs.getString(1);
            }
            response.getWriter().println("This is the response: " + s1);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			response.getWriter().println("Naming Exception");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response.getWriter().println("SQL Exception:   " + e.getStackTrace());
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
