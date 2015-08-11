package com.newidor.learn.metaq.demo3;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.SendResult;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;

public class Producer {
    public static void main(String[] args) throws Exception {
        final MetaClientConfig metaClientConfig = new MetaClientConfig();
        final ZKConfig zkConfig = new ZKConfig();
        //设置zookeeper地址
        zkConfig.zkConnect = "192.168.1.109:2181";
        metaClientConfig.setZkConfig(zkConfig);
        // New session factory,强烈建议使用单例
        MessageSessionFactory sessionFactory = new MetaMessageSessionFactory(metaClientConfig);
        /*
         *  create producer,强烈建议使用单例
         *  消息生产者的接口是MessageProducer，你可以通过它来发送消息
         */
        MessageProducer producer = sessionFactory.createProducer();
        // publish topic
        final String topic = "test";
        /*
         * 这一步在发送消息前是必须的，你必须发布你将要发送消息的topic
         * 这是为了让会话工厂帮你去查找接收这些topic的meta服务器地址并初始化连接
         * 这个步骤针对每个topic只需要做一次，多次调用无影响
         */
        producer.publish(topic);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        while ((line = reader.readLine()) != null) 
        {
            /*
             * send message
             * 在Meta里，每个消息对象都是Message类的实例，Message表示一个消息对象，它包含这么几个属性：
             * 1) id: Long型的消息id,消息的唯一id，系统自动产生，用户无法设置，在发送成功后由服务器返回，发送失败则为0。
             * 2) topic: 消息的主题，订阅者订阅该主题即可接收发送到该主题下的消息，生产者通过指定发布的topic查找到需要连接的服务器地址，必须。
             * 3) data: 消息的有效载荷，二进制数据，也就是消息内容，meta永远不会修改消息内容，你发送出去是什么样子，接收到就是什么样子。消息内容通常限制在1M以内，我的建议是最好不要发送超过上百K的消息，必须。数据是否压缩也完全取决于用户。
             * 4) attribute: 消息属性，一个字符串，可选。发送者可设置消息属性来让消费者过滤。
             */
            SendResult sendResult = producer.sendMessage(new Message(topic, line.getBytes()));
            // check result
            if (!sendResult.isSuccess()) 
            {
                System.err.println("Send message failed,error message:" + sendResult.getErrorMessage());
            }
            else {
                System.out.println("Send message successfully,sent to " + sendResult.getPartition());
            }
        }
    }

}
