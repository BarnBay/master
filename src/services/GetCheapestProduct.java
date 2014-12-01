package services;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
 * Servlet implementation class GetCheapestProduct,
 * which offers a json-service to the frontend
 * @author Anne
 * 
 */
public class GetCheapestProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @see HttpServlet#HttpServlet()
	 */
	public GetCheapestProduct() {
		super();
	}

	/**
	 * Method which is executed when the servlet receives an http get request
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String paramName = "id";
		String id = request.getParameter(paramName);
		DB_Connection db = new DB_Connection();
		String schema = DB_Connection.getSchemaName(request.getRequestURL().toString());

		ResultSet rs = db
				.executeSQL("SELECT * FROM " + schema + "CATEGORY WHERE IDCATEGORY ="
						+ id);
		if (rs != null) {
			Product p = new Product();
			try {
				if (rs.next()) {
					p.name = rs.getString(2);
					p.description = rs.getString(4);

					
					ResultSet rs2 = db
							.executeSQL("SELECT MIN(PRICE) FROM " + schema + "PRODUCT WHERE FK_CATEGORY="
									+ id);
					if (rs2 != null && rs2.next()) {
						p.price = rs2.getDouble(1);

						ResultSet rs3 = db
								.executeSQL("SELECT * FROM " + schema + "PRODUCT WHERE FK_CATEGORY="
										+ id + " AND PRICE=" + p.price);
						int fk_farmer = 0;
						if (rs3 != null && rs3.next()) {
							p.id = rs.getInt(1);
							p.available = rs3.getInt(8);
							p.stock = rs3.getInt(7);
							p.farmerproductdescription =rs3.getString(2);
							fk_farmer = rs3.getInt(4);

							int fk_barnbay = 0;
							int fk_address = 0;
							ResultSet rs4 = db
									.executeSQL("SELECT * FROM " + schema + "USERD WHERE IDUSER="
											+ fk_farmer);
							if (rs4 != null && rs4.next()) {
								p.farmerfirstname = rs4.getString(2);
								p.farmerlastname = rs4.getString(3);
								p.farmeremail = rs4.getString(10);
								fk_barnbay = rs4.getInt(9);
								fk_address = rs4.getInt(7);
								
								ResultSet rs45 = db.executeSQL("SELECT * FROM " + schema + "ADDRESS WHERE IDADDRESS=" + fk_address);
								p.farmeraddress = new Address();
								if(rs45!=null && rs45.next()){
									p.farmeraddress.idaddress = rs45.getInt(1);
									p.farmeraddress.street = rs45.getString(2);
									p.farmeraddress.number = rs45.getString(3);
									p.farmeraddress.zipcode = rs45.getString(4);
									p.farmeraddress.city = rs45.getString(5);
								}

								ResultSet rs5 = db
										.executeSQL("SELECT * FROM " + schema + "BARNBAY WHERE IDBARNBAY="
												+ fk_barnbay);

								while (rs5 != null && rs5.next()) {
									Barnbay b = new Barnbay();
									b.id = rs5.getInt(1);
									b.name = rs5.getString(2);
									b.description = rs5.getString(4);
									b.opening_hours = rs5.getString(5);

									ResultSet rs56 = db
											.executeSQL("SELECT * FROM " + schema + "ADDRESS WHERE IDADDRESS="
													+ rs5.getInt(6));
									if(rs56 != null && rs56.next()){
										b.address = new Address();
										
										b.address.idaddress = rs56.getInt(1);
										b.address.street = rs56.getString(2);
										b.address.number = rs56.getString(3);
										b.address.zipcode = rs56.getString(4);
										b.address.city = rs56.getString(5);
									}
									
									p.barnbays.add(b);
								}
								
								ResultSet rs6 = db
										.executeSQL("SELECT * FROM " + schema + "PRODUCT WHERE FK_CATEGORY="
												+ id);
								ResultSet rs7;
								while (rs6 != null && rs6.next()) {
									rs7 = db.executeSQL("SELECT * FROM " + schema + "USERD WHERE IDUSER="
											+ rs6.getInt(4));
									while (rs7 != null && rs7.next()) {
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

						}
					}

					String json = JSON_Server.objectToJson(p);
					response.getWriter().println(json);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		db.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
