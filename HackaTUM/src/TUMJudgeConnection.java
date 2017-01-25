

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.net.CookieHandler;
import java.net.CookieManager;

import javax.net.ssl.HttpsURLConnection;



import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;


public class TUMJudgeConnection {


	private static final String USER_AGENT = "Mozilla/5.0";

  private static List<String> cookies;
	// HTTP GET request
	// from http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
	private static String sendGet(String url) throws Exception {
 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		if (cookies != null) {
			for (String cookie :  cookies) {
				con.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
			}
		}

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();


		// Get the response cookies
		setCookies(con.getHeaderFields().get("Set-Cookie"));
		
		//print result
		return response.toString();

	}
	
	private static String sendGetHTTPS(String url) throws Exception {
		 
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		if (cookies != null) {
			for (String cookie :  cookies) {
				con.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
			}
		}

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();


		// Get the response cookies
		setCookies(con.getHeaderFields().get("Set-Cookie"));
		
		//print result
		return response.toString();

	}
	


	  public static List<String> getCookies() {
		return cookies;
	  }

	  public static void setCookies(List<String> cookies2) {
		cookies = cookies2;
	  }
	
	
	// HTTP POST request
		private static void sendPost() throws Exception {

			String url = "https://judge.in.tum.de/isabelle/public/login.php";
			//String url = "http://localhost:8080/HackaTUM/Submission";
			URL obj = new URL(url);
			//HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			if(cookies!=null) {
				for (String cookie : cookies) {
					con.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
				}
			}
			con.setRequestProperty("Connection", "keep-alive");

			String urlParameters = "cmd=login&login="+ LocalSettings.tumjudgeUser +"&passwd"+ LocalSettings.tumjudgePass ;

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString());

		}
	
	
   public static String doSubmission(String file, String shortname, String langid, String contest) {
	   
       String charset = "UTF-8";
       System.out.println("THE FILE TO UPLOAD: "+ file);
       System.out.println("shortname: "+ shortname);
       System.out.println("langid: "+ langid);
       System.out.println("contest: "+ contest);
       File uploadFile1 = new File(file); 
       String requestURL = "https://judge.in.tum.de/isabelle/api/submissions";
       String ret = "";
       try {
           MultipartUtility multipart = new MultipartUtility(requestURL, charset );
            
          // multipart.addHeaderField("User-Agent", "CodeJava");
         //  multipart.addHeaderField("Test-Header", "Header-Value");

           multipart.addFormField("shortname", shortname); 
           multipart.addFormField("langid", langid); 
           multipart.addFormField("contest", contest); 
           
           multipart.addFilePart("code[]", uploadFile1); 
           
           List<String> response = multipart.finish();
            
           System.out.println("SERVER REPLIED:");
            
           for (String line : response) {
               System.out.println(line);
               ret = ret+line;
           }
       } catch (IOException ex) {
           System.err.println(ex);
       }
       return ret;
	   
   }
		
   
   
   
   /**
    * For a given Submission ID, checks whether the submission has been judged correctly:
    * possible outcomes:
    * 
    * "pending?!", "correct", "run-error"
    * 
    * TODO: maybe we want to have integers, say 0=correct, 1=run-error, 2=pending, >2 is dont know...
    * 
    * @param submissionid
    * @return
    * @throws Exception
    */
  	public static String getjudging(String submissionid) throws Exception {

		CookieHandler.setDefault(new CookieManager());
		//login
		sendPost();
		
		String result = sendGetHTTPS("https://judge.in.tum.de/isabelle/api/judgings");
		JSONParser parser = new JSONParser();
		
		// now parse the JSONArray
        boolean accepted = false;
        boolean runerror = false;

		 try{
	         Object obj = parser.parse(result);
	         JSONArray array = (JSONArray)obj;
				
	         List<String> list = new ArrayList<String>(); 
	         for(int i = 0 ; i < array.size() ; i++){

		         JSONObject judgement = (JSONObject)array.get(i);
	        	 long submissionID = (long)judgement.get("submission");
	        	 long id = (long)judgement.get("id");
	        	 double timeStamp = (double) judgement.get("time");
	        	 String outcome = (String)judgement.get("outcome");
	        	 
	        	 
	        	 if(Long.toString(submissionID).equals(submissionid)) {
	        		 
	        		 if(accepted || outcome.equals("correct")) {
	        			 accepted = true;
	        			 runerror = false;
	        		 }
	        		 else if(outcome.equals("run-error"))
	        			 runerror = true;
	        			 
		        	 System.out.println("number "+i+":");
		        	 System.out.println("submission: " + submissionID);
		        	 System.out.println("id: " + id);
		        	 System.out.println("time1: " + timeStamp);
		        	 System.out.println("outcome: " + outcome);
	        	 }
	         }
		 } catch(ParseException pe){
	 			
		         System.out.println("position: " + pe.getPosition());
		         System.out.println(pe);
		      }

		 // FIXME: this looks for at least one submission being correctly judged. if later judgings (rejudgings) say it is not accpeted, this is worthless. 
		 //			maybe we want to have that in other ways.
		if(accepted)
			return "correct";
		else if(runerror)
			return "run-error";
		else 
			return "pending?!";		
  	}
   
   
   	public static String submit(String file, String shortname, String langid, String contest) throws Exception {

		CookieHandler.setDefault(new CookieManager());
		sendPost();
		return doSubmission(  file,   shortname,   langid,   contest);
   		
   	}
	// http://judge.in.tum.de/isabelle/api/user 
	
	public static void main(String args[]) throws Exception {
 
		
		CookieHandler.setDefault(new CookieManager());
		sendPost();
		//doSubmission("/home/haslbema/documents/test.txt", "mod9", "isabelle", "ICPC");
		
		System.out.println(getjudging("28"));
		return;
		/*
		// make sure cookies is turn on
		
		String result = sendGet("https://judge.in.tum.de/isabelle/api/user");
		System.out.println("RESULT:");
		System.out.println(result);
		System.out.println(cookies.size());
		System.out.println(cookies.get(0));
		

		doSubmission("/home/haslbema/documents/test.txt");
		
		sendPost();

		doSubmission("/home/haslbema/documents/test.txt");
		
		String result2 = sendGet("https://judge.in.tum.de/isabelle/api/user");
		System.out.println("RESULT2:");
		System.out.println(result2);
		System.out.println("\n-------\n");
		if(cookies!=null) {
			System.out.println(cookies.size());
			System.out.println(cookies.get(0));
		} else System.out.println("now cookies empty!");
		String result3 = sendGet("https://judge.in.tum.de/isabelle/api/user");
		System.out.println("RESULT3:");
		System.out.println(result3);
		
		doSubmission("/home/haslbema/documents/test.txt");
		*/
	}
	
	
	
}
