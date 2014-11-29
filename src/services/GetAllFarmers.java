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
import entities.Farmer;

/**
 * Servlet implementation class GetAllFarmers
 */
public class GetAllFarmers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllFarmers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DB_Connection db = new DB_Connection();
		String schema = DB_Connection.getSchemaName(request.getRequestURL().toString());
		
		ResultSet rs = db.executeSQL("SELECT * FROM " + schema + "USERD WHERE FK_USERTYPE=1");
		if(rs!=null){
			ArrayList<Farmer> a = new ArrayList<Farmer>();
			try {
				while(rs.next()){
					Farmer f = new Farmer();
					f.idfarmer = rs.getInt(1);
					f.firstname = rs.getString(2);
					f.lastname = rs.getString(3);
					
					int fk_farm = rs.getInt(8);
					int fk_address = rs.getInt(7);
					
					ResultSet rs2 = db.executeSQL("SELECT * FROM " + schema + "FARM WHERE IDFARM="+fk_farm);
					if(rs2!=null){
						while(rs2.next()){
							f.farmname = rs2.getString(2);
							f.farmpicture = rs2.getString(3);
							f.farmdescription =rs2.getString(4);
						}	
					}
					ResultSet rs3 = db.executeSQL("SELECT * FROM " + schema + "ADDRESS WHERE IDADDRESS="+fk_address);
					if(rs3!=null){
						while(rs3.next()){
							f.street = rs3.getString(2);
							f.number = rs3.getString(3);
							f.zipcode = rs3.getString(4);
							f.city = rs3.getString(5);
						}	
					}
					
					a.add(f);
					
				}
				
				String json = JSON_Server.farmerArrayToJson(a);
				response.getWriter().println(json);
				db.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
