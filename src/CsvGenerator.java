

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;

public class CsvGenerator {

	public CsvGenerator() {
	}

	public int listtoCsv(LinkedList<Restaurant> list) {
		
		int count = 0;
		
		System.out.println("list size : " + list.size());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Calendar cal = Calendar.getInstance();
		String fileName = dateFormat.format(cal.getTime());

		FileWriter writer = null;
		try {
			writer = new FileWriter("Restaurant_list_" + fileName + ".csv");

			writer.append("name");
			writer.append(",");
			writer.append("address");
			writer.append(",");
			writer.append("url");
			writer.append(",");
			writer.append("\n");

			System.out.println(" -- out size-- "+ list.size());
			if(list.size() > 0) {
				Iterator<Restaurant> it = list.iterator();
	
				while (it.hasNext()) {
					Restaurant res = (Restaurant) it.next();
					try {
					writer.append(csvformatDevider(res.getName()));
					writer.append(",");
					writer.append(csvformatDevider(res.getAddress()));
					writer.append(",");
					writer.append(csvformatDevider(res.getUrl()));
					
					writer.append("\n");
					count++;
				}catch (IOException e) {
					//..
				}
			}
			}		

		} catch (IOException e) {
			System.out.println(" csv g Error : " + e.getMessage());
		}finally {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		return count;
	} 

	private String csvformatDevider(String text) {
		String newText; 
		try {
		newText = text.replaceAll("[\\t\\n\\r]", " ");
		
		if (newText.contains(","))
			if (!newText.startsWith("\"") && !newText.endsWith("\""))
				newText = "\"" + newText + "\"";
		}catch (Exception e) {
			return "";
		}
		return newText;
	}
	
	

}
