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
		DB_Connection dbconnect = new DB_Connection(); //
		ResultSet rs = dbconnect.executeSQL("SELECT  * FROM TEST.USERD, TEST.USERTYPE, TEST.ADDRESS, TEST.TITLE WHERE FK_USERTYPE = IDUSERTYPE AND IDADDRESS = FK_ADDRESS AND IDTITLE = FK_TITLE AND USERNAME = '" + receiveduser.username + "'");
		//ResultSet rs = dbconnect.executeSQL("SELECT  * FROM TEST.USERD"); //, TEST.USERTYPE, TEST.ADDRESS, TEST.TITLE WHERE FK_USERTYPE = IDUSERTYPE AND idAddress = fk_Address AND idTitle = fk_Title AND username = '" + receiveduser.username + "'");
		int length = 0;
		
		// Transforming Data to User-Object
		
		
		
		if(rs!=null){

			try {
				while(rs.next()){
//					for (int i=1; i<50; i++){
//						response.getWriter().println(i + ": " + rs.getString(i));
//					} 
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
	
		response.getWriter().println("receiveduser.passwordhash : " + receiveduser.passwordhash);
		response.getWriter().println("db_user.passwordhash : " + db_user.passwordhash);

		
		if ( receiveduser.passwordhash.equals(db_user.passwordhash) ) {
			response.getWriter().print("{\"login\":\"success\"}");
		} else {
			response.getWriter().print("{\"login\":\"fail\"}");
		}
		
		//response.getWriter().print(mystring.toString());
		
			
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