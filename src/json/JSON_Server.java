package json;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSON_Server {
	
	
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
}
