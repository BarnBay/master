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

/**
 * Servlet implementation class GetBarnbayByBarnbayId
 */
public class GetBarnbayByBarnbayId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBarnbayByBarnbayId() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		DB_Connection db = new DB_Connection();
		ResultSet rs = db.executeSQL("SELECT * FROM TEST.BARNBAY WHERE IDBARNBAY=" + id);
		try {
			Barnbay b = new Barnbay();
			
			if(rs != null && rs.next()){
				
				b.id = rs.getInt(1);
				b.name = rs.getString(2);
				b.description = rs.getString(4);
				b.opening_hours = rs.getString(5);
				b.address = new Address();
				
				ResultSet rs1 = db.executeSQL("SELECT * FROM TEST.ADDRESS WHERE IDADDRESS=" + rs.getInt(6));
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
