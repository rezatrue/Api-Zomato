import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello --- Hello---Hello");

		String queryCity = "New York City, NY";
		LinkedList<Restaurant> restaurants = new LinkedList<>();
		
		CitiesApi citiesApi = new CitiesApi();
				
		LinkedList<City> cities = citiesApi.getZomatoCityID(queryCity);
		
		if(cities.size() > 0) {
			
			Iterator<City> it = cities.iterator();
			
			if(it.hasNext()) {
				City city = it.next();
				int id = city.getId();
		        String name = city.getName();
				System.out.println(id + " : " + name );
				SearchApi searchApi = new SearchApi();
				restaurants.addAll(searchApi.getRestaurants(id));
				//LinkedList<Restaurant> restaurants = searchApi.getRestaurants(id);
			}
			
//			while(it.hasNext()) {
//				City city = it.next();
//				int id = city.getId();
//		        String name = city.getName();
//				System.out.println(id + " : " + name );
//			}
		}
		
		/*
		SearchApi searchApi = new SearchApi();
		LinkedList<Restaurant> restaurants = searchApi.getRestaurants(280);
		*/
		
		CsvGenerator csvGenerator = new CsvGenerator();
		csvGenerator.listtoCsv(restaurants);
		
	}

}
