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
		db.executeSQL("DROP TABLE TEST.CATEGORY");
		db.executeSQL("DROP TABLE TEST.FARM");
		db.executeSQL("DROP TABLE TEST.PRODUCT");
		db.executeSQL("DROP TABLE TEST.USERD");
		db.executeSQL("DROP TABLE TEST.USERTYPE");
		db.executeSQL("DROP TABLE TEST.ADDRESS");
		db.executeSQL("DROP TABLE TEST.TITLE");
		db.executeSQL("DROP TABLE TEST.BARNBAY");
		db.executeSQL("DROP SCHEMA TEST RESTRICT");
		db.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
