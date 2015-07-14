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

			// 反序列化接收内容
			EmailRequest emailRequest = JSON.parseObject(
					new String(message.getData()), EmailRequest.class);
			log.info("------------------------------------------------------------------------");
			log.info("订阅者接收到的  EmailAddress = "
					+ emailRequest.getEmailAddress());
			log.info("订阅者接收到的 Topic = " + emailRequest.getEmailTopic());
			log.info("订阅者接收到的  邮件的内容  = " + emailRequest.getContent());
			log.info("订阅者接收到的  邮件发送的时间点  = " + emailRequest.getCreateTime());

			// 这里目前网友可以根据 当前系统时间 - emailRequest.getCreateTime()
			// 比较，超过多长时间，可以丢弃此次订阅信息，不消费,直接return
			log.info("------------------------------------------------------------------------");
			log.info("订阅者开始订阅消息啦！");
			log.info("开始发送邮件啦!");

			// 发邮件的代码逻辑(过程略),与本文介绍的内容没有太大联系！
			// 执行发送邮件的代码逻辑

			log.info("发送邮件成功啦!");

			log.info("订阅者结束订阅消息啦！");
			log.info("结束发送邮件啦!");
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