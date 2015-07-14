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

			// ����SessionFacotory(����ģʽ) �����ߺͶ����߹���sessionFactory
			MessageConsumer consumer = MessageSessionFactoryManager
					.getSessionFactory(false).createConsumer(
							new ConsumerConfig(group));

			// ÿ�ζ���300k���ֽ������ݣ���������Ϣ���浽����
			consumer.subscribe(topic, 1024 * 300, new EmailMessageListener());

			// completeSubscribeһ���Խ����еĶ�����Ч��������zk��metaq�����������н�������
			consumer.completeSubscribe();

		} catch (Exception ex) {
			log.error(ex);
		}
	}
}