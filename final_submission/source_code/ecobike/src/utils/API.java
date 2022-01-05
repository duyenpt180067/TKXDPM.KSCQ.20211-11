package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

public class API {
	public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static Logger LOGGER = Utils.getLogger(Utils.class.getName());
	
	private static HttpURLConnection setupConnection(String url, String method
//			,String token
			) throws IOException{
		LOGGER.info("REequest URL: " + url +"\n");
		URL line_api_url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", "application/json");
	//	conn.setRequestProperty("Authorization", "Bearer " + token);
		return conn;
	}
	
	private static String readResponse(HttpURLConnection conn) throws IOException {
		BufferedReader in;
		String inputLine;
		if(conn.getResponseCode() /100 ==2) {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		}
		else {
			in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			
		}
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);
		in.close();
		LOGGER.info("Respone Info: " + response.toString());
		return response.toString();
		
	}

	public static String get(String url, String token) throws Exception {
		//phan 1: setup
		HttpURLConnection conn = setupConnection(url, "GET");
		
		// phan 2: doc du lieu tra ve tu server
		String respone = readResponse(conn);
		
		return respone;
	}

	int var;

	public static String post(String url, String data
		//	, String token
			) throws IOException {
		allowMethods("PATCH");
		//phan 1 setup 
		HttpURLConnection conn = setupConnection(url, "PATCH");
		
		//phan2: gui du lieu
		Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(data);
		writer.close();
		
		// phan 3: doc du lieu tra ve tu server
		String respone = readResponse(conn);
		return respone;
	}
	
	
	/**
	 * Phuong thuc cho phep hoat dong voi cac giao thuc khac nhu PUT,PATCH...
	 *  use for java <11
	 * @param methods patch
	 * 
	 */

	private static void allowMethods(String... methods) {
		LOGGER.info("allowMethods: " );
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			methodsField.setAccessible(true);

			VarHandle MODIFIERS = MethodHandles.privateLookupIn(Field.class, MethodHandles.lookup()).findVarHandle(Field.class, "modifiers", int.class);
			int mods = methodsField.getModifiers();
			if(Modifier.isFinal(mods)){
				MODIFIERS.set(methodsField, mods & ~Modifier.FINAL);
			}

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
	
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}

}
