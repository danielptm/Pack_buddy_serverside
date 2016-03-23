package com.packpal.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.packpal.logging.LP;
import com.packpal.model.DbHandler;
import com.packpal.model.ProfileBean;

public class CreateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LP l = new LP();
		l.logInfo("in servlet");
		Writer pw = response.getWriter();
		response.setContentType("application/json");
		Gson gson = new Gson();
		InputStream is = request.getInputStream();
		String x = convertStreamToString(is);
		ProfileBean pfb = gson.fromJson(x, ProfileBean.class);
		DbHandler db = new DbHandler();
		db.writeProfileBeanToDb(pfb.getName(), pfb.getEmail(), pfb.getPassword(), pfb.getHomeCity(), pfb.getImg());
		ProfileBean sendBackProfileBean = db.getProfileFromDb(pfb);
		JsonObject jobj = getProfileAsJson(sendBackProfileBean);
		pw.write(jobj.toString());
		pw.flush();
		pw.close();	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}
	
    private String convertStreamToString(InputStream is){
    	BufferedReader r = new BufferedReader(new InputStreamReader(is));
    	StringBuilder total = new StringBuilder();
    	String line;
    	try {
			while ((line = r.readLine()) != null) {
			    total.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return total.toString();
    }
    
    public JsonObject getProfileAsJson(ProfileBean pfb){
        JsonObject jobj=null;
        jobj = new JsonObject();
        jobj.addProperty("img", pfb.getImg() );
        jobj.addProperty("password", pfb.getPassword());
        jobj.addProperty("homeCity", pfb.getHomeCity());
        jobj.addProperty("email", pfb.getEmail());
        jobj.addProperty("name", pfb.getName());
        return jobj;
    }
}
