package entities;

import java.util.ArrayList;

/**
 * 
 * @author Anne
 * 
 * Product entity class
 *
 */
public class Product{
	public int id;
	public int catid;
	public String name;
	public String description;
	public String farmerproductdescription;
	public int stock;
	public double price;
	public int available;
	public String farmerfirstname;
	public String farmerlastname;
	public Address farmeraddress;
	public String farmeremail;
	public ArrayList<Farmer> farmers = new ArrayList<Farmer>();
	public ArrayList<Barnbay> barnbays = new ArrayList<Barnbay>();
}
