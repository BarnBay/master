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
import entities.Address;
import entities.Barnbay;
import entities.Farmer;
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
		String schema = DB_Connection.getSchemaName(request.getRequestURL().toString());
		
		ResultSet rs = db.executeSQL("SELECT * FROM " + schema + "PRODUCT WHERE FK_CATEGORY =" + catid + " AND FK_USER =" + farmerid);
		
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
					
					ResultSet rs2 = db.executeSQL("SELECT * FROM " + schema + "USERD WHERE IDUSER =" + farmerid);
					int fk_address;
					if(rs2!=null && rs2.next()){
						int fk_barnbay = rs2.getInt(9);
						
						p.farmerfirstname = rs2.getString(2);
						p.farmerlastname =rs2.getString(3);
						p.farmeremail = rs2.getString(10);
						fk_address = rs2.getInt(7);
						
						ResultSet rs4 = db.executeSQL("SELECT * FROM " + schema + "ADDRESS WHERE IDADDRESS=" + fk_address);
						p.farmeraddress = new Address();
						if(rs4!=null && rs4.next()){
							p.farmeraddress.idaddress = rs4.getInt(1);
							p.farmeraddress.street = rs4.getString(2);
							p.farmeraddress.number = rs4.getString(3);
							p.farmeraddress.zipcode = rs4.getString(4);
							p.farmeraddress.city = rs4.getString(5);
						}
						
						ResultSet rs3 = db.executeSQL("SELECT * FROM " + schema + "BARNBAY WHERE IDBARNBAY =" + fk_barnbay);
						while(rs3!=null && rs3.next()){
							Barnbay b = new Barnbay();
							b.id = rs3.getInt(1);
							b.name = rs3.getString(2);
							b.description = rs3.getString(4);
							b.opening_hours = rs3.getString(5);
							
							ResultSet rs5 = db
									.executeSQL("SELECT * FROM " + schema + "ADDRESS WHERE IDADDRESS="
											+ rs3.getInt(6));
							if(rs5 != null && rs5.next()){
								b.address = new Address();
								
								b.address.idaddress = rs5.getInt(1);
								b.address.street = rs5.getString(2);
								b.address.number = rs5.getString(3);
								b.address.zipcode = rs5.getString(4);
								b.address.city = rs5.getString(5);
							}
							
							p.barnbays.add(b);
						}
						
						ResultSet rs6 = db.executeSQL("SELECT * FROM " + schema + "PRODUCT WHERE FK_CATEGORY=" + catid);
						ResultSet rs7;
						while(rs6!=null && rs6.next()){
							rs7 = db.executeSQL("SELECT * FROM " + schema + "USERD WHERE IDUSER=" + rs6.getInt(4));
							while(rs7!=null && rs7.next()){
								Farmer f = new Farmer();
								f.idfarmer = rs7.getInt(1);
								f.firstname = rs7.getString(2);
								f.lastname = rs7.getString(3);
								f.email = rs7.getString(10);
								
								fk_address = rs7.getInt(7);
								
								ResultSet rs8 = db.executeSQL("SELECT * FROM " + schema + "ADDRESS WHERE IDADDRESS=" + fk_address);
								if(rs8 != null && rs8.next()){
									f.street = rs8.getString(2);
									f.number = rs8.getString(3);
									f.zipcode = rs8.getString(4);
									f.city = rs8.getString(5);
								}
								
								p.farmers.add(f);
							}
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
