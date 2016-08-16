package com.epweike;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

public class PropTest {

	@Test
	public void test() throws FileNotFoundException, IOException {
		Properties pps = new Properties();
		pps.load(new FileInputStream(PropTest.class.getResource("ReleaseSingleReward.properties").getPath()));
		System.out.println("home_url:"+pps.getProperty("home_url"));
	}

}
