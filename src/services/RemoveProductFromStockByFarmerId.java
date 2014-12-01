package services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DB_Connection;

/**
 * Servlet implementation class RemoveProductFromStrockByFarmerId
 * @author Patrick
 */
public class RemoveProductFromStockByFarmerId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Constructor
     * @see HttpServlet#HttpServlet()
     */
    public RemoveProductFromStockByFarmerId() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		DB_Connection db = new DB_Connection();
		String schema = DB_Connection.getSchemaName(request.getRequestURL().toString());
		
		db.executeSQL("DELETE FROM "+ schema + "PRODUCT WHERE IDPRODUCT=" + id);
		
		db.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}