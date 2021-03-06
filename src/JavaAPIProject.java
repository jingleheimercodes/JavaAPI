import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Java project to practice with APIs
 *
 * @author     Jake Jacobs-Smith (jacobjv@auburn.edu)
 * @version    2021-1-1
 *
 */

public class JavaAPIProject {
	
	private static final int CONNECTION_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(15);
	private static final int READ_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(15);
	private static String kanyeThoughts = null;

	public static void main(String[] args) {
	    String address = "https://api.kanye.rest";
	    kanyeThoughts = get(address);
	    System.out.println(kanyeThoughts);
	    System.out.println(reverseText(kanyeThoughts));
	    System.out.println(arrayBuilder(kanyeThoughts));
	    System.out.println(pigLatinTranslator(kanyeThoughts));
	}

	public static String get(String address) {
	    String result = null;
	    HttpURLConnection conn = null;
	    InputStream in = null;
	    try {
	        // building api url
	        URL url = new URL(address);
	        System.out.println("GET URL " + url.toString());
	        // establishing connection with server
	        conn = (HttpURLConnection) url.openConnection();
	        // building headers
	        conn.setReadTimeout(READ_TIMEOUT);
	        conn.setConnectTimeout(CONNECTION_TIMEOUT);
	        conn.setRequestMethod("GET");
	        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
	            in = new BufferedInputStream(conn.getInputStream());
	            // building output string from stream
	            StringBuilder sb = new StringBuilder();
	            int b;
	            while ((b = in.read()) != -1) {
	                sb.append((char) b);
	            }
	            String output = sb.toString().replace("\n", "");
	            System.out.println("GET RES " + output);
	            result = output;
	        }
	    } catch (MalformedURLException ex) {
	        System.err.println("malformed url");
	        ex.printStackTrace();
	    } catch (IOException ex) {
	        System.err.println("I/O exception");
	        ex.printStackTrace();
	    } finally {
	        if (in != null) {
	            try {
	                in.close();
	            } catch (IOException ex) {
	            }
	        }
	        if (conn != null) {
	            conn.disconnect();
	        }
	    }
	    return result;
	}
	
	public static String reverseText(String stringIn) {
		String output = "";
		for (int i = stringIn.length() - 3; i >= 10; i--) {
			output += stringIn.charAt(i);
		}
		return output;
	}
	
	public static ArrayList<String> arrayBuilder(String stringIn) {
		ArrayList<String> stringArray = new ArrayList<String>();
		int j = 10;
		for (int i = 10; i < stringIn.length(); i++) {
			if (stringIn.charAt(i) == ' '|| stringIn.charAt(i) == '"') {
				stringArray.add(stringIn.substring(j, i));
				j = i + 1;
			}
		}
		return stringArray;
	}
	
	public static String pigLatinTranslator(String stringIn) {
		String output = "";
		ArrayList<String> stringArray = arrayBuilder(stringIn);
		for (String word : stringArray) {
			if (word.contains(".") || word.contains(",") || word.contains("'")) {
				//to-do
			}
			char check = word.toLowerCase().charAt(0);
			if (check == 'a' || check == 'e' || check == 'i' || check == 'o' || check == 'u' || check == 'y') {
				output += word + "-yay ";
			} else {
				check = word.toLowerCase().charAt(1);
				if (check == 'a' || check == 'e' || check == 'i' || check == 'o' || check == 'u' || check == 'y') {
					output += word.substring(1);
					output += "-" + word.charAt(0) + "ay ";
				} else {
					output += word.substring(2);
					output += "-" + word.charAt(0) + word.charAt(1) + "ay ";
				} 
			}
		}
		return output;
	}
	
}