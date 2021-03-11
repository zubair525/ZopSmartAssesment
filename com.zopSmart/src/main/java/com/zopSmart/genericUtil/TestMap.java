package com.zopSmart.genericUtil;

import java.util.HashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestMap {

	@Test(dataProvider = "getData")
	public void m1(String x , String y)
	{
		System.out.println(x);
		System.out.println(y);
	}
	
	@DataProvider
	public Object [][] getData()
	{
		System.out.println("bye");
		Object[][] obj = new Object[2][2];
		obj[0][0] = "zubair";
		obj[0][1] = "ahmed";
		obj[1][0] = "ajaz";
		obj[1][1] = "ahmed";
		return obj;
	}
}
