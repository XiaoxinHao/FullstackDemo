package com.newidor.learn.test;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.newidor.learn.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-aop-test.xml" })
public class TestSpringAOP {
	
	private User userProxy;
	
	@Autowired
	public void setUser(User userProxy) {
		this.userProxy = userProxy;
	}

	
	@Test
	public void testAOP(){
		
		userProxy.setName("’≈»˝–°ªÔ");
        
	}
	

}
