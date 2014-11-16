package test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DB_Connection;

/**
 * Servlet implementation class Initialize
 */
public class Initialize extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Initialize() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{

		DB_Connection db = new DB_Connection();
				
		db.executeSQL("CREATE SCHEMA TEST");
		db.executeSQL("CREATE TABLE TEST.PRODUCT ( IDPRODUCT INTEGER, PRODUCT_DESCRIPTION VARCHAR(100), FK_CATEGORY INTEGER, FK_USER INTEGER, PRICE DECIMAL(2,0), FK_OVERALL_RATING INTEGER, CURRENT_STOCK INTEGER, AVAILABLE INTEGER, SETUP_DATE DATE, DEACTIVATION_DATE DATE, FK_CURRENCY INTEGER, FK_AMOUNT_TYPE INTEGER)");
		
		db.executeSQL("CREATE TABLE TEST.CATEGORY ( IDCATEGORY INTEGER NOT NULL, NAME VARCHAR(100) NOT NULL, FK_SUPER_CATEGORY INTEGER, CATEGORY_DESCRIPTION VARCHAR(100))");
		
		db.executeSQL("CREATE TABLE TEST.USERD(IDUSER INTEGER NOT NULL , FIRST_NAME VARCHAR(45) NOT NULL , LAST_NAME VARCHAR(45) NOT NULL , USERNAME VARCHAR(45) NOT NULL , FK_USERTYPE INTEGER NOT NULL , FK_TITLE INTEGER NOT NULL , FK_ADDRESS INTEGER, FK_FARM INTEGER, FK_BARNBAY INTEGER, EMAIL_ADDRESS VARCHAR(100) NOT NULL , PASSWORDHASH VARCHAR(100), SALT VARCHAR(18), SESSION VARCHAR(25))");
		
		db.executeSQL("CREATE TABLE TEST.USERTYPE (IDUSERTYPE INTEGER, TYPE VARCHAR(50))");
		
		db.executeSQL("CREATE TABLE TEST.TITLE (IDTITLE INTEGER NOT NULL , TITLE VARCHAR(40) NOT NULL)");
		
		db.executeSQL("CREATE TABLE TEST.FARM (IDFARM INTEGER NOT NULL , NAME VARCHAR(100) NOT NULL , PICTURE VARCHAR(256), DESCRIPTION VARCHAR(100), OPENING_HOURS VARCHAR(50), FK_OVERALL_RATING INTEGER)");
		
		db.executeSQL("CREATE TABLE TEST.ADDRESS (IDADDRESS INTEGER NOT NULL , STREET VARCHAR(100), NUMBER VARCHAR(5), ZIP_CODE VARCHAR(10), CITY VARCHAR(100))");
		
		
		
		db.executeSQL("INSERT INTO TEST.ADDRESS VALUES(1, 'Waldstrasse', '23', '54329', 'KONZ')");
		db.executeSQL("INSERT INTO TEST.ADDRESS VALUES(2, 'Seckenheimerstr.', '5', '68163', 'Mannheim')");
		
		db.executeSQL("INSERT INTO TEST.FARM(IDFARM, NAME, PICTURE, DESCRIPTION, OPENING_HOURS) VALUES"
				+"(1, 'Seckenheimer Obsthof', '1', 'Wir haben Obstsorten aller Art und stellen alles biologisch her. Kommen Sie vorbei!', 'Montag-Sonntag 9:00-18:00 Uhr')");
		
		db.executeSQL("INSERT INTO TEST.FARM(IDFARM, NAME, PICTURE, DESCRIPTION, OPENING_HOURS) VALUES"
				+"(2, 'Super Hof', '2', 'Unsere Produkte koennen Sie sich schmecken lassen', 'Montag-Sonntag 9:00-18:00 Uhr')");
		
		db.executeSQL("INSERT INTO TEST.USERTYPE VALUES(1, 'FARMER')");
		
		db.executeSQL("INSERT INTO TEST.TITLE VALUES(1, 'Herr')");
		
		db.executeSQL("INSERT INTO TEST.USERD (IDUSER, FIRST_NAME, LAST_NAME, USERNAME, FK_USERTYPE, FK_TITLE, FK_ADDRESS, FK_FARM, EMAIL_ADDRESS, PASSWORDHASH)"
				+"VALUES (1, 'John', 'Bauer', 'johnb', 1, 1, 1, 2, 'johnb@farms.com', 'stuhl')");
		
		db.executeSQL("INSERT INTO TEST.USERD (IDUSER, FIRST_NAME, LAST_NAME, USERNAME, FK_USERTYPE, FK_TITLE, FK_ADDRESS, FK_FARM, EMAIL_ADDRESS, PASSWORDHASH)"
				+"VALUES (2, 'Peter', 'Meier', 'peterm', 1, 1, 2, 1, 'peterm@vollbio.org', 'tisch')");
		
		
		db.executeSQL("INSERT INTO TEST.PRODUCT(IDPRODUCT, PRODUCT_DESCRIPTION, FK_CATEGORY,"
           		 + "FK_USER, PRICE, CURRENT_STOCK, FK_CURRENCY, FK_AMOUNT_TYPE) VALUES(0,"
            	 + "'Sehr saftige Apfelsorte, die Sie lieben werden.', 5, 2, 3, 100, 0, 0)");
		
		db.executeSQL("INSERT INTO TEST.CATEGORY VALUES(1, 'Obst', 0,'')");
		db.executeSQL("INSERT INTO TEST.CATEGORY VALUES(2, 'Gemüse', 0, '')");
		db.executeSQL("INSERT INTO TEST.CATEGORY VALUES(3, 'Fleisch', 0,'')");
		db.executeSQL("INSERT INTO TEST.CATEGORY VALUES(4, 'Grannys Apfel', 1,'Grannys Apfel ist sehr lieblich.')");
		db.executeSQL("INSERT INTO TEST.CATEGORY VALUES(5, 'Pink Ladys Apfel', 1,'Pink Lady ist eine sehr knackige Apfelsorte')");
		db.executeSQL("INSERT INTO TEST.CATEGORY VALUES(6, 'Möhre flotte Hilde', 2,'Schön in der Farbe!')");
		db.executeSQL("INSERT INTO TEST.CATEGORY VALUES(7, 'Tomate Red Bull', 2,'Saftig, saftiger am saftigsten!')");
		
		db.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
