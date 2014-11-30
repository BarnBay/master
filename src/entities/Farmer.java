package entities;

import java.util.ArrayList;

/**
 * 
 * @author Anne
 * 
 * Farmer entity class
 *
 */
public class Farmer {
	public int idfarmer;
	public String firstname;
	public String lastname;
	public String farmname;
	public String farmdescription;
	public String farmpicture;
	public String farmopeninghours;
	public String street;
	public String number;
	public String zipcode;
	public String city;
	public String email;
	public ArrayList<Barnbay> barnbays;
	public ArrayList<Product> products;
}
