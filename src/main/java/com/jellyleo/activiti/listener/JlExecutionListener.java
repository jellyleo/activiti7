/**
 * LEKU APPLIANCE CHAINS.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.jellyleo.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 功能描述:
 *
 * @author Jelly
 * @created 2019年11月21日
 * @version 1.0.0
 */
public class JlExecutionListener implements ExecutionListener {

	String EVENTNAME_START = "start";
	String EVENTNAME_END = "end";
	String EVENTNAME_TAKE = "take";

	/**
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.activiti.engine.delegate.ExecutionListener#notify(org.activiti.engine.
	 * delegate.DelegateExecution)
	 */
	@Override
	public void notify(DelegateExecution execution) {
		String eventName = execution.getEventName();
		if (EVENTNAME_START.equals(eventName)) {
			System.out.println("start=========流程启动");
		} else if (EVENTNAME_END.equals(eventName)) {
			System.out.println("end=========流程结束");
		} else if (EVENTNAME_TAKE.equals(eventName))
			System.out.println("take ======经过连线");
	}

}
