/**
 * LEKU APPLIANCE CHAINS.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.jellyleo.activiti.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jellyleo.activiti.entity.CommonVariable;
import com.jellyleo.activiti.util.BeanUtil;

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
	 * 功能描述:classpath部署流程
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
	 * 功能描述:zip部署流程
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/deploy/zip")
	@ResponseBody
	public String deployZip(HttpServletRequest request, HttpServletResponse response) {

		String name = request.getParameter("name");
		String zip = request.getParameter("zip");

		if (StringUtils.isEmpty(zip) || StringUtils.isEmpty(name)) {
			return "param error";
		}

		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("processes/" + zip);
			ZipInputStream zipInputStream = new ZipInputStream(in);
			Deployment deployment = repositoryService// 与流程定义和部署对象相关的Service
					.createDeployment()// 创建一个部署对象
					.name(name)// 添加部署名称
					.addZipInputStream(zipInputStream)// 完成zip文件的部署
					.deploy();// 完成部署
			System.out.println("部署ID：" + deployment.getId());
			System.out.println("部署名称:" + deployment.getName());
			System.out.println("*****************************************************************************");
		} catch (Exception e) {
			return "fail";
		}
		return "success";
	}

	/**
	 * 
	 * 功能描述:查询流程定义
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/definition/query")
	@ResponseBody
	public String processDefinition(HttpServletRequest request, HttpServletResponse response) {

		try {
			List<ProcessDefinition> list = repositoryService// 与流程定义和部署对象相关的Service
					.createProcessDefinitionQuery()// 创建一个流程定义查询
					/* 指定查询条件,where条件 */
					// .deploymentId(deploymentId)//使用部署对象ID查询
					// .processDefinitionId(processDefinitionId)//使用流程定义ID查询
					// .processDefinitionKey(processDefinitionKey)//使用流程定义的KEY查询
					// .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

					/* 排序 */
					.orderByProcessDefinitionVersion().asc()// 按照版本的升序排列
					// .orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列

					.list();// 返回一个集合列表，封装流程定义
			// .singleResult();//返回唯一结果集
			// .count();//返回结果集数量
			// .listPage(firstResult, maxResults)//分页查询

			if (list != null && list.size() > 0) {
				for (ProcessDefinition processDefinition : list) {
					System.out.println("流程定义ID:" + processDefinition.getId());// 流程定义的key+版本+随机生成数
					System.out.println("流程定义名称:" + processDefinition.getName());// 对应HelloWorld.bpmn文件中的name属性值
					System.out.println("流程定义的key:" + processDefinition.getKey());// 对应HelloWorld.bpmn文件中的id属性值
					System.out.println("流程定义的版本:" + processDefinition.getVersion());// 当流程定义的key值相同的情况下，版本升级，默认从1开始
					System.out.println("资源名称bpmn文件:" + processDefinition.getResourceName());
					System.out.println("资源名称png文件:" + processDefinition.getDiagramResourceName());
					System.out.println("部署对象ID:" + processDefinition.getDeploymentId());
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
	 * 功能描述:删除流程定义
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/deployment/delete")
	@ResponseBody
	public String deleteDeployment(HttpServletRequest request, HttpServletResponse response) {

		String deploymentId = request.getParameter("deployId");

		if (StringUtils.isEmpty(deploymentId)) {
			return "param error";
		}

		try {
//			// 不带级联的删除：只能删除没有启动的流程，如果流程启动，则抛出异常
//			repositoryService.deleteDeployment(deploymentId);
			// 能级联的删除：能删除启动的流程，会删除和当前规则相关的所有信息，正在执行的信息，也包括历史信息
			repositoryService.deleteDeployment(deploymentId, true);
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
		String variable = request.getParameter("variable");

		if (StringUtils.isEmpty(processDefinitionKey)) {
			return "param error";
		}

		try {
			Map<String, Object> variables = new HashMap<>();
			if (!StringUtils.isEmpty(variable)) {
				CommonVariable variablesEntity = JSON.parseObject(variable, CommonVariable.class);
				variables = BeanUtil.beanToMap(variablesEntity);
			}

			ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);

			System.out.println("流程实例ID:" + instance.getId());
			System.out.println("流程定义ID:" + instance.getProcessDefinitionId());
			System.out.println("*****************************************************************************");
		} catch (Exception e) {
			e.printStackTrace();
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
			runtimeService.deleteProcessInstance(processId, "流程已完毕");
			System.out.println("终止流程");
			System.out.println("*****************************************************************************");
		} catch (Exception e) {
			return "fail";
		}
		return "success";
	}

}
