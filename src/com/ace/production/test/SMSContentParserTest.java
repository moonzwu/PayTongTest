package com.ace.production.test;


import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.AndroidTestCase;

import com.ace.production.text.SMSContent;
import com.ace.production.text.SMSContentParser;

public class SMSContentParserTest extends AndroidTestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void should_get_the_correct_SMSContent_of_gdbbank() {
		SMSContentParser parser = new SMSContentParser();
		String testData = "您尾号8436广发卡12月人民币账单金额647.69，" 
			+ "最低还款100.00，还款到期01月12日。请留意电子账单，" 
			+ "若已还勿理会【广发银行】";
		SMSContent content = parser.parse(testData);
		
		Assert.assertEquals("广发银行", content.getBankName());
		Assert.assertEquals(12, content.getBillingMonth());
		Assert.assertEquals(true, 647.69f - content.getPayMoney("人民币").floatValue() < Float.MIN_VALUE);
		Assert.assertEquals(true, 100.00f - content.getTheLeastPayMoney() < Float.MIN_VALUE);
		Assert.assertEquals(1, content.getPayTime().getMonth());
		Assert.assertEquals(12, content.getPayTime().getDate());
		
	}
	
	@Test
	public void should_get_the_correct_SMSContent_of_cmbbank(){
		SMSContentParser parser = new SMSContentParser();
		String testData = "李斌先生，您招行个人信用卡02月" +
				"账单金额人民币1,580.10，美金58.34。" +
				"到期还款日02月25日[招商银行]" ;
		SMSContent content = parser.parse(testData);
		Assert.assertEquals("招商银行", content.getBankName());
		Assert.assertEquals(2, content.getBillingMonth());
		Assert.assertEquals(true, 1580.10f - content.getPayMoney("人民币").floatValue() < Float.MIN_VALUE);
		Assert.assertEquals(true, 58.34f - content.getPayMoney("美金").floatValue() < Float.MIN_VALUE);
		Assert.assertEquals(2, content.getPayTime().getMonth());
		Assert.assertEquals(25, content.getPayTime().getDate());
	}
}
