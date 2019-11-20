package com.jellyleo.activiti.controller;

import org.activiti.api.process.runtime.ProcessAdminRuntime;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.task.runtime.TaskAdminRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 
 * 功能描述:activiti控制器基类
 *
 * @author Jelly
 * @created 2019年11月19日
 * @version 1.0.0
 */
@Controller
public class BaseController {
	@Autowired
	public TaskService taskService;
	@Autowired
	public RuntimeService runtimeService;
	@Autowired
	public HistoryService historyService;
	@Autowired
	public RepositoryService repositoryService;

	/**
	 * ProcessRuntime类内部最终调用repositoryService和runtimeService相关API。 需要ACTIVITI_USER权限
	 */
	@Autowired
	public ProcessRuntime processRuntime;

	/**
	 * ProcessRuntime类内部最终调用repositoryService和runtimeService相关API。
	 * 需要ACTIVITI_ADMIN权限
	 */
	@Autowired
	public ProcessAdminRuntime processAdminRuntime;

	/**
	 * 类内部调用taskService 需要ACTIVITI_USER权限
	 */
	@Autowired
	public TaskRuntime taskRuntime;

	/**
	 * 类内部调用taskService 需要ACTIVITI_ADMIN权限
	 */
	@Autowired
	public TaskAdminRuntime taskAdminRuntime;

}
