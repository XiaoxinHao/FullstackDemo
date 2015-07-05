package com.newidor.learn.test;

import java.util.Arrays;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;



public class HijackAroundMethod implements MethodInterceptor {
	
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		System.out.println("Method name : "
				+ methodInvocation.getMethod().getName());
		System.out.println("Method arguments : "
				+ Arrays.toString(methodInvocation.getArguments()));

		// �൱�� MethodBeforeAdvice
		System.out.println("HijackAroundMethod : Before method hijacked!");

		try {
			// ����ԭ������������CustomerService�еķ���
			Object result = methodInvocation.proceed();

			// �൱�� AfterReturningAdvice
			System.out.println("HijackAroundMethod : After method hijacked!");

			return result;

		} catch (IllegalArgumentException e) {
			// �൱�� ThrowsAdvice
			System.out
					.println("HijackAroundMethod : Throw exception hijacked!");
			throw e;
		}
	}

}
