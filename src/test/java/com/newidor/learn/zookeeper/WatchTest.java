package com.newidor.learn.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class WatchTest {

	public static void main(String[] args) throws Exception {
		// ����һ���������������
		ZooKeeper zk = new ZooKeeper("192.168.1.109:2181", 6000, new Watcher() {
			// ������б��������¼�
			public void process(WatchedEvent event) {
				System.out.println("defaultWatcher����������" + event.getType()
						+ "�¼���");
			}
		});

		/****/
		zk.exists("/testRootPath", new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				// ���EventType.None��EventType.NodeCreated()��EventType.NodeDeleted��EventType.NodeDataChanged��EventType.NodeChildrenChanged
				System.out.println("zk.exists1���Ѿ�������" + event.getType() + "�¼���");
			}
		});
		
		zk.exists("/testRootPath", new Watcher() {
			public void process(WatchedEvent event) {
				System.out.println("zk.exists2����������" + event.getType()
						+ "�¼���");
			}
		}); //����true��delete���ܴ����¼�
		
		zk.exists("/testRootPath", true);
		

		// ����һ��Ŀ¼�ڵ�
		zk.create("/testRootPath", "testRootData".getBytes(),
				Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT); //����3��watcher���ᱻִ�У���֮���������ִ��wather
		zk.setData("/testRootPath", "testRootData".getBytes(), -1);
		zk.setData("/testRootPath", "testRootData".getBytes(), -1);
		
		zk.setData("/testRootPath", "testRootData".getBytes(), -1);
		// ����һ����Ŀ¼�ڵ�
		zk.create("/testRootPath/testChildPathOne",
				"testChildDataOne".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		// �޸���Ŀ¼�ڵ�����
		zk.setData("/testRootPath/testChildPathOne",
				"modifyChildDataOne".getBytes(), -1);
		// ��������һ����Ŀ¼�ڵ�
		zk.create("/testRootPath/testChildPathTwo",
				"testChildDataTwo".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		// ɾ����Ŀ¼�ڵ�
		zk.delete("/testRootPath/testChildPathTwo", -1);
		zk.delete("/testRootPath/testChildPathOne", -1);
		// ɾ����Ŀ¼�ڵ�
		zk.delete("/testRootPath", -1);
		// �ر�����
		zk.close();
	}

}
