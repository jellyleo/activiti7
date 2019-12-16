/**
 * Created by Jellyleo on 2019年12月16日
 * Copyright © 2019 jellyleo.com 
 * All rights reserved. 
 */
package com.jellyleo.activiti.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能描述:历史控制器
 *
 * @author Jelly
 * @created 2019年11月19日
 * @version 1.0.0
 */
@Controller
@RequestMapping("/history")
public class HistoricController extends BaseController {

	/**
	 * 
	 * 功能描述:查询历史流程实例
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/process")
	@ResponseBody
	public String historicProcess(HttpServletRequest request, HttpServletResponse response) {

		try {
			List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery()
					.orderByProcessInstanceStartTime().asc()// 排序
					.list();
			if (list != null && list.size() > 0) {
				for (HistoricProcessInstance hpi : list) {
					System.out.println("流程定义ID：" + hpi.getProcessDefinitionId());
					System.out.println("流程实例ID：" + hpi.getId());
					System.out.println("开始时间：" + hpi.getStartTime());
					System.out.println("结束时间：" + hpi.getEndTime());
					System.out.println("流程持续时间：" + hpi.getDurationInMillis());
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
	 * 功能描述:查询流程历史步骤
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/activity")
	@ResponseBody
	public String historicActivity(HttpServletRequest request, HttpServletResponse response) {

		String processInstanceId = request.getParameter("processInstanceId");

		if (StringUtils.isEmpty(processInstanceId)) {
			return "param error";
		}

		try {
			List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
					.processInstanceId(processInstanceId).list();
			if (list != null && list.size() > 0) {
				for (HistoricActivityInstance hai : list) {
					System.out.println(hai.getId());
					System.out.println("步骤ID：" + hai.getActivityId());
					System.out.println("步骤名称：" + hai.getActivityName());
					System.out.println("执行人：" + hai.getAssignee());
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
	 * 功能描述:查询流程执行任务记录
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/task")
	@ResponseBody
	public String historicTask(HttpServletRequest request, HttpServletResponse response) {

		String processInstanceId = request.getParameter("processInstanceId");

		if (StringUtils.isEmpty(processInstanceId)) {
			return "param error";
		}

		try {
			List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
					.processInstanceId(processInstanceId).list();
			if (list != null && list.size() > 0) {
				for (HistoricTaskInstance hti : list) {
					System.out.println("任务ID:" + hti.getId());
					System.out.println("任务名称:" + hti.getName());
					System.out.println("流程定义ID:" + hti.getProcessDefinitionId());
					System.out.println("办理人:" + hti.getAssignee());
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
	 * 功能描述:查询流程历史变量
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/variable")
	@ResponseBody
	public String historicVariable(HttpServletRequest request, HttpServletResponse response) {

		String processInstanceId = request.getParameter("processInstanceId");

		if (StringUtils.isEmpty(processInstanceId)) {
			return "param error";
		}

		try {
			List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(processInstanceId).list();
			if (list != null && list.size() > 0) {
				System.out.println("流程实例ID:" + processInstanceId);
				for (HistoricVariableInstance hvi : list) {
					System.out.println("任务ID:" + hvi.getTaskId());
					System.out.println("变量:[" + hvi.getVariableName() + "=" + hvi.getValue() + "]");
					System.out.println("*****************************************************************************");
				}
			}

		} catch (Exception e) {
			return "fail";
		}
		return "success";
	}

}
