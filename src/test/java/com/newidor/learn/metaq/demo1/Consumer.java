package com.newidor.learn.metaq.demo1;

import java.util.concurrent.Executor;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;
import com.taobao.metamorphosis.client.consumer.MessageListener;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;

public class Consumer {

	public static void main(String[] args) throws Exception {
		final MetaClientConfig metaClientConfig = new MetaClientConfig();
		final ZKConfig zkConfig = new ZKConfig();
		zkConfig.zkConnect = "192.168.1.109:2181";
		metaClientConfig.setZkConfig(zkConfig);
		MessageSessionFactory sessionFactory = new MetaMessageSessionFactory(
				metaClientConfig);
		final String topic = "test";
		final String group = "meta-example";

		MessageConsumer consumer = sessionFactory
				.createConsumer(new ConsumerConfig(group));

		consumer.subscribe(topic, 1024 * 100, new MessageListener() {
			public void recieveMessages(Message message) {
				System.out.println("Receive message "
						+ new String(message.getData()));
				// System.out.println("·ÖÇø = "+message.getPartition().getPartition());
				// System.out.println("broker = "+message.getPartition().getBrokerId());
			}

			public Executor getExecutor() {
				return null;
			}
		});

		consumer.completeSubscribe();
	}

}
