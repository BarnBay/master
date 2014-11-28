package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import json.JSON_Server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import database.DB_Connection;
import entities.AddProduct;

public class UpdateFarmerProductStock extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateFarmerProductStock() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.getWriter().println("");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobject = new JSONObject();
		StringBuffer jb = new StringBuffer();
		DB_Connection dbconnect;
		String line;
		String sql;
		ResultSet rs;
		
		try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		} catch (Exception e) {
			/*report an error*/
		}
		
		dbconnect = new DB_Connection();
		
		jsonarray = JSON_Server.http_post_jsonArray(jb.toString(), "product");
		
		for(int i = 0; i < jsonarray.size(); i++) {
			jsonobject = (JSONObject) jsonarray.get(i);		
			
			Integer id = Integer.parseInt(jsonobject.get("Id").toString());
			Integer stock = Integer.parseInt(jsonobject.get("Stock").toString());
			Double price = Double.parseDouble(jsonobject.get("Price").toString());
			String description = jsonobject.get("Description").toString();
			
			sql = "SELECT * FROM TEST.PRODUCT WHERE IDPRODUCT = " + id;
			rs = dbconnect.executeSQL(sql);
		
			try {
				while (rs.next()) {
					if(!(rs.getString("PRODUCT_DESCRIPTION").equals(description)) || rs.getDouble("PRICE") != price || rs.getInt("CURRENT_STOCK") != stock) {
						sql = "UPDATE TEST.PRODUCT SET PRODUCT_DESCRIPTION='" + description + "', PRICE=" + price + ", CURRENT_STOCK=" + stock + " WHERE IDPRODUCT = " + id;
						dbconnect.executeSQL(sql);
					}
				} 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		dbconnect.close();
		
		response.getWriter().print("{\"AddProduct\":\"success\"}");
	}

}