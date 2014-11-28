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
 * Servlet implementation class GetAllProducts
 */
public class GetAllSubCategories extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllSubCategories() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DB_Connection db = new DB_Connection();
		
		ResultSet rs = db.executeSQL("SELECT * FROM TEST.CATEGORY WHERE FK_SUPER_CATEGORY!=0");
		if(rs!=null){
			ArrayList<Category> a = new ArrayList<Category>();
			try {
				while(rs.next()){
					Category c = new Category();
					c.idcategory = rs.getInt(1);
					c.name = rs.getString(2);
					c.super_category = rs.getInt(3);
					c.category_description = rs.getString(4);
					
					ResultSet rs2 = db.executeSQL("SELECT MIN(PRICE) FROM TEST.PRODUCT WHERE FK_CATEGORY=" + c.idcategory);
					if(rs2!=null && rs2.next()){
						c.price = rs2.getInt(1);
					}
					
					a.add(c);
				}
				String json = JSON_Server.categoryArrayToJson(a);
				response.getWriter().println(json);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		db.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
