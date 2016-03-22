package com.packpal.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.packpal.controller.CreateProfile;
import com.packpal.model.ProfileBean;

public class TestCreateProfile {
	
	@Test
	public void testDoPost(){
		byte[] b = new byte[1];
		
		ProfileBean pfb = new ProfileBean("Daniel", "Seattle", "danielptm", "hi", b);
		Gson gson = new Gson();
		String x = gson.toJson(pfb);
		ProfileBean xyz = gson.fromJson(x, ProfileBean.class);
		System.out.println(x);
		System.out.println(xyz.getName());
		
	}
}
