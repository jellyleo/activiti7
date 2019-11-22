/**
 * LEKU APPLIANCE CHAINS.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.jellyleo.activiti.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述:
 *
 * @author Jelly
 * @created 2019年11月22日
 * @version 1.0.0
 */
@Data
@Builder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
public class CommonVariable {

	private List<String> signList;
	
	private Integer day;
	
	private Integer amount;
	
	private Integer val;
	
	private Integer pay;
	
	private Integer order;
	
	private Integer sendout;

	private Integer pass;
}
