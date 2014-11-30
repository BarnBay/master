package json;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import entities.AddProduct;
import entities.Category;
import entities.Farmer;
import entities.Product;
import entities.User;

/**
 * 
 * @author Anne
 * This class offers methods
 * for json conversion which are used by the services
 *
 */
public class JSON_Server {
	
	/**
	 * method for converting a json object to a user object
	 * 
	 * @param jsonobject
	 * @return User
	 */
	public static User jsonToUser(JSONObject jsonobject) {
		User currentuser = new User();
		Gson gson = new Gson();
		currentuser = gson.fromJson(jsonobject.toString(), User.class);
		
		return currentuser;
	}
	
	/**
	 * method for converting a json object to a AddProduct
	 * 
	 * @param jsonobject JSONObject
	 * @return AddProduct Object
	 */
	public static AddProduct jsonToProduct(JSONObject jsonobject) {
		AddProduct currentProduct = new AddProduct();
		Gson gson = new Gson();
		currentProduct = gson.fromJson(jsonobject.toString(), AddProduct.class);
		
		return currentProduct;
	}
	
	
	/**
	 * method for converting a request string to a json object
	 * 
	 * @param request String
	 * @return JSONObject
	 */
	public static JSONObject http_post_json(String request){
		JSONParser jsonparser;
		JSONObject jsonobject;
		try {
			jsonparser = new JSONParser();
			jsonobject = (JSONObject)jsonparser.parse(request);
			
		} catch(ParseException e) {
			jsonobject = new JSONObject();
		}	
		return jsonobject;
	}
	
	/**
	 * method for converting a request string to a json array
	 * 
	 * @param request String
	 * @param value String
	 * @return JSONArray
	 */
	
	public static JSONArray http_post_jsonArray(String request, String value) {
		JSONParser jsonparser;
		JSONObject jsonobject;
		JSONArray jsonarray;
		
		try {
			jsonparser = new JSONParser();
			jsonobject = (JSONObject) jsonparser.parse(request);
			jsonarray = (JSONArray) jsonobject.get(value);
		} catch(ParseException e) {
			jsonarray = new JSONArray();
		}	
		
		return jsonarray;
	}
	
	/**
	 * This method converses an object to json string
	 * 
	 * @param o Object that should be converted to json string
	 * @return Json-String gs
	 */
	public static String objectToJson(Object o){
		Gson g = new Gson();
		String gs = g.toJson(o);
		return gs;
	}
}
