package test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DB_Connection;

/**
 * Servlet implementation class Initialize
 */
public class Initialize extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Initialize() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{

		DB_Connection db = new DB_Connection();
				
		db.executeSQL("CREATE SCHEMA TEST");
		db.executeSQL("CREATE TABLE TEST.PRODUCT ( IDPRODUCT INTEGER, PRODUCT_DESCRIPTION VARCHAR(100), FK_CATEGORY INTEGER, FK_USER INTEGER, PRICE DECIMAL(2,0), FK_OVERALL_RATING INTEGER, CURRENT_STOCK INTEGER, AVAILABLE INTEGER, SETUP_DATE DATE, DEACTIVATION_DATE DATE, FK_CURRENCY INTEGER, FK_AMOUNT_TYPE INTEGER)");
		db.executeSQL("INSERT INTO TEST.PRODUCT(IDPRODUCT, PRODUCT_DESCRIPTION, FK_CATEGORY,"
           		 + "FK_USER, PRICE, CURRENT_STOCK, FK_CURRENCY, FK_AMOUNT_TYPE) VALUES(0,"
            	 + "'Sehr saftige Apfelsorte, die Sie lieben werden.', 2, 0, 0.8, 100, 0, 0)");
		
		db.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
