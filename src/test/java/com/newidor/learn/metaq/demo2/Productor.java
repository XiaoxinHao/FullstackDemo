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
		// ����Ϣ����������Ϣ������
		MessageProducer producer = MessageSessionFactoryManager
				.getSessionFactory(true).createProducer();
		// ����topic,����Ҫ��server.ini�ļ��н�������
		final String topic = "email";
		// ����topic
		producer.publish(topic);
		// ��������(����ʵ��ҵ������װ����)
		String line = "����ʱ��(����)��ϲ���2015�������³�!";
		try {
			// ģ�ⷢ�ʼ�(����ʵ��ҵ���壬�����Ƿ��ʼ��������ţ�httpPost�ύ �Ⱦ���)
			EmailRequest request = new EmailRequest("heyitang@qq.com", topic,
					line);

			// ���л���������
			String jsonString = JSON.toJSONString(request,
					SerializerFeature.WriteClassName);

			log.info("------------------------------------------------------------------------");
			log.info("�����߷��͵�  EmailAddress = " + request.getEmailAddress());
			log.info("�����߷��͵� Topic = " + request.getEmailTopic());
			log.info("�����߷��͵�  �ʼ�������  = " + request.getContent());
			log.info("�����߷��͵�  �ʼ����͵�ʱ���  = " + request.getCreateTime());
			log.info("------------------------------------------------------------------------");

			// ��������Ϣ�������Ϻζ�������д�ص�SendMessageCallbackImplʵ�ַ���
			producer.sendMessage(new Message(topic, jsonString.getBytes()),
					new SendMessageCallbackImpl(producer, topic, jsonString));

		} catch (Exception ex) {
			log.error(ex);
		}
	}

}
