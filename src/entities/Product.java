package entities;

import java.util.ArrayList;

public class Product{
	public int id;
	public String name;
	public String description;
	public int stock;
	public double price;
	public int available;
	public String farmerfirstname;
	public String farmerlastname;
	public ArrayList<Farmer> farmers = new ArrayList<Farmer>();
	public ArrayList<Barnbay> barnbays = new ArrayList<Barnbay>();
}
