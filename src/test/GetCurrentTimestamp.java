package test;

import java.sql.Timestamp;

public class GetCurrentTimestamp {
	public static Timestamp currentTimestamp;
	
	public static Timestamp getTimestamp() {
		currentTimestamp = new java.sql.Timestamp(new java.util.Date().getTime());
		return currentTimestamp;
	}
}
