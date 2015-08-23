package com.newidor.learn.hessian;


import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;

public class ClientTest {
	
	  public static String url = "http://127.0.0.1:8080/FullstackDemo/HessianService";
	    public static void  main(String[] args){
	        HessianProxyFactory factory = new HessianProxyFactory();
	        try {
	            HelloService helloService = (HelloService) factory.create(HelloService.class, url);
	            System.out.println(helloService.helloWorld("jimmy"));
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        }
	    }

}
