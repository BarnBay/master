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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import database.DB_Connection;
import entities.User;

public class GetAllOrdersByFarmerId extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllOrdersByFarmerId() {
        super();
        // TODO Auto-generated constructor stub
    }
    
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
    	
		
		// DB Select:
		// SELECT bb.name, cat.name, o.pickup_date, orderproduct.amount
		//   FROM BarnBay bb, Category cat, Order_has_Product orderproduct, Order o, Userd barnbay_user, Userd farmer, Product prod 
		//   WHERE farmer.username = receiveduser.username AND farmer.idUser = prod.fk_User AND prod.fk_category = cat.idCategory
		//       AND orderproduct.fk_Product = prod.idProduct AND orderproduct.fk_Order = o.idOrder AND o.fk_User_pickup = barnbay_user.idUsers
		//       AND barnbay_user.fk_BarnBay = Barn_Bay.idBarnBay
		//   
		// Farmer wants information about:
		// Deliver to which location
		// Which product
		// How many products
		// Pickup date
		
		
		String json_string = "";
		JSONObject json = new JSONObject();
		String sql;
		String attributes = "bb.name, cat.name, o.pickup_date, SUM(orderproduct.amount)";
		String tables = "TEST.BARNBAY bb, TEST.CATEGORY cat, TEST.ORDER_HAS_PRODUCT orderproduct, TEST.ORDERS o, TEST.USERD barnbayuser, TEST.USERD farmer, TEST.PRODUCT prod";
		String where_clause = "farmer.username = '" + receiveduser.username + "' AND farmer.idUser = prod.fk_User AND prod.fk_category = cat.idCategory " + 
		                      "AND orderproduct.fk_Product = prod.idProduct AND orderproduct.fk_Order = o.idOrder AND o.fk_Users_pickup = barnbayuser.idUser " + 
				              "AND barnbayuser.fk_BarnBay = bb.idBarnBay";
		String group = "o.pickup_date, cat.name, bb.name";
		
		sql = "SELECT " + attributes + " FROM " + tables + " WHERE " + where_clause + " GROUP BY " + group;
		
		//System.out.println("SQL-String: " + sql);
		
		DB_Connection db_connect = new DB_Connection();
		ResultSet rs = db_connect.executeSQL(sql);
		
		
		int i = 1;
		json_string = "{ \"delivery\" : [ ";
		String comma = "";
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
		
		json_string = json_string + "] }";
		
		response.getWriter().print(json_string);
		
		db_connect.close();
		
    }
	
}
