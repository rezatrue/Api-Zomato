import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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


public class SearchApi {

	public SearchApi() {}
	
	public LinkedList<Restaurant> getRestaurants(int cityId) {
		
		LinkedList<Restaurant> restaurants = new LinkedList<>();
		
		// signup for api key
		String apiKey = "********************************";
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		int start = 0;
		boolean out = true;
		do {
			
			String queryUrl = "https://developers.zomato.com/api/v2.1/search?entity_id="
					+ cityId 
					+"&entity_type=city&start="
					+start;
					System.out.println("Start " + start);
					
					HttpGet httpGet = new HttpGet(queryUrl);
					
					httpGet.addHeader("Accept", "application/json");
					httpGet.addHeader("user-key", apiKey);
					

					
					CloseableHttpResponse response1 = null;
					try {
						response1 = httpclient.execute(httpGet);
					    System.out.println(response1.getStatusLine());
					    HttpEntity entity1 = response1.getEntity();
					    // do something useful with the response body
					    // and ensure it is fully consumed
					    //EntityUtils.consume(entity1);
					    
					    String responseString = EntityUtils.toString(entity1, "UTF-8");
					    //System.out.println(responseString);
					    
					    JSONObject obj = new JSONObject(responseString);
					    //String res = obj.getString("status");
					    //System.out.println(res);

					    //if(res.equalsIgnoreCase("success")) {
						    JSONArray rest = obj.getJSONArray("restaurants");
						    if(rest.length() < 1) {
						    	System.out.println("--> " + rest.length());
						    	out = false;
						    }
						    for (int i = 0; i < rest.length(); i++)
						    {
						    	
						    	JSONObject objRes = rest.getJSONObject(i).getJSONObject("restaurant");
						    	
						    	String name = objRes.getString("name");
						    	System.out.println("name : "+ name);
						    	
						    	JSONObject objLocation = objRes.getJSONObject("location");
						    	String address = objLocation.getString("address");
						    	System.out.println(address);
						    	
						    	String url = objRes.getString("url");
						    	System.out.println("url : "+ url);
						    	
						    	//System.out.println(name +" : " + url + " : " + address);
						    	restaurants.add(new Restaurant(name, url, address));
						    }
					    //}
					    
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
			
			
			//if(rest.length() < 1) out = true;
			start += start == 0 ? 21 : 20 ;
		}while(out);
	
		
	
	return restaurants;
		
	}
	
}
