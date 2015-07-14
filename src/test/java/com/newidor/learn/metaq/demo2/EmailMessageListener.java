package com.newidor.learn.metaq.demo2;

import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.consumer.MessageListener;

public class EmailMessageListener implements MessageListener {

	private static Log log = LogFactory.getLog(EmailMessageListener.class);

	public void recieveMessages(Message message) throws InterruptedException {

		try {

			log.info("------------------------------------------------------------------------");
			log.info("Receive Email message, BrokerId-Partition:"
					+ message.getPartition().getBrokerId() + ","
					+ message.getPartition().getPartition() + ", "
					+ message.getTopic() + " ," + message.getId());

			// �����л���������
			EmailRequest emailRequest = JSON.parseObject(
					new String(message.getData()), EmailRequest.class);
			log.info("------------------------------------------------------------------------");
			log.info("�����߽��յ���  EmailAddress = "
					+ emailRequest.getEmailAddress());
			log.info("�����߽��յ��� Topic = " + emailRequest.getEmailTopic());
			log.info("�����߽��յ���  �ʼ�������  = " + emailRequest.getContent());
			log.info("�����߽��յ���  �ʼ����͵�ʱ���  = " + emailRequest.getCreateTime());

			// ����Ŀǰ���ѿ��Ը��� ��ǰϵͳʱ�� - emailRequest.getCreateTime()
			// �Ƚϣ������೤ʱ�䣬���Զ����˴ζ�����Ϣ��������,ֱ��return
			log.info("------------------------------------------------------------------------");
			log.info("�����߿�ʼ������Ϣ����");
			log.info("��ʼ�����ʼ���!");

			// ���ʼ��Ĵ����߼�(������),�뱾�Ľ��ܵ�����û��̫����ϵ��
			// ִ�з����ʼ��Ĵ����߼�

			log.info("�����ʼ��ɹ���!");

			log.info("�����߽���������Ϣ����");
			log.info("���������ʼ���!");
			log.info("End Send Email:" + emailRequest.getEmailAddress());

			log.info("------------------------------------------------------------------------");

		} catch (Exception ex) {

			log.error("EmailMessageListener exception", ex);

		}
	}

	public Executor getExecutor() {
		// TODO Auto-generated method stub
		return null;
	}
}