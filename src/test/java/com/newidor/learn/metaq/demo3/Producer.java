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
        //����zookeeper��ַ
        zkConfig.zkConnect = "192.168.1.109:2181";
        metaClientConfig.setZkConfig(zkConfig);
        // New session factory,ǿ�ҽ���ʹ�õ���
        MessageSessionFactory sessionFactory = new MetaMessageSessionFactory(metaClientConfig);
        /*
         *  create producer,ǿ�ҽ���ʹ�õ���
         *  ��Ϣ�����ߵĽӿ���MessageProducer�������ͨ������������Ϣ
         */
        MessageProducer producer = sessionFactory.createProducer();
        // publish topic
        final String topic = "test";
        /*
         * ��һ���ڷ�����Ϣǰ�Ǳ���ģ�����뷢���㽫Ҫ������Ϣ��topic
         * ����Ϊ���ûỰ��������ȥ���ҽ�����Щtopic��meta��������ַ����ʼ������
         * ����������ÿ��topicֻ��Ҫ��һ�Σ���ε�����Ӱ��
         */
        producer.publish(topic);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        while ((line = reader.readLine()) != null) 
        {
            /*
             * send message
             * ��Meta�ÿ����Ϣ������Message���ʵ����Message��ʾһ����Ϣ������������ô�������ԣ�
             * 1) id: Long�͵���Ϣid,��Ϣ��Ψһid��ϵͳ�Զ��������û��޷����ã��ڷ��ͳɹ����ɷ��������أ�����ʧ����Ϊ0��
             * 2) topic: ��Ϣ�����⣬�����߶��ĸ����⼴�ɽ��շ��͵��������µ���Ϣ��������ͨ��ָ��������topic���ҵ���Ҫ���ӵķ�������ַ�����롣
             * 3) data: ��Ϣ����Ч�غɣ����������ݣ�Ҳ������Ϣ���ݣ�meta��Զ�����޸���Ϣ���ݣ��㷢�ͳ�ȥ��ʲô���ӣ����յ�����ʲô���ӡ���Ϣ����ͨ��������1M���ڣ��ҵĽ�������ò�Ҫ���ͳ����ϰ�K����Ϣ�����롣�����Ƿ�ѹ��Ҳ��ȫȡ�����û���
             * 4) attribute: ��Ϣ���ԣ�һ���ַ�������ѡ�������߿�������Ϣ�������������߹��ˡ�
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
