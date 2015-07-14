package com.newidor.learn.metaq.demo2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.SendMessageCallback;
import com.taobao.metamorphosis.client.producer.SendResult;

public class SendMessageCallbackImpl implements SendMessageCallback {
	private static Log log = LogFactory.getLog(SendMessageCallbackImpl.class);

	private MessageProducer messageProducer;
	private String topic;
	private String content;

	public SendMessageCallbackImpl(MessageProducer messageProducer,
			String topic, String content) {

		super();
		this.messageProducer = messageProducer;
		this.topic = topic;
		this.content = content;

	}

	public void onException(Throwable e) {

		log.fatal("metaq server exception , error message:" + e.getMessage());
		log.info("--------------------------------------------------------------");
		// 出现异常时写入日志文件，进行补偿机制
		logToFile();
		log.info("--------------------------------------------------------------");
	}

	public void onMessageSent(SendResult result) {

		log.info("result = " + result);
		if (!result.isSuccess()) {

			log.info("--------------------------------------------------------------");
			log.warn("Send " + topic + " message failed,error message:"
					+ result.getErrorMessage());
			// 没有收到broker服务器的正常应答时写入日志文件，进行补偿机制
			logToFile();

		} else {
			log.info("--------------------------------------------------------------");
			log.info("Send " + topic + " successfully,sent to "
					+ result.getPartition() + " " + result.getOffset());
			log.info("--------------------------------------------------------------");
		}

	}

	private void logToFile() {

		// 定义日志文件
		String fileName = MessageSessionFactoryManager.getMessagedir()
				+ UUID.randomUUID().toString();
		File file = new File(fileName);
		FileWriter fw = null;

		try {

			fw = new FileWriter(file);
			fw.write(topic + "\r\n");
			fw.write(content);
			fw.flush();

		} catch (IOException e) {

			log.error(e);

		} finally {

			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
				}
			}

		}
	}
}