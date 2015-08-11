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
    //设置zookeeper地址
    zkConfig.zkConnect = "192.168.1.109:2181";
    metaClientConfig.setZkConfig(zkConfig);
    // New session factory,强烈建议使用单例
    MessageSessionFactory sessionFactory = new MetaMessageSessionFactory(metaClientConfig);
    // subscribed topic
    final String topic = "test";
    // consumer group
    final String group = "meta-example";
    /*
     * create consumer,强烈建议使用单例
     * 通过createConsumer方法来创建MessageConsumer，注意到我们传入一个ConsumerConfig参数，
     * 这是消费者的配置对象。每个消息者都必须有一个ConsumerConfig配置对象，
     * 我们这里只设置了group属性，这是消费者的分组名称。
     * Meta的Producer、Consumer和Broker都可以为集群。
     * 消费者可以组成一个集群共同消费同一个topic，发往这个topic的消息将按照一定的负载均衡规则发送给集群里的一台机器。
     * 同一个消费者集群必须拥有同一个分组名称，也就是同一个group。我们这里将分组名称设置为meta-example
     */
    MessageConsumer consumer = sessionFactory.createConsumer(new ConsumerConfig(group));
    /*
     * subscribe topic
     * 订阅消息通过subscribe方法，这个方法接受三个参数 
     * 1) topic，订阅的主题
     * 2) maxSize，因为meta是一个消费者主动拉取的模型，这个参数规定每次拉取的最大数据量，单位为字节，这里设置为1M，默认最大为1M。
     * 3) MessageListener，消息监听器，负责消息消息。
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
