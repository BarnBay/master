package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DB_Connection {

	Connection con;

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
	
	public void close(){
		try {
			this.con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
