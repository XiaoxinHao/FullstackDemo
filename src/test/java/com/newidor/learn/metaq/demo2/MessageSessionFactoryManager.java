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

			// �����������,����ֵΪ��;�����������,����ֵΪ�١����Թ���ͬһ��sessionFactory
			if (isProducer) {
				// �������ƣ�������Ҫ����������������
				// ��������onException�в������쳣������
				// result.isSuccess()����ֵ���ɹ���д���뵽meaq.ini�ļ��ж�Ӧ��messagedirĿ¼��
				// Ȼ���ɶ�ʱ��ÿ���30����ɨ��һ�Σ�ɨ���ļ���Ȼ����в������ƽ���������producer���·���

				//Timer timer = new Timer();
				// ��2���ִ�д�����,ÿ�μ����Сʱɨ�� D:\metaq\mmp\logs Ŀ¼�µ��쳣�ļ������в������Ʒ�����Ϣ
				// MesaageExceptionHandleTask����ʵ�ַ����ܼ�(�����������Ʋ�����ȥ��)

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