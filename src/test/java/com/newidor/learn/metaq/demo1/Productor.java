package com.newidor.learn.metaq.demo1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.SendResult;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;

public class Productor {

	public static void main(String[] args) throws Exception {
			final MetaClientConfig metaClientConfig = new MetaClientConfig();
				final ZKConfig zkConfig = new ZKConfig();
				zkConfig.zkConnect = "192.168.1.109:2181";
			metaClientConfig.setZkConfig(zkConfig);
			
		MessageSessionFactory sessionFactory = new MetaMessageSessionFactory(
				metaClientConfig);
			MessageProducer producer = sessionFactory.createProducer();
			final String topic = "test";
			producer.publish(topic);
			
			
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String line = "ÍøÂçÊ±¿Õ(°¢ÌÃ)";
		while ((line = reader.readLine()) != null) {
			// send message
			SendResult sendResult = producer.sendMessage(new Message(topic,
					line.getBytes()));
			// check result
			if (!sendResult.isSuccess()) {
				System.err.println("Send message failed,error message:"
						+ sendResult.getErrorMessage());
			} else {
				System.out.println("Send message successfully,sent to "
						+ sendResult.getPartition());
				// System.out.println("·ÖÇø  ="+sendResult.getPartition().getPartition());
				// System.out.println("broker  ="+sendResult.getPartition().getBrokerId());
			}
		}

	}
}
