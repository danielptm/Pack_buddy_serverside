package com.packpal.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class DataTypeConversion {
	
    public String convertStreamToString(InputStream is){
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
//                Log.i(info, line);
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
        	try {
				r.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return total.toString();
    }
    
    public static String getJsonStringWithGson(EmailAndPasswordBean epb){
        Gson gson = new Gson();
        String x = gson.toJson(epb, EmailAndPasswordBean.class);
        return x;
    }
    
    public static String getJsonStringWithGson(ProfileBean pfb){
        Gson gson=null;
        gson = new Gson();
        String x = gson.toJson(pfb, ProfileBean.class);
        return x;
    }
    
    public static EmailAndPasswordBean getEmailPasswordBean(String jsonString){
    	Gson gson = new Gson();
    	EmailAndPasswordBean epb = gson.fromJson(jsonString, EmailAndPasswordBean.class);
    	return epb;
    }
    
    private String convertStreamToString2(InputStream is){
    	BufferedReader r;
    	StringBuilder total = new StringBuilder();
		r = new BufferedReader(new InputStreamReader(is));
		
		String line;
		try {
			while (( line = r.readLine() ) != null) {
			    total.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return total.toString();
    }
    
    public JsonObject getProfileAsJson2(ProfileBean pfb){
        JsonObject jobj=null;
        jobj = new JsonObject();
        jobj.addProperty("img", pfb.getImg() );
        jobj.addProperty("password", pfb.getPassword());
        jobj.addProperty("homeCity", pfb.getHomeCity());
        jobj.addProperty("email", pfb.getEmail());
        jobj.addProperty("name", pfb.getName());
        return jobj;
    }
    public String getEntireStream(HttpServletRequest request ){
//    	DataInputStream ds = new DataInputStream(request.getInputStream());
//    	System.out.println("Data inputStream length is it greater than 73000??? "+ds.av);
    	return null;
    }

}
