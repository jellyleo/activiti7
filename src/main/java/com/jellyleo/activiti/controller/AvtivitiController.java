/**
 * LEKU APPLIANCE CHAINS.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.jellyleo.activiti.controller;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能描述:请假控制器
 *
 * @author Jelly
 * @created 2019年11月19日
 * @version 1.0.0
 */
@Controller
public class AvtivitiController extends BaseController {

	/**
	 * 
	 * 功能描述:部署流程
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/deploy")
	@ResponseBody
	public String deploy(HttpServletRequest request, HttpServletResponse response) {

		String name = request.getParameter("name");
		String resource = request.getParameter("resource");

		if (StringUtils.isEmpty(resource) || StringUtils.isEmpty(name)) {
			return "param error";
		}

		try {
			// 创建一个部署对象
			Deployment deploy = repositoryService.createDeployment().name(name)
					.addClasspathResource("processes/" + resource).deploy();
			System.out.println("部署成功:" + deploy.getId());
			System.out.println("*****************************************************************************");
		} catch (Exception e) {
			return "fail";
		}
		return "success";
	}

	/**
	 * 
	 * 功能描述:删除流程
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response) {

		String deploymentId = request.getParameter("deployId");

		if (StringUtils.isEmpty(deploymentId)) {
			return "param error";
		}

		try {
			repositoryService.deleteDeployment(deploymentId);

			System.out.println("删除成功:" + deploymentId);
			System.out.println("*****************************************************************************");
		} catch (Exception e) {
			return "fail";
		}
		return "success";
	}

	/**
	 * 
	 * 功能描述:启动流程
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/start")
	@ResponseBody
	public String start(HttpServletRequest request, HttpServletResponse response) {

		String processDefinitionKey = request.getParameter("processId");

		if (StringUtils.isEmpty(processDefinitionKey)) {
			return "param error";
		}

		String key = request.getParameter("key");
		String value = request.getParameter("value");

		try {
			HashMap<String, Object> variables = new HashMap<>();
			variables.put(key, value);

			ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);

			System.out.println("流程实例ID:" + instance.getId());
			System.out.println("流程定义ID:" + instance.getProcessDefinitionId());
			System.out.println("*****************************************************************************");
		} catch (Exception e) {
			return "fail";
		}
		return "success";
	}

	/**
	 * 
	 * 功能描述:查询任务
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/query")
	@ResponseBody
	public String taskQuery(HttpServletRequest request, HttpServletResponse response) {

		try {
			List<Task> list = taskService.createTaskQuery()// 创建任务查询对象
					.list();
			if (list != null && list.size() > 0) {
				for (Task task : list) {
					System.out.println("任务ID:" + task.getId());
					System.out.println("任务名称:" + task.getName());
					System.out.println("任务的创建时间:" + task.getCreateTime());
					System.out.println("任务的办理人:" + task.getAssignee());
					System.out.println("流程实例ID：" + task.getProcessInstanceId());
					System.out.println("执行对象ID:" + task.getExecutionId());
					System.out.println("流程定义ID:" + task.getProcessDefinitionId());
					System.out.println("*****************************************************************************");
				}
			}
		} catch (Exception e) {
			return "fail";
		}
		return "success";
	}

	/**
	 * 
	 * 功能描述:查询当前任务
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public String getTask(HttpServletRequest request, HttpServletResponse response) {

		String processInstanceId = request.getParameter("processInstanceId");

		if (StringUtils.isEmpty(processInstanceId)) {
			return "param error";
		}

		try {
			Task task = taskService.createTaskQuery()// 创建查询对象
					.processInstanceId(processInstanceId)// 通过流程实例id来查询当前任务
					.singleResult();// 获取单个查询结果
			if (task == null) {
				System.out.println("流程已结束");
				System.out.println("流程实例ID:" + processInstanceId);
				System.out.println("*****************************************************************************");
				return "success";
			}
			System.out.println("任务ID:" + task.getId());
			System.out.println("任务名称:" + task.getName());
			System.out.println("任务的创建时间:" + task.getCreateTime());
			System.out.println("任务的办理人:" + task.getAssignee());
			System.out.println("流程实例ID：" + task.getProcessInstanceId());
			System.out.println("执行对象ID:" + task.getExecutionId());
			System.out.println("流程定义ID:" + task.getProcessDefinitionId());
			System.out.println("*****************************************************************************");
		} catch (Exception e) {
			return "fail";
		}
		return "success";
	}

	/**
	 * 
	 * 功能描述:查询进行中任务
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/getruntask")
	@ResponseBody
	public String getRun(HttpServletRequest request, HttpServletResponse response) {

		String processInstanceId = request.getParameter("processInstanceId");

		if (StringUtils.isEmpty(processInstanceId)) {
			return "param error";
		}

		try {
			ProcessInstance process = runtimeService.createProcessInstanceQuery()// 获取查询对象
					.processInstanceId(processInstanceId)// 根据id查询流程实例
					.singleResult();// 获取查询结果,如果为空,说明这个流程已经执行完毕,否则,获取任务并执行
			if (process == null) {
				System.out.println("流程已结束");
				System.out.println("流程实例ID:" + processInstanceId);
				System.out.println("*****************************************************************************");
				return "success";
			}
		} catch (Exception e) {
			return "fail";
		}
		return "success";
	}

	/**
	 * 
	 * 功能描述:完成任务
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/complete")
	@ResponseBody
	public String complete(HttpServletRequest request, HttpServletResponse response) {

		String taskId = request.getParameter("taskid");

		if (StringUtils.isEmpty(taskId)) {
			return "param error";
		}

		String key = request.getParameter("key");
		String value = request.getParameter("value");
		try {

			taskService.setVariable(taskId, key, value);
			taskService.complete(taskId);
			System.out.println("任务完成");
			System.out.println("任务ID:" + taskId);
			System.out.println("任务处理结果:" + key + "=" + value);
			System.out.println("*****************************************************************************");
		} catch (Exception e) {
			return "fail";
		}
		return "success";
	}

	/**
	 * 
	 * 功能描述:完成任务
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/deleteprocess")
	@ResponseBody
	public String deleteProcess(HttpServletRequest request, HttpServletResponse response) {

		String processId = request.getParameter("processId");

		if (StringUtils.isEmpty(processId)) {
			return "param error";
		}

		try {
			runtimeService.deleteProcessInstance(processId, "结束");
			System.out.println("终止流程");
			System.out.println("*****************************************************************************");
		} catch (Exception e) {
			return "fail";
		}
		return "success";
	}

}
