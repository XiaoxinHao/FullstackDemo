package com.newidor.learn.metaq.demo2;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.gecko.service.timer.Timer;
import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;

public class MessageSessionFactoryManager {

	private static Log log = LogFactory
			.getLog(MessageSessionFactoryManager.class);

	private static MessageSessionFactory sessionFactory = null;
	private static String messagedir;

	public static String getMessagedir() {
		return messagedir;
	}

	private MessageSessionFactoryManager() {
	}

	public synchronized static MessageSessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			init(true);
		}
		return sessionFactory;
	}

	public synchronized static MessageSessionFactory getSessionFactory(
			boolean isProducer) {
		if (sessionFactory == null) {
			init(isProducer);
		}
		return sessionFactory;
	}

	private static void init(boolean isProducer) {

		try {

			String confFile = "/metaq.ini";
			InputStream in = MessageSessionFactoryManager.class
					.getResourceAsStream(confFile);
			Properties conf = new Properties();
			conf.load(in);
			String zookeeper = conf.getProperty("zookeeper");
			messagedir = conf.getProperty("messagedir");

			final MetaClientConfig metaClientConfig = new MetaClientConfig();
			final ZKConfig zkConfig = new ZKConfig();
			zkConfig.zkConnect = zookeeper;
			metaClientConfig.setZkConfig(zkConfig);
			sessionFactory = new MetaMessageSessionFactory(metaClientConfig);

			// 如果是生产者,则传入值为真;如果是消费者,则传入值为假。可以共用同一个sessionFactory
			if (isProducer) {
				// 补偿机制，这里主要是针对如下两种情况
				// 发布者在onException中产生了异常，或者
				// result.isSuccess()返回值不成功，写会入到meaq.ini文件中对应的messagedir目录下
				// 然后由定时器每间隔30分钟扫描一次，扫到文件后，然后进行补偿机制进行重新由producer重新发布

				//Timer timer = new Timer();
				// 在2秒后执行此任务,每次间隔半小时扫描 D:\metaq\mmp\logs 目录下的异常文件，进行补偿机制发布消息
				// MesaageExceptionHandleTask（）实现方法很简单(由于字数限制不贴上去了)

				//timer.schedule(new MesaageExceptionHandleTask(), 2000,
						//1000 * 30 * 60);
			}

		} catch (Exception e) {

			log.error(e);
			throw new RuntimeException(e.getCause());

		}
	}

	public static MessageProducer getMessageProducer() {
		return getSessionFactory(true).createProducer();
	}
}