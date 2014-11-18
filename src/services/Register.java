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
import org.json.simple.parser.ParseException;

import database.DB_Connection;
import entities.RandomString;
import entities.User;

public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
		
		StringBuffer jb = new StringBuffer();
		String line;
		JSONObject jsonobject = new JSONObject();
		boolean is_valid = true;
		
		try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { /*report an error*/ }

		
		jsonobject = JSON_Server.http_post_json(jb.toString());
		User receiveduser = JSON_Server.jsonToUser(jsonobject);
		
		// Checking requirements:
		if (receiveduser.email.isEmpty() || receiveduser.firstname.isEmpty() // Pflichtfelder überprüfen
				|| receiveduser.lastname.isEmpty() || receiveduser.passwordhash.isEmpty()
				|| receiveduser.usertype.isEmpty() || receiveduser.title.isEmpty()) {
			is_valid = false;
		}
		
		// Checking if user exists:		
		DB_Connection dbconnect = new DB_Connection(); //
		String sql = "SELECT  * FROM TEST.USERD WHERE USERNAME = '" + receiveduser.username + "'";
		ResultSet rs = dbconnect.executeSQL(sql);
		boolean check_connect = true;
		if(rs!=null){
			try {
				check_connect = rs.next();
				// check_connect = rs.isAfterLast();
				if (check_connect) {
//				rs.next();
//				check_connect = rs.getString(4);
//				if (check_connect.isEmpty()) {
					is_valid = false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		dbconnect.close();
		
		if (is_valid) {
			// Inserting Address data:
			int max_address_id = 0;
			if (!receiveduser.street.isEmpty() && !receiveduser.number.isEmpty() && 
					!receiveduser.zip.isEmpty() && !receiveduser.town.isEmpty()) {
				// Getting highest id from table
				sql = "SELECT MAX(IDADDRESS) FROM TEST.ADDRESS";
				dbconnect = new DB_Connection(); //
				rs = dbconnect.executeSQL(sql);
				max_address_id = 1;
				try {
					rs.next();
					max_address_id = rs.getInt(1) + 1;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				dbconnect.close();
		
				// Creating SQL Statement
				sql = "INSERT INTO TEST.ADDRESS VALUES(" + max_address_id + ", '"
						+ receiveduser.street +"', '"+ receiveduser.number +"', '" 
						+ receiveduser.zip +"', '"+ receiveduser.town +"')";
				dbconnect = new DB_Connection();
				rs = dbconnect.executeSQL(sql);
				dbconnect.close();
				
			   // Address Data is inserted.
			
				// Generating Salt - currently salt not implemented.
				RandomString random_salt = new RandomString(16);
				receiveduser.salt = random_salt.nextString();
				
				// Calculating Usertype and Title
				int usertype_id = 0;
				int title_id = 0;
				
				// Usertype
				sql = "SELECT IDUSERTYPE FROM TEST.USERTYPE WHERE TYPE = '" + receiveduser.usertype + "'";
				dbconnect = new DB_Connection();
				rs = dbconnect.executeSQL(sql);
				try {
					rs.next();
					usertype_id = rs.getInt(1);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dbconnect.close();
				
				// Title
				sql = "SELECT IDTITLE FROM TEST.TITLE WHERE TITLE = '" + receiveduser.title + "'";
				dbconnect = new DB_Connection();
				rs = dbconnect.executeSQL(sql);
				try {
					rs.next();
					title_id = rs.getInt(1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dbconnect.close();
				
				// Getting maximum User ID
				sql = "SELECT MAX(IDUSER) FROM TEST.USERD";
				dbconnect = new DB_Connection(); //
				rs = dbconnect.executeSQL(sql);
				int max_user_id = 1;
				try {
					rs.next();
					max_user_id = rs.getInt(1) + 1;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				// Inserting into Users:
					// Building columns and values accordingly
					// sql = INSERT INTO TEST.USERD ( COLUMNS ) VALUES (VALUES);
				
				String cols = "";
				String vals = "";
				
				if (!receiveduser.firstname.isEmpty()) {
					cols = cols + "FIRST_NAME";
					vals = vals + "'" + receiveduser.firstname +"'";
				}
				
				if (!receiveduser.lastname.isEmpty()) {
					cols = cols + ", LAST_NAME";
					vals = vals + ", '" + receiveduser.lastname +"'";
				}
				
				if (!receiveduser.username.isEmpty()) {
					cols = cols + ", USERNAME";
					vals = vals + ", '" + receiveduser.username +"'";
				}
				
				if (usertype_id != 0) {
					cols = cols + ", FK_USERTYPE";
					vals = vals + ", " + usertype_id +"";
				}
				
				if (title_id != 0) {
					cols = cols + ", FK_TITLE";
					vals = vals + ", " + title_id +"";
				}
				
				if (max_address_id != 0) {
					cols = cols + ", FK_ADDRESS";
					vals = vals + ", " + max_address_id +"";
				}
				
				if (!receiveduser.email.isEmpty()) {
					cols = cols + ", EMAIL_ADDRESS";
					vals = vals + ", '" + receiveduser.email +"'";
				}
				
				if (!receiveduser.passwordhash.isEmpty()) {
					cols = cols + ", PASSWORDHASH";
					vals = vals + ", '" + receiveduser.passwordhash +"'";
				}
				
				if (!receiveduser.salt.isEmpty()) {
					cols = cols + ", SALT";
					vals = vals + ", '" + receiveduser.salt +"'";
				}
				
				if (max_user_id != 0) {
					cols = cols + ", IDUSER";
					vals = vals + ", " + max_user_id +"";
				}
				
				
				
				sql = "INSERT INTO TEST.USERD ( " + cols + " ) VALUES ( " + vals + ")";
				dbconnect = new DB_Connection();
				check_connect = dbconnect.executeSQLbool(sql);
				

				
				dbconnect.close();
			}
			
			if (check_connect) {
				response.getWriter().print("{\"register\":\"success\"}");
			} else if (!is_valid) {
				response.getWriter().print("{\"register\":\"fail\",\"reason\":\"exists\"");
			} else {
				response.getWriter().print("{\"register\":\"fail\",\"reason\":\"sqlerror\"}");
			}
 		}
		

	}

}