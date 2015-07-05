package com.newidor.learn.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

public class TestSpringMVC {

	private static HandlerMapping handlerMapping;
	private static HandlerAdapter handlerAdapter;

	/**
	 * 读取spring3 MVC配置文件
	 */
	@BeforeClass
	public static void setUp() {
		if (handlerMapping == null) {
			String[] configs = { "classpath:spring-mvc.xml",
					"classpath:spring.xml", "classpath:spring-mybatis.xml" };
			XmlWebApplicationContext context = new XmlWebApplicationContext();
			context.setConfigLocations(configs);
			MockServletContext msc = new MockServletContext();
			context.setServletContext(msc);
			context.refresh();
			msc.setAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
					context);
			System.out.println(context.getBean("userController"));
			handlerMapping = (HandlerMapping) context
					.getBean(DefaultAnnotationHandlerMapping.class);
			//不支持Restful调用，暂时不知道原因
			handlerAdapter = (HandlerAdapter) context
					.getBean(context
							.getBeanNamesForType(AnnotationMethodHandlerAdapter.class)[0]);
		}
	}

	@Test
	public void testUserController() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		// request.setContentType("application/xml");
		request.setServletPath("/userController/2/showUser.do");
		// 执行URI对应的action
		final ModelAndView mav = this.excuteAction(request, response);
		System.out.println(mav.getViewName());
	}

	/**
	 * 执行request对象请求的action
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView excuteAction(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HandlerExecutionChain chain = handlerMapping.getHandler(request);
		final ModelAndView model = handlerAdapter.handle(request, response,
				chain.getHandler());
		return model;
	}

}
