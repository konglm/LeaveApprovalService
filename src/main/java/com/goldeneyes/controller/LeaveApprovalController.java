/*----------------------------------------------------------------
 *  Copyright (C) 2017山东金视野教育科技股份有限公司
 * 版权所有。 
 *
 * 文件名：
 * 文件功能描述：
 *
 * 
 * 创建标识：
 *
 * 修改标识：
 * 修改描述：
 *----------------------------------------------------------------*/

package com.goldeneyes.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.goldeneyes.service.LeaveApprovalService;
import com.goldeneyes.util.CommonTool;
import com.goldeneyes.vo.TaskVO;

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class LeaveApprovalController {
	@Resource
	LeaveApprovalService leaveApprovalService;
	
	/**
	 * 启动申请流程
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/launchApplication")
	public void launchApplication(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 返回参数用
		JSONObject jsonData = new JSONObject();

		String procId = "";
		try {
			procId = leaveApprovalService.launchApplication();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1014").toString());
			return;
		}
		jsonData.put("procId", procId);
		// 在这里输出，手机端就能拿到请求返回的值了
		CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
	}
	
	/**
	 * 获取任务列表
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getTasks")
	public void getTasks(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 接收参数用
		JSONObject jsonInput = new JSONObject();
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收app端发送来的json请求
		String requestStr = "";
		try {

			requestStr = (String) request.getAttribute("requestStr");// 从键requestStr中得到请求的字符串的值
			jsonInput = JSONObject.fromObject(requestStr);// 将String类型的变量requestStr转换成json对象
		} catch (Exception e) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}
		if (!jsonInput.has("userName") || !jsonInput.has("taskType")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;

		} else {
			String userName = "";
			int taskType = 0;
			try {
				/*
				 * 从jsonInput取对应各个键的值
				 */
				userName = jsonInput.getString("userName");
				taskType = Integer.parseInt(jsonInput.getString("taskType"));
			} catch (Exception e) {
				// TODO: handle exception
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;

			}
			
			List<TaskVO> tasks = new ArrayList<TaskVO>();
			try {
				tasks = leaveApprovalService.getTasks(userName, taskType);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1015").toString());
				return;
			}
			jsonData.put("tasks", tasks);
			// 在这里输出，手机端就能拿到请求返回的值了
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
		}
	}
	
	/**
	 * 完成任务
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/completeTask")
	public void completeTask(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 接收参数用
		JSONObject jsonInput = new JSONObject();
		// 返回参数用
		JSONObject jsonData = new JSONObject();
		// 接收app端发送来的json请求
		String requestStr = "";
		try {

			requestStr = (String) request.getAttribute("requestStr");// 从键requestStr中得到请求的字符串的值
			jsonInput = JSONObject.fromObject(requestStr);// 将String类型的变量requestStr转换成json对象
		} catch (Exception e) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;
		}
		if (!jsonInput.has("userName") || !jsonInput.has("taskId")) {
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1004").toString());
			return;

		} else {
			String userName = "";
			String taskId = "";
			try {
				/*
				 * 从jsonInput取对应各个键的值
				 */
				userName = jsonInput.getString("userName");
				taskId = jsonInput.getString("taskId");
			} catch (Exception e) {
				// TODO: handle exception
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1003").toString());
				return;

			}
			
			int success = 0;
			try {
				leaveApprovalService.completeTask(userName, taskId);
				success = 1;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "1016").toString());
				return;
			}
			jsonData.put("success", success);
			// 在这里输出，手机端就能拿到请求返回的值了
			CommonTool.outJsonString(response, CommonTool.outJson(jsonData, "0000").toString());
		}
	}

}
