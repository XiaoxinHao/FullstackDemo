package com.newidor.learn.metaq.demo3;
import java.util.concurrent.Executor;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;
import com.taobao.metamorphosis.client.consumer.MessageListener;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;

public class AsyncConsumer {
  public static void main(String[] args) throws Exception {
    final MetaClientConfig metaClientConfig = new MetaClientConfig();
    final ZKConfig zkConfig = new ZKConfig();
    //����zookeeper��ַ
    zkConfig.zkConnect = "192.168.1.109:2181";
    metaClientConfig.setZkConfig(zkConfig);
    // New session factory,ǿ�ҽ���ʹ�õ���
    MessageSessionFactory sessionFactory = new MetaMessageSessionFactory(metaClientConfig);
    // subscribed topic
    final String topic = "test";
    // consumer group
    final String group = "meta-example";
    /*
     * create consumer,ǿ�ҽ���ʹ�õ���
     * ͨ��createConsumer����������MessageConsumer��ע�⵽���Ǵ���һ��ConsumerConfig������
     * ���������ߵ����ö���ÿ����Ϣ�߶�������һ��ConsumerConfig���ö���
     * ��������ֻ������group���ԣ����������ߵķ������ơ�
     * Meta��Producer��Consumer��Broker������Ϊ��Ⱥ��
     * �����߿������һ����Ⱥ��ͬ����ͬһ��topic���������topic����Ϣ������һ���ĸ��ؾ�������͸���Ⱥ���һ̨������
     * ͬһ�������߼�Ⱥ����ӵ��ͬһ���������ƣ�Ҳ����ͬһ��group���������ｫ������������Ϊmeta-example
     */
    MessageConsumer consumer = sessionFactory.createConsumer(new ConsumerConfig(group));
    /*
     * subscribe topic
     * ������Ϣͨ��subscribe������������������������� 
     * 1) topic�����ĵ�����
     * 2) maxSize����Ϊmeta��һ��������������ȡ��ģ�ͣ���������涨ÿ����ȡ���������������λΪ�ֽڣ���������Ϊ1M��Ĭ�����Ϊ1M��
     * 3) MessageListener����Ϣ��������������Ϣ��Ϣ��
     */
    consumer.subscribe(topic, 1024 * 1024, new MessageListener() {

      public void recieveMessages(Message message) {
        System.out.println("Receive message " + new String(message.getData()));
      }


      public Executor getExecutor() {
        // Thread pool to process messages,maybe null.
        return null;
      }
    });
    // complete subscribe
    consumer.completeSubscribe();
  }
}
