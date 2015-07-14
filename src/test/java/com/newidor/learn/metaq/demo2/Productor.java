package com.newidor.learn.metaq.demo2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.producer.MessageProducer;

public class Productor {
	private static Log log = LogFactory.getLog(Productor.class);

	public static void main(String[] args) throws Exception {
		// 由消息工厂产生消息生产者
		MessageProducer producer = MessageSessionFactoryManager
				.getSessionFactory(true).createProducer();
		// 设置topic,必须要在server.ini文件中进行配置
		final String topic = "email";
		// 发布topic
		producer.publish(topic);
		// 发布内容(根据实际业务来组装内容)
		String line = "网络时空(阿堂)恭喜大家2015年心想事成!";
		try {
			// 模拟发邮件(根据实际业务定义，可以是发邮件，发短信，httpPost提交 等均可)
			EmailRequest request = new EmailRequest("heyitang@qq.com", topic,
					line);

			// 序列化发送内容
			String jsonString = JSON.toJSONString(request,
					SerializerFeature.WriteClassName);

			log.info("------------------------------------------------------------------------");
			log.info("发布者发送的  EmailAddress = " + request.getEmailAddress());
			log.info("发布者发送的 Topic = " + request.getEmailTopic());
			log.info("发布者发送的  邮件的内容  = " + request.getContent());
			log.info("发布者发送的  邮件发送的时间点  = " + request.getCreateTime());
			log.info("------------------------------------------------------------------------");

			// 发布订消息，这里老何定义了重写回调SendMessageCallbackImpl实现方法
			producer.sendMessage(new Message(topic, jsonString.getBytes()),
					new SendMessageCallbackImpl(producer, topic, jsonString));

		} catch (Exception ex) {
			log.error(ex);
		}
	}

}
