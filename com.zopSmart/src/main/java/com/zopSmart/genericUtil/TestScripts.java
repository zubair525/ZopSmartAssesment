package com.zopSmart.genericUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class TestScripts {
	
	static int num = 1;
	@DataProvider
	public Object[][] getData() throws Throwable {
		ExcelUtil eutil = new ExcelUtil();
	
		int noOfRows = eutil.rowCount("Sheet1");
		//System.out.println("No of rows are"+noOfRows);
	
		ArrayList<String> l1 = new ArrayList<String>();
		ArrayList<String> l2 = new ArrayList<String>();
		
		
		for(int i=1;i<=noOfRows-1;i++) {
			
			String TestId = "TC_" + i;
			String expContentType = eutil.getExcelData("Sheet1", TestId, "content Type");
			String expBody = eutil.getExcelData("Sheet1", TestId, "body");
			
			l1.add(expContentType);
			l1.add(expBody);
			
			String actualContentType = eutil.getExcelData("data", TestId, "content Type");
			String actualBanner = eutil.getExcelData("data", TestId, "Banner");
			String actualEmailAddress = eutil.getExcelData("data", TestId, "emailaddress");
			
			l2.add(actualContentType);
			l2.add(actualBanner);
			l2.add(actualEmailAddress);
		}
		
		Object [][] obj=new Object[noOfRows-1][2];

		for(int i = 0 ; i < noOfRows-1 ; i++)
		{
			String data1 = l1.get(0) + ";" + l1.get(1);
			String data2 = l2.get(0) + ";" + l2.get(1) + ";" + l2.get(2);
			
			obj[i][0] = data1;
			obj[i][1] = data2;
			
			for(int j = 0 ; j < 2 ; j++) {
				l1.remove(0);
			}
			for(int j = 0 ; j < 3 ; j++) {
				l2.remove(0);
			}
		}
		
		return obj;
	}
	public HashMap<String, Object> jsonToMap(String t) throws JSONException {

        HashMap<String, Object> map = new HashMap<String, Object>();
        JSONObject jObject = new JSONObject(t);
        Iterator<?> keys = jObject.keys();

        while( keys.hasNext() ){
           String key = (String)keys.next();
           try {
        	   String value = jObject.getString(key); 

        	   key = key.replace("”", "").replace("“", "");
        	   value = value.replace("”", "").replace("“", "");
        	   map.put(key, value);
           }catch(Exception e)
           {
        	   Object value = jObject.getInt(key);
        	   map.put(key, value);
           }

        }
        return map;
    }

	@Test(dataProvider = "getData")
	public void test(String expected , String actual) throws Throwable {
		
		ExcelUtil eutil = new ExcelUtil();
//		System.out.println("expected : " + expected);
//		System.out.println("Actual : " + actual);
		System.out.println("----------------------------------------------------");
		String[] exp = expected.split(";");
		String[] act = actual.split(";");

		// validate content type
		boolean flag1 = exp[0].equals(act[0]);
		
		boolean flag2 = false; 
		boolean flag3 = false;
		HashMap<String, Object> map = jsonToMap(exp[1]);
		
		if(map.containsKey("banner") && map.containsKey("emailaddress")) {
		// validate banner
			flag2 = jsonToMap(exp[1]).get("banner").equals(act[1]);
		
		// validate emailaddress
			flag3 = jsonToMap(exp[1]).get("emailaddress").equals(act[2]);
		}
		
		String TestId = "TC_" + num++;
		
		if(flag1 && flag2 && flag3)
		{
			System.out.println("Test : "+TestId+" is pass");
			String statusCode = eutil.getExcelData("Sheet1", TestId, "status Code");
			System.out.println("status code is : " + statusCode);
			System.out.println("Json Data is : " + exp[1]);
			
		}else {
			System.out.println("Test :"+TestId+" is fail");
 
			String statusCode = eutil.getExcelData("Sheet1", TestId, "status Code");
			System.out.println("status code is : " + statusCode);
		}
	
		System.out.println("----------------------------------------------------------------");
		
	
		
	}

}
