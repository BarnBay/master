package entities;

import java.sql.*;

public class Product {
	int fk_amount_type;
	int fk_currency;
	Date deactivation_date;
	Date setup_date;
	int available;
	int current_stock;
	int fk_overall_rating;
	double price;
	int fk_user;
	int fk_category;
	String product_descripition;
	int id_product;
	
}
