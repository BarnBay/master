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
 * Servlet implementation class GetFarmerByFarmerId
 */
public class GetFarmerByFarmerId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFarmerByFarmerId() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		DB_Connection db = new DB_Connection();
		String schema = DB_Connection.getSchemaName(request.getRequestURL().toString());
		
		Farmer f = new Farmer();
		ResultSet rs = db.executeSQL("SELECT * FROM " + schema + "USERD WHERE IDUSER =" + id);
		int fk_address = 0;
		int fk_farm = 0;
		int fk_barnbay = 0;
		try {
			if(rs != null && rs.next()){
				f.idfarmer = rs.getInt(1);
				f.firstname = rs.getString(2);
				f.lastname = rs.getString(3);
				f.email = rs.getString(10);
				fk_address = rs.getInt(7);
				fk_farm = rs.getInt(8);
				fk_barnbay = rs.getInt(9);
				
				ResultSet rs1 = db.executeSQL("SELECT * FROM " + schema + "PRODUCT WHERE FK_USER =" + id);
				f.products = new ArrayList<Product>();
				while(rs1 !=null && rs1.next()){
					Product p = new Product();
					p.id = rs1.getInt(1);
					p.farmerproductdescription = rs1.getString(2);
					p.price = rs1.getDouble(5);
					p.stock = rs1.getInt(7);
					p.available = rs1.getInt(8);
					
					ResultSet rs12 = db.executeSQL("SELECT * FROM " + schema + "CATEGORY WHERE IDCATEGORY =" + rs1.getInt(3));
					if(rs12 != null && rs12.next()){
						p.name = rs12.getString(2);
						p.catid = rs12.getInt(1);
						p.description = rs12.getString(4);
					}
					
					f.products.add(p);
				}
				
				ResultSet rs2 = db.executeSQL("SELECT * FROM " + schema + "ADDRESS WHERE IDADDRESS =" + fk_address);
				if(rs2 != null && rs2.next()){
					f.street = rs2.getString(2);
					f.number = rs2.getString(3);
					f.zipcode = rs2.getString(4);
					f.city = rs2.getString(5);
				}
				
				ResultSet rs3 = db.executeSQL("SELECT * FROM " + schema + "FARM WHERE IDFARM=" + fk_farm);
				if(rs3 != null && rs3.next()){
					f.farmname = rs3.getString(2);
					f.farmpicture = rs3.getString(3);
					f.farmdescription = rs3.getString(4);
					f.farmopeninghours = rs3.getString(5);
				}
				
				ResultSet rs4 = db.executeSQL("SELECT * FROM " + schema + "BARNBAY WHERE IDBARNBAY=" + fk_barnbay);
				f.barnbays = new ArrayList<Barnbay>();
				while(rs4 != null && rs4.next()){
					Barnbay b = new Barnbay();
					b.id = rs4.getInt(1);
					b.name = rs4.getString(2);
					b.description = rs4.getString(4);
					b.opening_hours = rs4.getString(5);
					
					ResultSet rs5 = db.executeSQL("SELECT * FROM " + schema + "ADDRESS WHERE IDADDRESS=" + rs4.getInt(6));
					b.address = new Address();
					if(rs5 != null && rs5.next()){
						b.address.idaddress = rs5.getInt(1);
						b.address.street = rs5.getString(2);
						b.address.number = rs5.getString(3);
						b.address.zipcode = rs5.getString(4);
						b.address.city = rs5.getString(5);
					}
					
					f.barnbays.add(b);
				}
				
				String json = JSON_Server.objectToJson(f);
				response.getWriter().println(json);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
