package services;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import json.JSON_Server;

import org.json.simple.JSONObject;

import database.DB_Connection;
import entities.Farmer;

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
		
		Farmer f = new Farmer();
		ResultSet rs = db.executeSQL("SELECT * FROM TEST.USERD WHERE IDUSER =" + id);
		int fk_address = 0;
		int fk_farm = 0;
		int fk_barnbay = 0;
		try {
			if(rs != null && rs.next()){
				f.idfarmer = rs.getInt(1);
				f.firstname = rs.getString(2);
				f.lastname = rs.getString(3);
				fk_address = rs.getInt(7);
				fk_farm = rs.getInt(8);
				fk_barnbay = rs.getInt(9);
				
				ResultSet rs2 = db.executeSQL("SELECT * FROM TEST.ADDRESS WHERE IDADDRESS =" + fk_address);
				if(rs2 != null && rs2.next()){
					f.street = rs2.getString(2);
					f.number = rs2.getString(3);
					f.zipcode = rs2.getString(4);
					f.city = rs2.getString(5);
				}
				
				ResultSet rs3 = db.executeSQL("SELECT * FROM TEST.FARM WHERE IDFARM=" + fk_farm);
				if(rs3 != null && rs3.next()){
					f.farmname = rs3.getString(2);
					f.farmpicture = rs3.getString(3);
					f.farmdescription = rs3.getString(4);
					f.farmopeninghours = rs3.getString(5);
				}
				JSONObject json = JSON_Server.wrap_JSON(f);
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
