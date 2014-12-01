package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 
 * This class manages the connection to the database (local Derby
 *  or SAP HANA) and offers methods that are used by the services
 * @author Anne
 * 
 */
public class DB_Connection {

	Connection con;

	/** The constructor takes the initial context (the initial context 
	 * implements the Context interface and provides the starting point 
	 * for resolution of names) and looks up the data source,
	 * then it gets the connection to the datasource
	 * 
	 * -> here is the inital touch to the database (HANA or Derby) done!
	 * 
	 */
	public DB_Connection() {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx
					.lookup("java:comp/env/jdbc/DefaultDB");
			con = ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/** Update table method which returns true or false
	 * 
	 * @param sql SQL-String
	 * @return true or false indicating whether the operation succeeded
	 */
	public boolean executeSQLbool(String sql) {
		int res = 0;
		try {
			Statement s = con.createStatement();
			res = s.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (res == 0) {
			return false;
		} else {
			return true;
		}
	}

	/** This method takes an SQL-String,
	 * executes it agains the database
	 * and returns and SQL-ResultSet
	 * 
	 * @param sql SQL-String
	 * @return ResultSet
	 */
	public ResultSet executeSQL(String sql) {
		ResultSet rs = null;
		try {
			Statement s = con.createStatement();
			s.execute(sql);
			rs = s.getResultSet();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
	/**
	 * This method closes the connection to the db
	 */
	public void close(){
		try {
			this.con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used for distinguishing
	 * between the local test-schema and the actual
	 * schema on the SAP HANA Cloud Platform according to the URL
	 * 
	 * @param url url-String
	 * @return schema name as String
	 */
	public static String getSchemaName(String url){
		String s;
		if(url.indexOf("ondemand.com") > -1){
			s="";
		}else{
			s="TEST.";
		}
		return s;
	}
}
