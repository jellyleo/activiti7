/**
 * LEKU APPLIANCE CHAINS.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.jellyleo.activiti.service.impl;

import org.springframework.stereotype.Service;

import com.jellyleo.activiti.service.ResumeService;

/**
 * 功能描述:
 *
 * @author Jelly
 * @created 2019年11月20日
 * @version 1.0.0
 */
@Service("resumeService")
public class ResumeServiceImpl implements ResumeService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jellyleo.activiti.service.ResumeService#positiveNumber()
	 */
	@Override
	public void positiveNumber() {
		System.out.println("处理结果为:正数");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jellyleo.activiti.service.ResumeService#zeroNumber()
	 */
	@Override
	public void zeroNumber() {
		System.out.println("处理结果为:零");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jellyleo.activiti.service.ResumeService#negativeNumber()
	 */
	@Override
	public void negativeNumber() {
		System.out.println("处理结果为:负数");
	}

	@Override
	public void otherNumber() {
		System.out.println("处理结果为:非整数");
	}

}
