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
import entities.Barnbay;
import entities.Product;

/**
 * Servlet implementation class GetProductByCategoryAndFarmer
 */
public class GetProductByCategoryAndFarmer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProductByCategoryAndFarmer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String catid = request.getParameter("catid");
		String farmerid = request.getParameter("farmerid");
		DB_Connection db = new DB_Connection();
		
		ResultSet rs = db.executeSQL("SELECT * FROM TEST.PRODUCT WHERE FK_CATEGORY =" + catid + " AND FK_USER =" + farmerid);
		
		ArrayList<Product> a = new ArrayList<Product>();
		
		if(rs != null){
			try {
				while(rs.next()){
					Product p = new Product();
					p.id = rs.getInt(1);
					p.description = rs.getString(2);
					p.price = rs.getDouble(5);
					p.available = rs.getInt(8);
					p.stock = rs.getInt(7);
					
					ResultSet rs2 = db.executeSQL("SELECT * FROM TEST.USERD WHERE IDUSER =" + farmerid);
					if(rs2!=null && rs2.next()){
						int fk_barnbay = rs2.getInt(9);
						
						p.farmerfirstname = rs2.getString(2);
						p.farmerlastname =rs2.getString(3);
						
						ResultSet rs3 = db.executeSQL("SELECT * FROM TEST.BARNBAY WHERE IDBARNBAY =" + fk_barnbay);
						while(rs3!=null && rs3.next()){
							Barnbay b = new Barnbay();
							b.id = rs3.getInt(1);
							b.name = rs3.getString(2);
							b.description = rs3.getString(4);
							b.opening_hours = rs3.getString(5);
							p.barnbays.add(b);
						}
					}
					
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
