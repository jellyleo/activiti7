package com.jellyleo.activiti.controller;

import java.util.List;

import org.activiti.api.process.runtime.ProcessAdminRuntime;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.task.runtime.TaskAdminRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 
 * 功能描述:activiti基类
 *
 * @author Jelly
 * @created 2019年11月19日
 * @version 1.0.0
 */
@Controller
public class BaseController {
	@Autowired
	TaskService taskService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	HistoryService historyService;
	@Autowired
	RepositoryService repositoryService;

	/**
	 * ProcessRuntime类内部最终调用repositoryService和runtimeService相关API。 需要ACTIVITI_USER权限
	 */
	@Autowired
	ProcessRuntime processRuntime;

	/**
	 * ProcessRuntime类内部最终调用repositoryService和runtimeService相关API。
	 * 需要ACTIVITI_ADMIN权限
	 */
	@Autowired
	ProcessAdminRuntime processAdminRuntime;

	/**
	 * 类内部调用taskService 需要ACTIVITI_USER权限
	 */
	@Autowired
	TaskRuntime taskRuntime;

	/**
	 * 类内部调用taskService 需要ACTIVITI_ADMIN权限
	 */
	@Autowired
	TaskAdminRuntime taskAdminRuntime;

	/**
	 * 
	 * 功能描述:Acticiti7 formService替代方法
	 *
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	List<FormProperty> getFormPropertiesOfOldFormService(String processDefinitionId, String taskDefinitionKey) {
		// 在Acticiti7中，删除了FormService接口，可用以下方法代替
		UserTask userTask = (UserTask) repositoryService.getBpmnModel(processDefinitionId)
				.getFlowElement(taskDefinitionKey);
		return userTask.getFormProperties();
	}
}
