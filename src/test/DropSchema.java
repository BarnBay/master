package test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DB_Connection;

/**
 * Servlet implementation class DropSchema
 */
public class DropSchema extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DropSchema() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DB_Connection db = new DB_Connection();
		String schema = DB_Connection.getSchemaName(request.getRequestURL().toString());
		
		db.executeSQL("DROP TABLE " + schema + "CATEGORY");
		db.executeSQL("DROP TABLE " + schema + "FARM");
		db.executeSQL("DROP TABLE " + schema + "PRODUCT");
		db.executeSQL("DROP TABLE " + schema + "USERD");
		db.executeSQL("DROP TABLE " + schema + "USERTYPE");
		db.executeSQL("DROP TABLE " + schema + "ADDRESS");
		db.executeSQL("DROP TABLE " + schema + "TITLE");
		db.executeSQL("DROP TABLE " + schema + "BARNBAY");
		if(schema== "TEST."){
			db.executeSQL("DROP SCHEMA TEST RESTRICT");
		}
		db.close();
		
		response.getWriter().println("Schema dropped.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
