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

import database.DB_Connection;
import entities.AddProduct;

public class AddProductToFarmerStock extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddProductToFarmerStock() {
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
		
		JSONObject jsonobject = new JSONObject();
		StringBuffer jb = new StringBuffer();
		DB_Connection dbconnect;
		String schema = DB_Connection.getSchemaName(request.getRequestURL().toString());
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

		jsonobject = JSON_Server.http_post_json(jb.toString());
		AddProduct receivedproduct = JSON_Server.jsonToProduct(jsonobject);

		// Inserting Product data:
		int max_product_id = 1;
		dbconnect = new DB_Connection();
		sql = "SELECT MAX(IDPRODUCT) FROM "+ schema + "PRODUCT";
		rs = dbconnect.executeSQL(sql);
		
		try {
			rs.next();
			max_product_id = rs.getInt(1) + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbconnect.close();
		
		// Creating SQL Statement		
		sql = "INSERT INTO "+ schema + "PRODUCT(IDPRODUCT, PRODUCT_DESCRIPTION, FK_CATEGORY,"
				+ "FK_USER, PRICE, CURRENT_STOCK, FK_CURRENCY, FK_AMOUNT_TYPE) VALUES(" + max_product_id + ","
				+ "'" + receivedproduct.productdescription + "', " + receivedproduct.categoryid + ", " 
				+ receivedproduct.userid + ", " + receivedproduct.productprice  + ", " + receivedproduct.productstock + ", 0, 0)";
		dbconnect = new DB_Connection();
		rs = dbconnect.executeSQL(sql);
		
		dbconnect.close();
		
		response.getWriter().print("{\"AddProduct\":\"success\"}");
	}

}