package com.jellyleo.activiti.listener;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.Task;

/**
 * 
 * 功能描述:会签监听
 *
 * @author Jelly
 * @created 2019年11月22日
 * @version 1.0.0
 */
public class JlSignListener implements TaskListener {

	/**
	 * 
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
		System.out.println("会签监听");
		// 获取流程id
		String executionId = delegateTask.getExecutionId();

		// 创建ProcessEngine
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		RuntimeService runtimeService = engine.getRuntimeService();
		TaskService taskService = engine.getTaskService();

		// 本次审批结果
		Integer pass = (Integer) runtimeService.getVariable(executionId, "pass");
		// 审批通过实例数
		Integer positive = (Integer) runtimeService.getVariable(executionId, "nrOfPositiveInstances");
		if (pass == 1) {
			positive = (positive == null) ? 1 : positive + 1;
		}
		// 审批完成实例数（不包含此实例，因为监听触发在更新nrOfCompletedInstances之前）
		Integer complete = (Integer) runtimeService.getVariable(executionId, "nrOfCompletedInstances");
		// 所有实例数
		Integer all = (Integer) runtimeService.getVariable(executionId, "nrOfInstances");

		// 全部实例审批完成
		if ((complete + 1) == all) {
			int result = (positive != null && positive == (int) all) ? 1 : 0;
			// 会签结束，设置参数result，下个任务为申请
			runtimeService.setVariable(executionId, "result", result);
			// 下个任务
			String processInstanceId = delegateTask.getProcessInstanceId();
			Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
			System.out.println("下个任务编码：" + task.getId() + "，下个任务名称：" + task.getName());
		}

		// 更新通过实例数
		runtimeService.setVariable(executionId, "nrOfPositiveInstances", positive);
	}

}