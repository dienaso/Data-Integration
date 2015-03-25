package com.epweike;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epweike.model.DicBasic;
import com.epweike.service.DicBasicService;

public class DicBasicTest {

	@Autowired
	private DicBasicService service;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath:applicationContext.xml" });
		service = (DicBasicService) context.getBean("dicBasicService");
	}
	
//	@Test
//	public void readAndImportDic() {
//		String fileName = "F:\\SogouLabDic.dic";
//		
//		File file = new File(fileName); 
//		BufferedReader reader = null; 
//		try { 
//			System.out.println("以行为单位读取文件内容，一次读一整行："); 
//			//计时开始
//			long t1 = System.currentTimeMillis();
//			
//			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));  
//			String tempString = null; 
//			List<DicBasic> list = new ArrayList<DicBasic>();
//			int line = 1; 
//			//一次读入一行，直到读入null为文件结束 
//			while ((tempString = reader.readLine()) != null){ 
//				//显示行号 
//				System.out.println("line " + line + ": " + tempString); 
//				//单条数据
//				DicBasic dicBasic = new DicBasic();
//				String[] tempArr = tempString.split("	");
//				dicBasic.setWord(tempArr[0]);
//				dicBasic.setwFrequency(Integer.parseInt(tempArr[1]));
//				if(tempArr.length > 2)
//					dicBasic.setwClass(tempArr[2]);
//				//单个对象加入list
//				list.add(dicBasic);
//				
//				line++; 
//			} 
//			
//			
//			this.service.insertDicBasicBatch(list);
//			
//			reader.close(); 
//			
//			long t2 = System.currentTimeMillis(); // 排序后取得当前时间  
//			
//			//计时结束
//	        Calendar c = Calendar.getInstance();  
//	        c.setTimeInMillis(t2 - t1);  
//	        System.out.println("耗时: "
//	        		+ c.get(Calendar.MINUTE) + "分 "  
//	                + c.get(Calendar.SECOND) + "秒 " 
//	        		+ c.get(Calendar.MILLISECOND) + " 毫秒");  
//		} catch (IOException e) { 
//			e.printStackTrace(); 
//		} finally { 
//			if (reader != null){ 
//				try { 
//				reader.close(); 
//				} catch (IOException e1) { 
//				} 
//			} 
//		} 
//		
//		
//	}
	
	@Test
	public void selectPage() {
		int pageNum = 1;
		int pageSize = 30;
	    List<DicBasic> list = this.service.selectPage(pageNum, pageSize);
		System.out.println("------======"+list.toString());
	}

}
