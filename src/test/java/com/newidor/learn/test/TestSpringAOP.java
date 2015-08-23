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

	private User user;

	@Autowired
	public void setUser(User user) {
		this.user = user;
	}

	@Test
	public void testAOP() {
		user.setName("’≈»˝–°ªÔ");
	}

}
