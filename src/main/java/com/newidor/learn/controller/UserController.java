package com.newidor.learn.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.newidor.learn.model.User;
import com.newidor.learn.service.UserServiceI;

@Controller
@RequestMapping("/userController")
public class UserController {

	private UserServiceI userService;

	public UserServiceI getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
	}

	@RequestMapping("/{id}/showUser")
	public String showUser(@PathVariable String id, HttpServletRequest request) {
		User u = userService.getUserById(id);
		request.setAttribute("user", u);
		request.setAttribute("role", u.getUserRoles().get(0).getRole());
		System.out.println("123456");
		return "showUser";
	}

	@RequestMapping("/{id}/showUser2")
	public ModelAndView showUserModelAndView(@PathVariable String id,
			HttpServletRequest request) {
		User u = userService.getUserById(id);
		request.setAttribute("user", u);
		request.setAttribute("role", u.getUserRoles().get(0).getRole());
		System.out.println("123456");
		// 效果等同showUser中的只返回showUser字符串
		return new ModelAndView("showUser");
	}

	@RequestMapping("/{id}/showUser3")
	public ModelAndView showUserModelAndView(User user,
			HttpServletRequest request) {
		User u = userService.getUserById(user.getId());
		user.setName(u.getName());
		// 此处的user为null，但应该会在到View层前由SpringMVC进行request.setAttribute("user",user)设置
		System.out.println(request.getAttribute("user"));
		request.setAttribute("role", u.getUserRoles().get(0).getRole());
		System.out.println("1234567");
		return new ModelAndView("showUser");
	}

	/**
	 * http://localhost:8080/FullstackDemo/userController/user/2
	 */
	@RequestMapping(value="/user/{id}")
	public @ResponseBody User showUserRestful(HttpServletRequest request,
			@PathVariable String id) {
		User u = userService.getUserById(id);
		return u;
	}

}
