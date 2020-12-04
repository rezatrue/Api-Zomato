import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


public class CitiesApi {

	public CitiesApi() {}
	
	public LinkedList<City> getZomatoCityID(String cityName){
		
		LinkedList<City> cities = new LinkedList<>();
		
		String parameter = "";
		try {
			parameter = URLEncoder.encode(cityName,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return cities;
		}
		
		String apiKey = "d779d76b19c9c1dd7d1ea14cbec53e70";
		String queryUrl = "https://developers.zomato.com/api/v2.1/cities?q="+ parameter +"&count=1" ;
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(queryUrl);
		
		httpGet.addHeader("Accept", "application/json");
		httpGet.addHeader("user-key", apiKey);
		

		// The underlying HTTP connection is still held by the response object
		// to allow the response content to be streamed directly from the network socket.
		// In order to ensure correct deallocation of system resources
		// the user MUST call CloseableHttpResponse#close() from a finally clause.
		// Please note that if response content is not fully consumed the underlying
		// connection cannot be safely re-used and will be shut down and discarded
		// by the connection manager. 
		CloseableHttpResponse response1 = null;
		try {
			response1 = httpclient.execute(httpGet);
		    System.out.println(response1.getStatusLine());
		    HttpEntity entity1 = response1.getEntity();
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    //EntityUtils.consume(entity1);
		    
		    String responseString = EntityUtils.toString(entity1, "UTF-8");
		    System.out.println(responseString);
		    
		    JSONObject obj = new JSONObject(responseString);
		    String res = obj.getString("status");
		    System.out.println(res);

		    if(res.equalsIgnoreCase("success")) {
			    JSONArray user = obj.getJSONArray("location_suggestions");
			    
			    for (int i = 0; i < user.length(); i++)
			    {
			        int id = user.getJSONObject(i).getInt("id");
			        String name = user.getJSONObject(i).getString("name");
			        cities.add(new City(id, name));
			    }
		    }
		    
		}catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
				try {
					response1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	
	return cities;
		
	}
	
}
