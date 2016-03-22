package com.packpal.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
import com.packpal.logging.LP;
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
		l.logInfo("Servlet is being served.");
		PrintWriter pw = response.getWriter();
		Gson gson = new Gson();
		l.logInfo("line 35");
		InputStream is = request.getInputStream();
		String x = convertStreamToString(is);
		l.logInfo("line 37 is.toString(): "+x);
		pw.write("From the servlet your android device has entered this data..... "+ x);
//		ProfileBean pfb = gson.fromJson(is.toString(), ProfileBean.class);
//		l.logInfo("line 39.");
//		l.logInfo(pfb.getName()+" "+pfb.getEmail()+" "+pfb.getHomeCity()+" "+pfb.getImg());
//		l.logInfo("COMPLETE	");
		
		

		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}
	
    private String convertStreamToString(InputStream is){
    	LP l = new LP();
    	
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder jsonString =  new StringBuilder();

        String line=null;
        try {
            while((line = br.readLine())!=null){
            	l.logInfo("Line of input stream---- "+line);
            	
                jsonString.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonString.toString();
    }
}
