/**
 * Created by Jellyleo on 2019年12月16日
 * Copyright © 2019 jellyleo.com 
 * All rights reserved. 
 */
package com.jellyleo.activiti.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jellyleo.activiti.entity.CommonVariable;
import com.jellyleo.activiti.util.BeanUtil;

/**
 * 功能描述:任务控制器
 *
 * @author Jelly
 * @created 2019年11月19日
 * @version 1.0.0
 */
@Controller
@RequestMapping("/task")
public class TaskController extends BaseController {

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
	 * 功能描述:查询当前全部任务
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public String listTask(HttpServletRequest request, HttpServletResponse response) {

		String processInstanceId = request.getParameter("processInstanceId");

		if (StringUtils.isEmpty(processInstanceId)) {
			return "param error";
		}

		try {
			List<Task> taskList = taskService.createTaskQuery()// 创建查询对象
					.processInstanceId(processInstanceId)// 通过流程实例id来查询当前任务
					.list();// 获取单个查询结果
			if (CollectionUtils.isEmpty(taskList)) {
				System.out.println("流程已结束");
				System.out.println("流程实例ID:" + processInstanceId);
				System.out.println("*****************************************************************************");
				return "success";
			}

			taskList.forEach(task -> {
				System.out.println("任务ID:" + task.getId());
				System.out.println("任务名称:" + task.getName());
				System.out.println("任务的创建时间:" + task.getCreateTime());
				System.out.println("任务的办理人:" + task.getAssignee());
				System.out.println("流程实例ID：" + task.getProcessInstanceId());
				System.out.println("执行对象ID:" + task.getExecutionId());
				System.out.println("流程定义ID:" + task.getProcessDefinitionId());
				System.out.println("*****************************************************************************");
			});

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
	@RequestMapping(value = "/get/run")
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
		String variable = request.getParameter("variable");

		if (StringUtils.isEmpty(taskId)) {
			return "param error";
		}

		try {
			Map<String, Object> variables = new HashMap<>();
			if (!StringUtils.isEmpty(variable)) {
				CommonVariable variablesEntity = JSON.parseObject(variable, CommonVariable.class);
				variables = BeanUtil.beanToMap(variablesEntity);
			}
//			// 设置流程参数（单）
//			taskService.setVariable(taskId, key, value);
			// 设置流程参数（多）
			taskService.setVariables(taskId, variables);

			// 若是委托任务，请先解决委托任务
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			if (DelegationState.PENDING.equals(task.getDelegationState())) {
				return "resolve delegation first";
			}
			taskService.complete(taskId);
			System.out.println("任务完成");
			System.out.println("任务ID:" + taskId);
			System.out.println("任务处理结果:" + variables);
			System.out.println("*****************************************************************************");
		} catch (Exception e) {
			return "fail";
		}
		return "success";
	}

	/**
	 * 
	 * 功能描述:任务委托
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/assignee")
	@ResponseBody
	public String assignee(HttpServletRequest request, HttpServletResponse response) {

		String taskId = request.getParameter("taskid");
		String assignee = request.getParameter("assignee");

		if (StringUtils.isEmpty(taskId) || StringUtils.isEmpty(assignee)) {
			return "param error";
		}

		try {
			taskService.delegateTask(taskId, assignee);
			System.out.println("任务已委托给：" + assignee);
			System.out.println("*****************************************************************************");
		} catch (Exception e) {
			return "fail";
		}
		return "success";
	}

	/**
	 * 
	 * 功能描述:解决委托任务
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/resolve")
	@ResponseBody
	public String resolve(HttpServletRequest request, HttpServletResponse response) {

		String taskId = request.getParameter("taskid");
		String variable = request.getParameter("variable");

		if (StringUtils.isEmpty(taskId)) {
			return "param error";
		}

		try {
			Map<String, Object> variables = new HashMap<>();
			if (!StringUtils.isEmpty(variable)) {
				CommonVariable variablesEntity = JSON.parseObject(variable, CommonVariable.class);
				variables = BeanUtil.beanToMap(variablesEntity);
			}
//			// 设置流程参数（单）
//			taskService.setVariable(taskId, key, value);
			// 设置流程参数（多）
			taskService.setVariables(taskId, variables);

			// 根据taskId提取任务
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			if (task.getOwner() != null && !task.getOwner().equals("null")) {
				DelegationState delegationState = task.getDelegationState();
				if (delegationState.equals(DelegationState.RESOLVED)) {
					System.out.println("此委托任务已是完结状态");
				} else if (delegationState.equals(DelegationState.PENDING)) {
					// 如果是委托任务需要做处理
					taskService.resolveTask(taskId, variables);
				} else {
					System.out.println("此任务不是委托任务");
				}
			}
			System.out.println("委托任务处理完毕");
			System.out.println("*****************************************************************************");
		} catch (Exception e) {
			return "fail";
		}
		return "success";
	}

}
