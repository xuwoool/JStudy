package com.jstudy.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程阻断,用于代码执行暂停或继续
 * @author xuxb
 *
 */
public class BlockUtil implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(BlockUtil.class);
	
	@Override
	public void run() {
		System.out.println("用于代码执行暂停或继续");
	}
	/**
	 * 暂停
	 * @throws InterruptedException
	 */
	public void lock(){
        synchronized (this) {
        	try {
				wait();
			} catch (InterruptedException e) {
				log.error(e.getMessage());
				throw new RuntimeException(e);
			}
        }
    }
	/**
	 * 继续
	 */
    public final synchronized void unlock() {
        notify();
    }
}
