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

import json.JSON_Server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import database.DB_Connection;
import entities.Category;
import entities.User;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StringBuffer jb = new StringBuffer();
		String line;
		JSONObject jsonobject = new JSONObject();
		JSONParser jsonparser = new JSONParser();
		User db_user = new User();
		
		try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { /*report an error*/ }

		
		jsonobject = JSON_Server.http_post_json(jb.toString());
		User receiveduser = JSON_Server.jsonToUser(jsonobject);
		
		// Getting Data from Database:	
		DB_Connection dbconnect = new DB_Connection(); //
		String sql = "SELECT  * FROM TEST.USERD, TEST.USERTYPE, TEST.ADDRESS, TEST.TITLE WHERE FK_USERTYPE = IDUSERTYPE AND IDADDRESS = FK_ADDRESS AND IDTITLE = FK_TITLE AND USERNAME = '" + receiveduser.username + "'";
		ResultSet rs = dbconnect.executeSQL(sql);
		int length = 0;
		
		// Transforming Data to User-Object
		
		if(rs!=null){

			try {
				while(rs.next()){
					
					db_user.id = rs.getInt(1);
					db_user.firstname = rs.getString(2);
					db_user.lastname = rs.getString(3);
					db_user.username = rs.getString(4);
					db_user.email = rs.getString(10);
					db_user.passwordhash = rs.getString(11);
					db_user.usertype = rs.getString(15);
					db_user.street = rs.getString(17);
					db_user.number = rs.getString(18);
					db_user.zip = rs.getString(19);
					db_user.town = rs.getString(20);
					db_user.title = rs.getString(22);

				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dbconnect.close();
		
		
		
		
		if ( receiveduser.passwordhash.equals(db_user.passwordhash) ) {
			db_user.generateSession();
			JSONObject user_return = JSON_Server.wrap_JSON(db_user);
			String returnstring = "{\"login\":\"success\",\"user\":" + user_return.toString() + "}";
			JSONObject returnjson = JSON_Server.http_post_json(returnstring);
			response.getWriter().print(returnjson.toString());
			
			String sql2 = "UPDATE TEST.USERD SET SESSION = '" + db_user.session + "' WHERE USERNAME = '" + db_user.username + "'";
			DB_Connection dbconnect2 = new DB_Connection();
			ResultSet rs2 = dbconnect2.executeSQL(sql2);

			dbconnect2.close();
		} else {
			response.getWriter().print("{\"login\":\"fail\"}");
		}
		

	}

}
	
