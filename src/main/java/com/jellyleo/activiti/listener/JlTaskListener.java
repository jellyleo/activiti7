/**
 * Created by Jellyleo on 2019年12月16日
 * Copyright © 2019 jellyleo.com 
 * All rights reserved. 
 */
package com.jellyleo.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 功能描述:
 *
 * @author Jelly
 * @created 2019年11月21日
 * @version 1.0.0
 */
public class JlTaskListener implements TaskListener {

	String EVENTNAME_CREATE = "create";
	String EVENTNAME_ASSIGNMENT = "assignment";
	String EVENTNAME_COMPLETE = "complete";
	String EVENTNAME_DELETE = "delete";

	/**
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.activiti.engine.delegate.TaskListener#notify(org.activiti.engine.delegate
	 * .DelegateTask)
	 */
	@Override
	public void notify(DelegateTask delegateTask) {
		String eventName = delegateTask.getEventName();
		if (EVENTNAME_CREATE.endsWith(eventName))
			System.out.println("create===任务创建");
		else if (EVENTNAME_ASSIGNMENT.endsWith(eventName))
			System.out.println("assignment===任务分配");
		else if (EVENTNAME_COMPLETE.endsWith(eventName))
			System.out.println("complete===任务完成");
		else if (EVENTNAME_DELETE.endsWith(eventName))
			System.out.println("delete===任务删除");
	}

}
