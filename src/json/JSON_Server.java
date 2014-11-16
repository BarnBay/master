package json;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import entities.Category;
import entities.Farmer;
import entities.User;

public class JSON_Server {
	
	public static JSONObject wrap_JSON(Object myObject) {
		JSONObject jsonobject;
		JSONParser jsonparser;
		Gson mygson = new Gson();
		String jsonstring = "";
		jsonstring = mygson.toJson(myObject);
		// TODO: Wrapping of objects.
		
		try {
			jsonparser = new JSONParser();
			jsonobject = (JSONObject)jsonparser.parse(jsonstring);
			
		} catch(ParseException e) {
			jsonobject = new JSONObject();
			// Nothing to do here
		}	
		
		return jsonobject;
	}
	
	public static User jsonToUser(JSONObject jsonobject) {
		User currentuser = new User();
		Gson gson = new Gson();
		currentuser = gson.fromJson(jsonobject.toString(), User.class);
		
		return currentuser;
	}
	
	public static JSONObject http_post_json(String request){
		JSONParser jsonparser;
		JSONObject jsonobject;
		try {
			jsonparser = new JSONParser();
			jsonobject = (JSONObject)jsonparser.parse(request);
			
		} catch(ParseException e) {
			jsonobject = new JSONObject();
			// Nothing to do here
		}	
		return jsonobject;
	}
	
	public static String categoryArrayToJson(ArrayList<Category> a){
		Gson g = new Gson();
		String gs = g.toJson(a);
		return gs;
	}
	
	public static String farmerArrayToJson(ArrayList<Farmer> a){
		Gson g = new Gson();
		String gs = g.toJson(a);
		return gs;
	}
}
