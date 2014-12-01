package services;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import json.JSON_Server;

import database.DB_Connection;
import entities.Category;

/**
 * Servlet implementation class GetAllSubCategories,
 * which offers a json-service to the frontend
 * @author Anne
 * 
 */
public class GetAllSubCategories extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Contructor
     * @see HttpServlet#HttpServlet()
     */
    public GetAllSubCategories() {
        super();
    }

	/**
	 * Method which is executed when the servlet receives an http get request
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DB_Connection db = new DB_Connection();
		String schema = DB_Connection.getSchemaName(request.getRequestURL().toString());
		
		ResultSet rs = db.executeSQL("SELECT * FROM " + schema + "CATEGORY WHERE FK_SUPER_CATEGORY!=0");
		if(rs!=null){
			ArrayList<Category> a = new ArrayList<Category>();
			try {
				while(rs.next()){
					Category c = new Category();
					c.idcategory = rs.getInt(1);
					c.name = rs.getString(2);
					c.super_category = rs.getInt(3);
					c.category_description = rs.getString(4);
					
					ResultSet rs2 = db.executeSQL("SELECT MIN(PRICE) FROM " + schema + "PRODUCT WHERE FK_CATEGORY=" + c.idcategory);
					if(rs2!=null && rs2.next()){
						c.price = rs2.getInt(1);
					}
					
					a.add(c);
				}
				String json = JSON_Server.objectToJson(a);
				response.getWriter().println(json);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		db.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
