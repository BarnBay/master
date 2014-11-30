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
import entities.ProductOrdered;
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
    	String teststring = "{\"username\":\"peterm\",\"session\":\"asdfusw\"}";
    	JSONObject jsonobject = new JSONObject();
    	JSONParser jsonparser = new JSONParser();
    	User farmer = new User();
    	
    	try {
    	    BufferedReader reader = request.getReader();
    	    while ((line = reader.readLine()) != null)
    	      jb.append(line);
    	  } catch (Exception e) { /*report an error*/ }

    	//jsonobject = JSON_Server.http_post_json(jb.toString());
    	jsonobject = JSON_Server.http_post_json(teststring);
    	
		User receiveduser = JSON_Server.jsonToUser(jsonobject); 
    	
		ProductOrdered[] products;
		
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
		
		String sql;
		String attributes = "bb.name, cat.name, o.pickup_date, orderproduct.amount";
		String tables = "TEST.BARNBAY bb, TEST.CATEGORY cat, TEST.ORDER_HAS_PRODUCT orderproduct, TEST.ORDERS o, TEST.USERD barnbayuser, TEST.USERD farmer, TEST.PRODUCT prod";
		String where_clause = "farmer.username = '" + receiveduser.username + "' AND farmer.idUser = prod.fk_User AND prod.fk_category = cat.idCategory " + 
		                      "AND orderproduct.fk_Product = prod.idProduct AND orderproduct.fk_Order = o.idOrder AND o.fk_Users_pickup = barnbayuser.idUser " + 
				              "AND barnbayuser.fk_BarnBay = bb.idBarnBay";
		
		sql = "SELECT " + attributes + " FROM " + tables + " WHERE " + where_clause;
		
		DB_Connection db_connect = new DB_Connection();
		ResultSet rs = db_connect.executeSQL(sql);
		
		try {
			while (rs.next()) {
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getString(3));
				System.out.println(rs.getString(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		db_connect.close();
		
    }
	
}
