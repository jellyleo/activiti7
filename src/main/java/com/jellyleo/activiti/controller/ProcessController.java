/**
 * LEKU APPLIANCE CHAINS.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.jellyleo.activiti.controller;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能描述:流程控制器
 *
 * @author Jelly
 * @created 2019年11月19日
 * @version 1.0.0
 */
@Controller
@RequestMapping("/process")
public class ProcessController extends BaseController {

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
	@RequestMapping(value = "/deploy/delete")
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
	 * 功能描述:删除流程
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/delete")
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
