package com.newidor.learn.metaq.demo2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;

public class Consumer {

	private static Log log = LogFactory.getLog(Consumer.class);

	public static void main(String[] args) throws Exception {
		final String topic = "email";
		final String group = "meta-example";

		try {

			// 复用SessionFacotory(单例模式) 发布者和订阅者共用sessionFactory
			MessageConsumer consumer = MessageSessionFactoryManager
					.getSessionFactory(false).createConsumer(
							new ConsumerConfig(group));

			// 每次订阅300k的字节流内容，将订阅信息保存到本地
			consumer.subscribe(topic, 1024 * 300, new EmailMessageListener());

			// completeSubscribe一次性将所有的订阅生效，并处理zk和metaq服务器的所有交互过程
			consumer.completeSubscribe();

		} catch (Exception ex) {
			log.error(ex);
		}
	}
}