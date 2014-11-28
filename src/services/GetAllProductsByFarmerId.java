package services;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import json.JSON_Server;
import database.DB_Connection;
import entities.Product;

/**
 * Servlet implementation class GetFarmerByFarmerId
 */
public class GetAllProductsByFarmerId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllProductsByFarmerId() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		DB_Connection db = new DB_Connection();
		
		ResultSet rs = db.executeSQL("SELECT p.IDPRODUCT, c.Name, p.PRICE, p.CURRENT_STOCK, p.PRODUCT_DESCRIPTION FROM TEST.PRODUCT p, TEST.CATEGORY c WHERE FK_USER =" + id + " AND p.FK_CATEGORY = c.IDCATEGORY");
		
		if(rs!=null){
			ArrayList<Product> a = new ArrayList<Product>();
			try {
				while(rs.next()){
					Product p = new Product();
					p.id = rs.getInt(1);
					p.name = rs.getString(2);
					p.price = rs.getDouble(3);
					p.stock = rs.getInt(4);
					p.description = rs.getString(5);
					
					a.add(p);
				}
				String json = JSON_Server.productArrayToJson(a);
				response.getWriter().println(json);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		db.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
