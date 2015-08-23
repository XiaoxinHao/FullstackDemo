package com.newidor.learn.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class WatchTest {

	public static void main(String[] args) throws Exception {
		// 创建一个与服务器的连接
		ZooKeeper zk = new ZooKeeper("192.168.1.109:2181", 6000, new Watcher() {
			// 监控所有被触发的事件
			public void process(WatchedEvent event) {
				System.out.println("defaultWatcher：被触发了" + event.getType()
						+ "事件！");
			}
		});

		/****/
		zk.exists("/testRootPath", new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				// 监控EventType.None、EventType.NodeCreated()、EventType.NodeDeleted、EventType.NodeDataChanged、EventType.NodeChildrenChanged
				System.out.println("zk.exists1：已经触发了" + event.getType() + "事件！");
			}
		});
		
		zk.exists("/testRootPath", new Watcher() {
			public void process(WatchedEvent event) {
				System.out.println("zk.exists2：被触发了" + event.getType()
						+ "事件！");
			}
		}); //设置true后，delete才能触发事件
		
		zk.exists("/testRootPath", true);
		

		// 创建一个目录节点
		zk.create("/testRootPath", "testRootData".getBytes(),
				Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT); //上述3个watcher均会被执行，但之后操作不会执行wather
		zk.setData("/testRootPath", "testRootData".getBytes(), -1);
		zk.setData("/testRootPath", "testRootData".getBytes(), -1);
		
		zk.setData("/testRootPath", "testRootData".getBytes(), -1);
		// 创建一个子目录节点
		zk.create("/testRootPath/testChildPathOne",
				"testChildDataOne".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		// 修改子目录节点数据
		zk.setData("/testRootPath/testChildPathOne",
				"modifyChildDataOne".getBytes(), -1);
		// 创建另外一个子目录节点
		zk.create("/testRootPath/testChildPathTwo",
				"testChildDataTwo".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		// 删除子目录节点
		zk.delete("/testRootPath/testChildPathTwo", -1);
		zk.delete("/testRootPath/testChildPathOne", -1);
		// 删除父目录节点
		zk.delete("/testRootPath", -1);
		// 关闭连接
		zk.close();
	}

}
