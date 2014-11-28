package entities;

public class User {
	public Integer id;
	public String firstname;
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
	
	
	public void generateSession() {
		RandomString random = new RandomString(16);
		this.session = random.nextString();
	}
}



