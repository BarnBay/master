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
import org.json.simple.parser.JSONParser;

import database.DB_Connection;
import entities.User;

public class SetNewOrders extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SetNewOrders() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		StringBuffer jb = new StringBuffer();
		String line;
		JSONObject jsonobject = new JSONObject();
		JSONParser jsonparser = new JSONParser();
		User farmer = new User();

		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) { /* report an error */
		}

		jsonobject = JSON_Server.http_post_json(jb.toString());
		// jsonobject = JSON_Server.http_post_json(teststring);

		String[][] orders = new String[100][6];

		String schema = DB_Connection.getSchemaName(request.getRequestURL()
				.toString());
		String sql;
		DB_Connection dbconnect;
		dbconnect = new DB_Connection();
		ResultSet rs;

		String username = (String) jsonobject.get("username");
		String pickupdate = (String) jsonobject.get("pickupdate");

		Integer product_id = 0;
		JSONObject currentrow;
		JSONArray jsonarray = (JSONArray) jsonobject.get("orders");
		for (int i = 0; i < jsonarray.size(); i++) {
			currentrow = (JSONObject) jsonarray.get(i);
			orders[i][0] = (String) currentrow.get("productID");
			orders[i][1] = (String) currentrow.get("productQuantity");

			orders[i][2] = (String) currentrow.get("productPrice");
			orders[i][3] = (String) currentrow.get("barnbayID");
			orders[i][4] = (String) currentrow.get("farmerID");

			// Calculating the product id from the information.
			sql = "SELECT IDPRODUCT FROM " + schema
					+ "PRODUCT WHERE FK_CATEGORY = " + orders[i][0]
					+ " AND FK_USER = " + orders[i][4];
			System.out.println(sql);

			rs = dbconnect.executeSQL(sql);
			if (rs != null) {
				try {
					rs.next();
					product_id = rs.getInt(1);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			orders[i][5] = product_id.toString();
		}

		// Generate multiple orders based on barnbayid:
		int step = 1;
		int j = 0;
		int n = 0;
		int max_length_db_orders = 0;
		String product_orders[][] = new String[100][3];
		String db_orders[][] = new String[100][3];
		boolean isPresent;
		// Find out all different barnbayids:

		while (orders[j][3] != null && !orders[j][3].isEmpty()) {
			// Check if barnbayid is already present in db_orderstring

			isPresent = false;
			n = 0;
			while (db_orders[n][1] != null && !db_orders[n][1].isEmpty()) {

				if (orders[j][3].equals(db_orders[n][1])) {
					isPresent = true;

				}

				n++;

			}

			if (!isPresent) {
				db_orders[n][0] = username;

				db_orders[n][1] = orders[j][3];
				db_orders[n][2] = pickupdate;
				max_length_db_orders = n;
			}

			j++;
		}

		// Getting Max ID:

		sql = "SELECT MAX(IDORDER) FROM " + schema + "ORDERS";

		//
		rs = dbconnect.executeSQL(sql);
		int max_order_id = 1;
		if (rs != null) {
			try {
				rs.next();
				max_order_id = rs.getInt(1) + 1;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Getting Customer User-ID

		sql = "SELECT IDUSER FROM " + schema + "USERD WHERE USERNAME = '"
				+ username + "'";
		rs = dbconnect.executeSQL(sql);
		int user_id = 0;
		if (rs != null) {
			try {
				rs.next();
				user_id = rs.getInt(1);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		int barnbay_user_id;

		// Getting Barnbay User-ID
		int currentorder = 0;
		boolean success = false;
		int i = 0;

		boolean insert_order = true;
		boolean insert_order_products = true;
		boolean update_stock = true;
		while (db_orders[i][1] != null && !db_orders[i][1].isEmpty()) {
			String barnbayidstring = db_orders[i][1];
			sql = "SELECT IDUSER FROM " + schema
					+ "USERD WHERE FK_USERTYPE = 3 AND FK_BARNBAY = "
					+ barnbayidstring + "";
			rs = dbconnect.executeSQL(sql);
			barnbay_user_id = 0;
			if (rs != null) {
				try {
					rs.next();
					barnbay_user_id = rs.getInt(1);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			currentorder = max_order_id + i;
			sql = "INSERT INTO " + schema + "ORDERS VALUES(" + currentorder
					+ ", " + user_id + ", " + barnbay_user_id + ", 0, 0, 0, '"
					+ pickupdate + "')";
			success = dbconnect.executeSQLbool(sql);

			if (!success) {
				insert_order = false;

			}

			// Adding the products to ORDER_HAS_PRODUCT

			int new_stock = 0;
			int k = 0;
			n = 0;
			success = true;
			while (orders[k][3] != null && !orders[k][3].isEmpty()) {
				if (db_orders[i][1].equals(orders[k][3])) {
					sql = "INSERT INTO " + schema + "ORDER_HAS_PRODUCT VALUES("
							+ currentorder + ", " + orders[k][5] + ", "
							+ orders[k][2] + ", " + orders[k][1] + ", 0, 0)";
					success = dbconnect.executeSQLbool(sql);

					if (!success) {
						insert_order_products = false;

					}

					// Subtracting Amount from current stock

					sql = "SELECT CURRENT_STOCK FROM " + schema
							+ "PRODUCT WHERE IDPRODUCT = " + orders[k][5] + "";
					rs = dbconnect.executeSQL(sql);
					if (rs != null) {
						try {
							rs.next();
							int amount = Integer.parseInt(orders[k][1]);
							new_stock = rs.getInt(1) - amount;

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					sql = "UPDATE " + schema + "PRODUCT SET CURRENT_STOCK = "
							+ new_stock + " WHERE IDPRODUCT = " + orders[k][5];

					success = dbconnect.executeSQLbool(sql);

					if (!success) {
						update_stock = false;

					}
				}
				k++;

			}
			i++;

		}

		if (update_stock && insert_order_products && insert_order) {
			response.getWriter().print("{\"set_order\":\"success\"}");
		} else {
			response.getWriter().print("{\"set_order\":\"fail\"}");
		}

		dbconnect.close();

	}

}
