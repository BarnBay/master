package json;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSON_Server {
	
	public static void http_get_json(JSONObject jsonobject, HttpServletResponse response ) {
		if (jsonobject == null) {
			jsonobject = new JSONObject();
		}
		try {
			response.getWriter().println(jsonobject.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static JSONObject http_post_json(HttpServletRequest request){
		BufferedReader bufferedreader;
		StringBuffer jb = new StringBuffer();
		String line;
		JSONObject jsonobject = new JSONObject();
		JSONParser jsonparser = new JSONParser();
		String mJSONData;
		mJSONData = "";
		
		try {
			bufferedreader = request.getReader();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { /*report an error*/ }

		
		try {
			jsonparser = new JSONParser();
			jsonobject = (JSONObject)jsonparser.parse(jb.toString());
			
		} catch(ParseException e) {
			// Nothing to do here
		}	
		return jsonobject;
	}
}
