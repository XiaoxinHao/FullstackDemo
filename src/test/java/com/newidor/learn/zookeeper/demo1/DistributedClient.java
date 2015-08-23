package com.newidor.learn.zookeeper.demo1;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;


/**
 * ת���ԣ�http://coolxing.iteye.com/blog/1871630
 * @author Administrator
 *
 */
public class DistributedClient {
    // ��ʱʱ��
    private static final int SESSION_TIMEOUT = 5000;
    // zookeeper server�б�
    private String hosts = "192.168.1.109:2181,192.168.1.109:2182,192.168.1.109:2183";
    private String locks = "locks";
    private String subNode = "sub";

    private ZooKeeper zk;
    // ��ǰclient�������ӽڵ�
    private String thisPath;
    // ��ǰclient�ȴ����ӽڵ�
    private String waitPath;

    private CountDownLatch latch = new CountDownLatch(1);

    /**
     * ����zookeeper
     */
    public void connectZookeeper() throws Exception {
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, new Watcher() {
            public void process(WatchedEvent event) {
                try {
                    // ���ӽ���ʱ, ��latch, ����wait�ڸ�latch�ϵ��߳�
                    if (event.getState() == KeeperState.SyncConnected) {
                        latch.countDown();
                    }

                    // ������waitPath��ɾ���¼�
                    if (event.getType() == EventType.NodeDeleted && event.getPath().equals(waitPath)) {
                        doSomething();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // �ȴ����ӽ���
        latch.await();

        // �����ӽڵ�
        thisPath = zk.create("/" + locks + "/" + subNode, null, Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);

        // waitһС��, �ý��������һЩ
        Thread.sleep(10);

        // ע��, û�б�Ҫ����"/locks"���ӽڵ�ı仯���
        List<String> childrenNodes = zk.getChildren("/" + locks, false);

        // �б���ֻ��һ���ӽڵ�, �ǿ϶�����thisPath, ˵��client�����
        if (childrenNodes.size() == 1) {
            doSomething();
        } else {
            String thisNode = thisPath.substring(("/" + locks + "/").length());
            // ����
            Collections.sort(childrenNodes);
            int index = childrenNodes.indexOf(thisNode);
            if (index == -1) {
                // never happened
            } else if (index == 0) {
                // inddx == 0, ˵��thisNode���б�����С, ��ǰclient�����
                doSomething();
            } else {
                // ���������thisPathǰ1λ�Ľڵ�
                this.waitPath = "/" + locks + "/" + childrenNodes.get(index - 1);
                // ��waitPath��ע�������, ��waitPath��ɾ��ʱ, zookeeper��ص���������process����
                zk.getData(waitPath, true, new Stat());
            }
        }
    }

    private void doSomething() throws Exception {
        try {
            System.out.println("gain lock: " + thisPath);
            Thread.sleep(2000);
            // do something
        } finally {
            System.out.println("finished: " + thisPath);
            // ��thisPathɾ��, ����thisPath��client�����֪ͨ
            // �൱���ͷ���
            zk.delete(this.thisPath, -1);
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    try {
                        DistributedClient dl = new DistributedClient();
                        dl.connectZookeeper();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        Thread.sleep(Long.MAX_VALUE);
    }
}
