package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import json.JSON_Server;
import database.DB_Connection;
import entities.Product;
import entities.User;

public class GetAllOrdersByUserId extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllOrdersByUserId() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Method which is executed when the servlet receives an http get request
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	StringBuffer jb = new StringBuffer();
    	String line;
    	//String teststring = "{\"username\":\"peterm\",\"session\":\"asdfusw\"}";
    	JSONObject jsonobject = new JSONObject();
    	JSONParser jsonparser = new JSONParser();
    	User farmer = new User();
    	
    	try {
    	    BufferedReader reader = request.getReader();
    	    while ((line = reader.readLine()) != null)
    	      jb.append(line);
    	  } catch (Exception e) { /*report an error*/ }

    	jsonobject = JSON_Server.http_post_json(jb.toString());
    	//jsonobject = JSON_Server.http_post_json(teststring);
    	
		User receiveduser = JSON_Server.jsonToUser(jsonobject); 
    	
		
		String schema = DB_Connection.getSchemaName(request.getRequestURL().toString());
		
		String json_string = "";
		JSONObject json = new JSONObject();
		String sql;
		String attributes = "bb.name, cat.name, o.pickup_date, orderproduct.amount ";
		String tables = schema + "BARNBAY bb, " + schema + "CATEGORY cat, " + schema + "ORDER_HAS_PRODUCT orderproduct, " + schema + "ORDERS o, " + 
						schema + "USERD barnbayuser, " + schema + "USERD customer, " + schema + "PRODUCT prod";
		String where_clause = "customer.username = '" + receiveduser.username + "' AND customer.idUser = o.FK_USERS_CUSTOMER AND prod.fk_category = cat.idCategory " + 
		                      "AND orderproduct.fk_Product = prod.idProduct AND orderproduct.fk_Order = o.idOrder AND o.fk_Users_pickup = barnbayuser.idUser " + 
				              "AND barnbayuser.fk_BarnBay = bb.idBarnBay";
		String sort = "o.pickup_date DESC";
		
		sql = "SELECT " + attributes + " FROM " + tables + " WHERE " + where_clause + " ORDER BY " + sort;
		
		System.out.println("SQL-String: " + sql);
		
		DB_Connection db_connect = new DB_Connection();
		ResultSet rs = db_connect.executeSQL(sql);
		
		
		int i = 1;
		json_string = "{ \"pickup\" : [ ";
		String comma = "";
		
		if (rs != null) {
		
			try {
				while (rs.next()) {
					if (i > 1) {
						comma = ",";
					}
					i++;
					json_string = json_string + comma + " { ";
					json_string = json_string + "\"barnbay\" : " + "\"" + rs.getString(1) + "\", " + 
							"\"productcategory\" : \"" + rs.getString(2) + "\", " +
							"\"deliverydate\" : \"" + rs.getString(3) + "\", " +
							"\"amount\" : \"" + rs.getString(4) + "\" ";
							
					json_string = json_string + " }  ";		
					//System.out.print(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4)) ;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		json_string = json_string + "] }";
		
		response.getWriter().print(json_string);
		
		db_connect.close();
		
    }
	
}
