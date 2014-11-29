package services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DB_Connection;

/**
 * Servlet implementation class GetFarmerByFarmerId
 */
public class GetNewsFeedActivity extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static DB_Connection db;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetNewsFeedActivity() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		db = new DB_Connection();
		
		getNewestProduct();
		db.executeSQL("DELETE FROM TEST.PRODUCT");
		
		db.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Get Newest Product from Database
	 */
	
	private void getNewestProduct() {
		db.executeSQL("DELETE FROM TEST.PRODUCT");
	}
}