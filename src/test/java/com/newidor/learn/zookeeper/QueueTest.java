package com.newidor.learn.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class QueueTest {

	public static void doOne() throws Exception {
		String host1 = "192.168.1.109:2181";
		ZooKeeper zk = connection(host1);
		initQueue(zk);
		joinQueue(zk, 1);
		joinQueue(zk, 2);
		joinQueue(zk, 3);

		// zk.close(); //close之后，临时节点会被删除
	}

	public static ZooKeeper connection(String host) throws IOException {
		// 如果没有执行zk.close()而直接关闭进程，60s后，才能检测到临时节点被删除
		ZooKeeper zk = new ZooKeeper(host, 60000, new Watcher() {
			// 监听/queue/start创建的事件
			public void process(WatchedEvent event) {
				if (event.getPath() != null
						&& event.getPath().equals("/queue/start")
						&& event.getType() == Event.EventType.NodeCreated) {
					System.out.println("Queue has Completed.Finish testing!!!");
				}
			}
		});
		return zk;
	}

	public static void initQueue(ZooKeeper zk) throws KeeperException,
			InterruptedException {
		System.out.println("WATCH => /queue/start");
		//zk.exists("/queue/start", true);

		if (zk.exists("/queue", false) == null) {
			System.out.println("create /queue task-queue");
			zk.create("/queue", "task-queue".getBytes(), Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT);
		} else {
			System.out.println("/queue is exist!");
		}
	}

	public static void joinQueue(ZooKeeper zk, int x) throws KeeperException,
			InterruptedException {
		System.out.println("create /queue/x" + x + " x" + x);
		zk.create("/queue/x" + x, ("x" + x).getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL_SEQUENTIAL);
		isCompleted(zk);
	}

	public static void isCompleted(ZooKeeper zk) throws KeeperException,
			InterruptedException {
		int size = 3;
		int length = zk.getChildren("/queue", true).size();

		System.out.println("Queue Complete:" + length + "/" + size);

		if (length >= size) {
			System.out.println("create /queue/start start");
			zk.create("/queue/start", "start".getBytes(), Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL);
		}
	}

	public static void main(String[] args) throws Exception {
		doOne();
		Thread.sleep(50000);
	}

}
