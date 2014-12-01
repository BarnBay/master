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

/**
 * Servlet implementation class GetBarnbayByBarnbayId,
 * which offers a json-service to the frontend
 * @author Anne
 * 
 */
public class GetBarnbayByBarnbayId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Constructor
     * @see HttpServlet#HttpServlet()
     */
    public GetBarnbayByBarnbayId() {
        super();
    }

	/**
	 * Method which is executed when the servlet receives an http get request
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		DB_Connection db = new DB_Connection();
		String schema = DB_Connection.getSchemaName(request.getRequestURL().toString());
		ResultSet rs = db.executeSQL("SELECT * FROM " + schema + "BARNBAY WHERE IDBARNBAY=" + id);
		try {
			Barnbay b = new Barnbay();
			
			if(rs != null && rs.next()){
				
				b.id = rs.getInt(1);
				b.name = rs.getString(2);
				b.description = rs.getString(4);
				b.opening_hours = rs.getString(5);
				b.address = new Address();
				
				ResultSet rs1 = db.executeSQL("SELECT * FROM " + schema + "ADDRESS WHERE IDADDRESS=" + rs.getInt(6));
				if(rs1 != null && rs1.next()){
					b.address.idaddress = rs1.getInt(1);
					b.address.street = rs1.getString(2);
					b.address.number = rs1.getString(3);
					b.address.zipcode = rs1.getString(4);
					b.address.city = rs1.getString(5);
				}
			}
			String json = JSON_Server.objectToJson(b);
			response.getWriter().println(json);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
