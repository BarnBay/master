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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {		
		DB_Connection db = new DB_Connection();

		String schema = DB_Connection.getSchemaName(request.getRequestURL().toString());
		
		if(schema ==""){

			//db.executeSQL("CREATE SCHEMA NEO_39ENP71K0F1EDD9OYBD7IWIME");

			db.executeSQL("CREATE TABLE PRODUCT ( IDPRODUCT INT, PRODUCT_DESCRIPTION VARCHAR(100), FK_CATEGORY INT, FK_USER INT, PRICE DECIMAL(2,0), FK_OVERALL_RATING INT, CURRENT_STOCK INT, AVAILABLE INT, SETUP_DATE DATE, DEACTIVATION_DATE DATE, FK_CURRENCY INT, FK_AMOUNT_TYPE INT)");
			
			db.executeSQL("CREATE TABLE CATEGORY ( IDCATEGORY INT NOT NULL, NAME VARCHAR(100) NOT NULL, FK_SUPER_CATEGORY INT, CATEGORY_DESCRIPTION VARCHAR(100))");
			
			db.executeSQL("CREATE TABLE USERD (IDUSER INT NOT NULL , FIRST_NAME VARCHAR(45) NOT NULL , LAST_NAME VARCHAR(45) NOT NULL , USERNAME VARCHAR(45) NOT NULL , FK_USERTYPE INT NOT NULL , FK_TITLE INT NOT NULL , FK_ADDRESS INT, FK_FARM INT, FK_BARNBAY INT, EMAIL_ADDRESS VARCHAR(100) NOT NULL , PASSWORDHASH VARCHAR(100), SALT VARCHAR(18), SESSION VARCHAR(25))");
			
			db.executeSQL("CREATE TABLE USERTYPE (IDUSERTYPE INT, TYPE VARCHAR(50))");
			
			db.executeSQL("CREATE TABLE TITLE (IDTITLE INT NOT NULL , TITLE VARCHAR(40) NOT NULL)");
			
			db.executeSQL("CREATE TABLE FARM (IDFARM INT NOT NULL , NAME VARCHAR(100) NOT NULL , PICTURE VARCHAR(256), DESCRIPTION VARCHAR(100), OPENING_HOURS VARCHAR(50), FK_OVERALL_RATING INT)");
			
			db.executeSQL("CREATE TABLE ADDRESS (IDADDRESS INT NOT NULL , STREET VARCHAR(100), NUMBER VARCHAR(5), ZIP_CODE VARCHAR(10), CITY VARCHAR(100))");
			
			db.executeSQL("CREATE TABLE BARNBAY (IDBARNBAY INT NOT NULL , NAME VARCHAR(50) NOT NULL , PICTURE VARCHAR(256), DESCRIPTION VARCHAR(256), OPENING_HOURS VARCHAR(100), FK_ADDRESS INT NOT NULL)");
			
			// Added order support
			db.executeSQL("CREATE TABLE ORDERS (IDORDER INTEGER CS_INT NOT NULL , FK_USERS_CUSTOMER INTEGER CS_INT NOT NULL , FK_USERS_PICKUP INTEGER CS_INT NOT NULL , FK_USERS_FINANCIALS INTEGER CS_INT NOT NULL , FK_ORDER_STATUS INTEGER CS_INT NOT NULL , FK_PACKAGING_TYPE INTEGER CS_INT NOT NULL , PICKUP_DATE DAYDATE CS_DAYDATE, PRIMARY KEY (IDORDER))");
			db.executeSQL("CREATE  TABLE ORDER_HAS_PRODUCT (FK_ORDER INTEGER CS_INT NOT NULL , FK_PRODUCT INTEGER CS_INT NOT NULL , CURRENT_PRICE DECIMAL(2,0) CS_FIXED NOT NULL , AMOUNT INTEGER CS_INT NOT NULL , FK_CURRENCY INTEGER CS_INT NOT NULL , FK_AMOUNT_TYPE INTEGER CS_INT NOT NULL , PRIMARY KEY (FK_ORDER, FK_PRODUCT))");
					
			try {
				response.getWriter().println("Initialization of database on hana cloud platform done.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} else {
			 
			
			db.executeSQL("CREATE SCHEMA " + schema.replace(schema.substring(schema.length()-1), ""));
			
			db.executeSQL("CREATE TABLE " + schema + "PRODUCT ( IDPRODUCT INTEGER, PRODUCT_DESCRIPTION VARCHAR(100), FK_CATEGORY INTEGER, FK_USER INTEGER, PRICE DECIMAL(2,0), FK_OVERALL_RATING INTEGER, CURRENT_STOCK INTEGER, AVAILABLE INTEGER, SETUP_DATE DATE, DEACTIVATION_DATE DATE, FK_CURRENCY INTEGER, FK_AMOUNT_TYPE INTEGER)");
			
			db.executeSQL("CREATE TABLE " + schema + "CATEGORY ( IDCATEGORY INTEGER NOT NULL, NAME VARCHAR(100) NOT NULL, FK_SUPER_CATEGORY INTEGER, CATEGORY_DESCRIPTION VARCHAR(100))");
			
			db.executeSQL("CREATE TABLE " + schema + "USERD(IDUSER INTEGER NOT NULL , FIRST_NAME VARCHAR(45) NOT NULL , LAST_NAME VARCHAR(45) NOT NULL , USERNAME VARCHAR(45) NOT NULL , FK_USERTYPE INTEGER NOT NULL , FK_TITLE INTEGER NOT NULL , FK_ADDRESS INTEGER, FK_FARM INTEGER, FK_BARNBAY INTEGER, EMAIL_ADDRESS VARCHAR(100) NOT NULL , PASSWORDHASH VARCHAR(100), SALT VARCHAR(18), SESSION VARCHAR(25))");
			
			db.executeSQL("CREATE TABLE " + schema + "USERTYPE (IDUSERTYPE INTEGER, TYPE VARCHAR(50))");
			
			db.executeSQL("CREATE TABLE " + schema + "TITLE (IDTITLE INTEGER NOT NULL , TITLE VARCHAR(40) NOT NULL)");
			
			db.executeSQL("CREATE TABLE " + schema + "FARM (IDFARM INTEGER NOT NULL , NAME VARCHAR(100) NOT NULL , PICTURE VARCHAR(256), DESCRIPTION VARCHAR(100), OPENING_HOURS VARCHAR(50), FK_OVERALL_RATING INTEGER)");
			
			db.executeSQL("CREATE TABLE " + schema + "ADDRESS (IDADDRESS INTEGER NOT NULL , STREET VARCHAR(100), NUMBER VARCHAR(5), ZIP_CODE VARCHAR(10), CITY VARCHAR(100))");
			
			db.executeSQL("CREATE TABLE " + schema + "BARNBAY (IDBARNBAY INTEGER NOT NULL , NAME VARCHAR(50) NOT NULL , PICTURE VARCHAR(256), DESCRIPTION VARCHAR(256), OPENING_HOURS VARCHAR(100), FK_ADDRESS INTEGER NOT NULL)");
			
			// Added order support
			db.executeSQL("CREATE TABLE " + schema + "ORDERS (IDORDER INTEGER NOT NULL , FK_USERS_CUSTOMER INTEGER NOT NULL , FK_USERS_PICKUP INTEGER NOT NULL , FK_USERS_FINANCIALS INTEGER NOT NULL , FK_ORDER_STATUS INTEGER NOT NULL , FK_PACKAGING_TYPE INTEGER NOT NULL , PICKUP_DATE VARCHAR(20) NOT NULL )");
			db.executeSQL("CREATE TABLE " + schema + "ORDER_HAS_PRODUCT (FK_ORDER INTEGER NOT NULL , FK_PRODUCT INTEGER NOT NULL , CURRENT_PRICE DECIMAL(2,0) NOT NULL , AMOUNT INTEGER NOT NULL , FK_CURRENCY INTEGER NOT NULL , FK_AMOUNT_TYPE INTEGER NOT NULL)");
		
			try {
				response.getWriter().println("Initialization of database on local derby done.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
			db.executeSQL("INSERT INTO " + schema + "BARNBAY(IDBARNBAY, NAME, DESCRIPTION, OPENING_HOURS, FK_ADDRESS) VALUES(1, 'Barnbay@Castle', 'Our original Barnbay at the castle in Mannheim!', 'Monday-Sunday 7:00 am-10:00 pm', 4)");
			db.executeSQL("INSERT INTO " + schema + "BARNBAY(IDBARNBAY, NAME, DESCRIPTION, OPENING_HOURS, FK_ADDRESS) VALUES(2, 'Barnbay@HBF Mannheim', 'Next Stop: Barnbay!', 'Monday-Sunday 6:00 am-10:00 pm', 5)");
			db.executeSQL("INSERT INTO " + schema + "BARNBAY(IDBARNBAY, NAME, DESCRIPTION, OPENING_HOURS, FK_ADDRESS) VALUES(3, 'Barnbay@SAP', 'Perfect for SAP employees!', 'Monday-Sunday 8:00 am-10:00 pm', 6)");
			
			db.executeSQL("INSERT INTO " + schema + "ADDRESS VALUES(1, 'In den Boellenruthen', '15', '68623', 'Lampertheim')");
			db.executeSQL("INSERT INTO " + schema + "ADDRESS VALUES(2, 'Seckenheimerstr.', '5', '68163', 'Mannheim')");
			db.executeSQL("INSERT INTO " + schema + "ADDRESS VALUES(4, 'L1', '1', '68161', 'Mannheim')");
			db.executeSQL("INSERT INTO " + schema + "ADDRESS VALUES(5, 'Tattersallstr.', '2', '68165', 'Mannheim')");
			db.executeSQL("INSERT INTO " + schema + "ADDRESS VALUES(6, 'Dietmar-Hopp-Allee', '20', '69190', 'Walldorf')");
			
			db.executeSQL("INSERT INTO " + schema + "FARM(IDFARM, NAME, PICTURE, DESCRIPTION, OPENING_HOURS) VALUES"
					+"(1, 'Seckenheimer Obsthof', '1', 'We have fruits of all kinds and grow them organically. Taste and smile!', 'Monday-Sunday 9:00 am-6:00 pm')");
			
			db.executeSQL("INSERT INTO " + schema + "FARM(IDFARM, NAME, PICTURE, DESCRIPTION, OPENING_HOURS) VALUES"
					+"(2, 'Super Hof', '2', 'Come and try our products!', 'Monday-Sunday 9:00 am-6:00 pm')");
			
			db.executeSQL("INSERT INTO " + schema + "USERTYPE VALUES(1, 'FARMER')");
			db.executeSQL("INSERT INTO " + schema + "USERTYPE VALUES(2, 'CUSTOMER')");
			db.executeSQL("INSERT INTO " + schema + "USERTYPE VALUES(3, 'PICKUPLOCATION')");
			db.executeSQL("INSERT INTO " + schema + "USERTYPE VALUES(4, 'BARNBAYFINANCIALS')");
			
			db.executeSQL("INSERT INTO " + schema + "TITLE VALUES(1, 'Herr')");
			db.executeSQL("INSERT INTO " + schema + "TITLE VALUES(2, 'Frau')");
			
			db.executeSQL("INSERT INTO " + schema + "USERD (IDUSER, FIRST_NAME, LAST_NAME, USERNAME, FK_USERTYPE, FK_TITLE, FK_ADDRESS, FK_FARM, EMAIL_ADDRESS, PASSWORDHASH, FK_BARNBAY)"
					+"VALUES (1, 'John', 'Bauer', 'johnb', 1, 1, 1, 2, 'johnb@farms.com', 'tisch', 1)");
			
			db.executeSQL("INSERT INTO " + schema + "USERD (IDUSER, FIRST_NAME, LAST_NAME, USERNAME, FK_USERTYPE, FK_TITLE, FK_ADDRESS, FK_FARM, EMAIL_ADDRESS, PASSWORDHASH, FK_BARNBAY)"
					+"VALUES (2, 'Peter', 'Meier', 'peterm', 1, 1, 2, 1, 'peterm@vollbio.org', 'tisch', 2)");
			
			db.executeSQL("INSERT INTO " + schema + "USERD (IDUSER, FIRST_NAME, LAST_NAME, USERNAME, FK_USERTYPE, FK_TITLE, FK_ADDRESS, FK_FARM, EMAIL_ADDRESS, PASSWORDHASH, FK_BARNBAY)"
					+"VALUES (3, 'Christian', 'Mueller', 'cmueller', 2, 1, 2, 0, 'cmueller@vollbio.org', 'tisch', 3)");
			
			db.executeSQL("INSERT INTO " + schema + "USERD (IDUSER, FIRST_NAME, LAST_NAME, USERNAME, FK_USERTYPE, FK_TITLE, FK_ADDRESS, FK_FARM, EMAIL_ADDRESS, PASSWORDHASH, FK_BARNBAY)"
					+"VALUES (4, '', '', 'BBMaschloss', 3, 1, 2, 0, 'bbmaschloss@vollbio.org', 'tisch', 1)");
			
			db.executeSQL("INSERT INTO " + schema + "USERD (IDUSER, FIRST_NAME, LAST_NAME, USERNAME, FK_USERTYPE, FK_TITLE, FK_ADDRESS, FK_FARM, EMAIL_ADDRESS, PASSWORDHASH, FK_BARNBAY)"
					+"VALUES (5, '', '', 'BBMaschloss', 4, 1, 2, 0, 'bbmaschloss@vollbio.org', 'tisch', 0)");
			

			db.executeSQL("INSERT INTO " + schema + "USERD (IDUSER, FIRST_NAME, LAST_NAME, USERNAME, FK_USERTYPE, FK_TITLE, FK_ADDRESS, FK_FARM, EMAIL_ADDRESS, PASSWORDHASH, FK_BARNBAY)"
					+"VALUES (6, 'Erika', 'Giebel', 'egiebel', 2, 2, 2, 0, 'cmueller@vollbio.org', 'tisch', 0)");
			
			db.executeSQL("INSERT INTO " + schema + "USERD (IDUSER, FIRST_NAME, LAST_NAME, USERNAME, FK_USERTYPE, FK_TITLE, FK_ADDRESS, FK_FARM, EMAIL_ADDRESS, PASSWORDHASH, FK_BARNBAY)"
					+"VALUES (7, '', '', 'BBSAP', 4, 1, 2, 0, 'bbsap@vollbio.org', 'tisch', 3)");
			
			/**
			 * Initialize Grannys Apfel
			 */
			db.executeSQL("INSERT INTO " + schema + "PRODUCT(IDPRODUCT, PRODUCT_DESCRIPTION, FK_CATEGORY,"
	          		 + "FK_USER, PRICE, CURRENT_STOCK, FK_CURRENCY, FK_AMOUNT_TYPE) VALUES(1,"
	           	 + "'You will love this Product.', 4, 1, 2, 100, 0, 0)");
			
			db.executeSQL("INSERT INTO " + schema + "PRODUCT(IDPRODUCT, PRODUCT_DESCRIPTION, FK_CATEGORY,"
	         		 + "FK_USER, PRICE, CURRENT_STOCK, FK_CURRENCY, FK_AMOUNT_TYPE) VALUES(2,"
	          	 + "'Best Product in my stock.', 4, 2, 1, 20, 0, 0)");
			
			
		
			/**
			 * Initialize Apple Cripps Pink
			 */
			db.executeSQL("INSERT INTO " + schema + "PRODUCT(IDPRODUCT, PRODUCT_DESCRIPTION, FK_CATEGORY,"
	          		 + "FK_USER, PRICE, CURRENT_STOCK, FK_CURRENCY, FK_AMOUNT_TYPE) VALUES(3,"
	           	 + "'You will love this Product.', 5, 1, 2, 100, 0, 0)");
			
			db.executeSQL("INSERT INTO " + schema + "PRODUCT(IDPRODUCT, PRODUCT_DESCRIPTION, FK_CATEGORY,"
	         		 + "FK_USER, PRICE, CURRENT_STOCK, FK_CURRENCY, FK_AMOUNT_TYPE) VALUES(4,"
	          	 + "'Best Product in my stock.', 5, 2, 3, 20, 0, 0)");
			
			
			/**
			 * Initialize carrot
			 */
			db.executeSQL("INSERT INTO " + schema + "PRODUCT(IDPRODUCT, PRODUCT_DESCRIPTION, FK_CATEGORY,"
	           		 + "FK_USER, PRICE, CURRENT_STOCK, FK_CURRENCY, FK_AMOUNT_TYPE) VALUES(5,"
	            	 + "'You will love this Product.', 6, 1, 3, 100, 0, 0)");
			
			db.executeSQL("INSERT INTO " + schema + "PRODUCT(IDPRODUCT, PRODUCT_DESCRIPTION, FK_CATEGORY,"
	          		 + "FK_USER, PRICE, CURRENT_STOCK, FK_CURRENCY, FK_AMOUNT_TYPE) VALUES(6,"
	           	 + "'Best Product in my stock.', 6, 2, 4, 20, 0, 0)");
			
			/**
			 * Initialize tomato
			 */
			db.executeSQL("INSERT INTO " + schema + "PRODUCT(IDPRODUCT, PRODUCT_DESCRIPTION, FK_CATEGORY,"
	          		 + "FK_USER, PRICE, CURRENT_STOCK, FK_CURRENCY, FK_AMOUNT_TYPE) VALUES(7,"
	           	 + "'You will love this Product.', 7, 1, 4, 100, 0, 0)");
			
			db.executeSQL("INSERT INTO " + schema + "PRODUCT(IDPRODUCT, PRODUCT_DESCRIPTION, FK_CATEGORY,"
	         		 + "FK_USER, PRICE, CURRENT_STOCK, FK_CURRENCY, FK_AMOUNT_TYPE) VALUES(8,"
	          	 + "'Best Product in my stock.', 7, 2, 5, 20, 0, 0)");
			
			/**
			 * Initialize demo orders
			 */
			
			// Order 1: 5 Granny Smith apples, 3 Carrots Milan, 10 Tomatoes
			
			// Create Order:
			db.executeSQL("INSERT INTO " + schema + "ORDERS (IDORDER , FK_USERS_CUSTOMER , FK_USERS_PICKUP , FK_USERS_FINANCIALS , FK_ORDER_STATUS , FK_PACKAGING_TYPE , PICKUP_DATE )" +
					" VALUES(1, 3, 4, 5, 0, 0 , '10.12.2014')");

			// Granny Smith apples:
			db.executeSQL("INSERT INTO " + schema + "ORDER_HAS_PRODUCT(FK_ORDER, FK_PRODUCT, CURRENT_PRICE, AMOUNT, FK_CURRENCY, FK_AMOUNT_TYPE)" + 
						" VALUES(1, 1, 2, 5, 0, 0)");
			// Carrots:
			db.executeSQL("INSERT INTO " + schema + "ORDER_HAS_PRODUCT(FK_ORDER, FK_PRODUCT, CURRENT_PRICE, AMOUNT, FK_CURRENCY, FK_AMOUNT_TYPE)" + 
					" VALUES(1, 5, 3, 3, 0, 0)");			
			// Tomatoes:
			db.executeSQL("INSERT INTO " + schema + "ORDER_HAS_PRODUCT(FK_ORDER, FK_PRODUCT, CURRENT_PRICE, AMOUNT, FK_CURRENCY, FK_AMOUNT_TYPE)" + 
					" VALUES(1, 8, 5, 10, 0, 0)");			
			
			// Order 2: 7 Cripps Pink apples, 3 Carrots Milan, 7 Tomatoes

			// Create Order:
			db.executeSQL("INSERT INTO " + schema + "ORDERS (IDORDER , FK_USERS_CUSTOMER , FK_USERS_PICKUP , FK_USERS_FINANCIALS , FK_ORDER_STATUS , FK_PACKAGING_TYPE , PICKUP_DATE)" +
					" VALUES(2, 6, 4, 5, 0, 0, '10.12.2014')");
			
			// Cripps Pink apples:
			db.executeSQL("INSERT INTO " + schema + "ORDER_HAS_PRODUCT(FK_ORDER, FK_PRODUCT, CURRENT_PRICE, AMOUNT, FK_CURRENCY, FK_AMOUNT_TYPE)" + 
						" VALUES(2, 4, 4, 7, 0, 0)");
			// Carrots:
			db.executeSQL("INSERT INTO " + schema + "ORDER_HAS_PRODUCT(FK_ORDER, FK_PRODUCT, CURRENT_PRICE, AMOUNT, FK_CURRENCY, FK_AMOUNT_TYPE)" + 
					" VALUES(2, 6, 3, 4, 0, 0)");			
			// Tomatoes:
			db.executeSQL("INSERT INTO " + schema + "ORDER_HAS_PRODUCT(FK_ORDER, FK_PRODUCT, CURRENT_PRICE, AMOUNT, FK_CURRENCY, FK_AMOUNT_TYPE)" + 
					" VALUES(2, 8, 7, 9, 0, 0)");			
			
			
			// Order 3: 7 Cripps Pink apples, 3 Carrots Milan, 7 Tomatoes

			// Create Order:
			db.executeSQL("INSERT INTO " + schema + "ORDERS (IDORDER , FK_USERS_CUSTOMER , FK_USERS_PICKUP , FK_USERS_FINANCIALS , FK_ORDER_STATUS , FK_PACKAGING_TYPE , PICKUP_DATE)" +
					" VALUES(3, 6, 7, 1, 0, 0, '10.12.2014')");
			
			// Cripps Pink apples:
			db.executeSQL("INSERT INTO " + schema + "ORDER_HAS_PRODUCT(FK_ORDER, FK_PRODUCT, CURRENT_PRICE, AMOUNT, FK_CURRENCY, FK_AMOUNT_TYPE)" + 
						" VALUES(3, 4, 4, 1, 0, 0)");
			// Carrots:
			db.executeSQL("INSERT INTO " + schema + "ORDER_HAS_PRODUCT(FK_ORDER, FK_PRODUCT, CURRENT_PRICE, AMOUNT, FK_CURRENCY, FK_AMOUNT_TYPE)" + 
					" VALUES(3, 6, 3, 1, 0, 0)");			
			// Tomatoes:
			db.executeSQL("INSERT INTO " + schema + "ORDER_HAS_PRODUCT(FK_ORDER, FK_PRODUCT, CURRENT_PRICE, AMOUNT, FK_CURRENCY, FK_AMOUNT_TYPE)" + 
					" VALUES(3, 8, 7, 1, 0, 0)");			

			// Order 4: 5 Granny Smith apples, 3 Carrots Milan, 10 Tomatoes
			
			// Create Order:
			db.executeSQL("INSERT INTO " + schema + "ORDERS (IDORDER , FK_USERS_CUSTOMER , FK_USERS_PICKUP , FK_USERS_FINANCIALS , FK_ORDER_STATUS , FK_PACKAGING_TYPE , PICKUP_DATE )" +
					" VALUES(4, 3, 4, 5, 0, 0 , '11.12.2014')");

			// Granny Smith apples:
			db.executeSQL("INSERT INTO " + schema + "ORDER_HAS_PRODUCT(FK_ORDER, FK_PRODUCT, CURRENT_PRICE, AMOUNT, FK_CURRENCY, FK_AMOUNT_TYPE)" + 
						" VALUES(4, 1, 2, 6, 0, 0)");
			// Carrots:
			db.executeSQL("INSERT INTO " + schema + "ORDER_HAS_PRODUCT(FK_ORDER, FK_PRODUCT, CURRENT_PRICE, AMOUNT, FK_CURRENCY, FK_AMOUNT_TYPE)" + 
					" VALUES(4, 5, 3, 4, 0, 0)");			
			// Tomatoes:
			db.executeSQL("INSERT INTO " + schema + "ORDER_HAS_PRODUCT(FK_ORDER, FK_PRODUCT, CURRENT_PRICE, AMOUNT, FK_CURRENCY, FK_AMOUNT_TYPE)" + 
					" VALUES(4, 8, 5, 2, 0, 0)");					
			
			db.executeSQL("INSERT INTO " + schema + "CATEGORY VALUES(1, 'Fruits', 0,'')");
			db.executeSQL("INSERT INTO " + schema + "CATEGORY VALUES(2, 'Vegetables', 0, '')");
			db.executeSQL("INSERT INTO " + schema + "CATEGORY VALUES(3, 'Meat', 0,'')");
			db.executeSQL("INSERT INTO " + schema + "CATEGORY VALUES(4, 'Apple Granny Smith', 1,'The Granny Smith is a tip-bearing apple cultivar, which originated in Australia in 1868.')");
			db.executeSQL("INSERT INTO " + schema + "CATEGORY VALUES(5, 'Apple Cripps Pink', 1,'Cripps Pink is a variety of apple, sold under the trade mark name Pink Lady.')");
			db.executeSQL("INSERT INTO " + schema + "CATEGORY VALUES(6, 'Carrot Milan', 2,'The carrot is a root vegetable, usually orange, purple, red, white, and yellow varieties exist.')");
			db.executeSQL("INSERT INTO " + schema + "CATEGORY VALUES(7, 'Tomato', 2,'The tomato is the edible, often red fruit/berry of a tomato plant.')");
			

		db.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
