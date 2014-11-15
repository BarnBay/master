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
		// TODO Auto-generated method stub
		
		


		
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
		DB_Connection dbconnect = new DB_Connection();
		ResultSet resultset = dbconnect.executeSQL("SELECT User.first_name, User.last_name, title, User.email_address, User.username, Usertype.type, User.passwordhash, User.salt FROM User, Usertype, Address, Title WHERE User.fk_Usertype = Usertype.idUsertype AND Address.idAddress = User.fk_Address AND Title.idTitle = User.fk_Title AND username = '" + receiveduser.username + "'");
		int length = 0;
		
		// Transforming Data to User-Object
		try {
			resultset.last();
			length = resultset.getRow();
			resultset.first(); // back to first datapoint
			if (length == 1) {
				db_user.firstname = resultset.getString("User.first_name");
				db_user.lastname = resultset.getString("User.last_name");
				db_user.title = resultset.getString("Title.title");
				db_user.email = resultset.getString("User.email_address");
				db_user.username = resultset.getString("User.username");
				db_user.usertype = resultset.getString("Usertype.type");
				db_user.passwordhash = resultset.getString("User.passwordhash");
				db_user.salt = resultset.getString("User.salt");
				db_user.session = resultset.getString("User.session");
				db_user.street = resultset.getString("Address.street");
				db_user.number = resultset.getString("Address.number");
				db_user.zip = resultset.getString("Address.zip_code");
				db_user.town = resultset.getString("Address.city");
				
			} else {
				// More than 1 user found or no user found! Aborting!
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
		//response.getWriter().println(obj2.toString());
		
	}

}
	
	/*
	 * 	public String firstname;
	public String lastname;
	public String title;      // Mr. or Mrs. (Dr.)
	public String email;
	public String username;   // currently equals email
	public String usertype;   // Examples: Farmer, Customer, Barnbay-Pickup, Barnbay-Financials
	public String passwordhash;
	public String salt;
	public String session;
	public String street;
	public String number;
	public String zip;
	public String town;
	 */




/*

{{"user":{"title":"Mr.","firstname":"Peter":{"GlossEntry":{"SortAs":"SGML","GlossDef":{"GlossSeeAlso":["GML","XML"],"para":"A meta-markup language, used to create markup languages such as DocBook."},"GlossSee":"markup","GlossTerm":"Standard Generalized Markup Language","ID":"SGML","Acronym":"SGML","Abbrev":"ISO 8879:1986"}},"title":"S"}}} 

*/