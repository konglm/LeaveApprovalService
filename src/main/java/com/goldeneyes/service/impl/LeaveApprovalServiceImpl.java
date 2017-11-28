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

package com.goldeneyes.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import com.goldeneyes.service.LeaveApprovalService;
import com.goldeneyes.vo.TaskVO;

/**
 * @author Administrator
 *
 */
@Service("leaveApprovalService")
public class LeaveApprovalServiceImpl implements LeaveApprovalService {

	/**
	 *  @author Administrator
	 */
	@Override
	public String launchApplication() throws Exception {
		// TODO Auto-generated method stub
		ProcessEngine processEngine = ProcessEngineConfiguration  
	                .createProcessEngineConfigurationFromResource("activiti.cfg.xml")  
	                .buildProcessEngine();  
		ProcessInstance instance = processEngine  
                 .getRuntimeService().startProcessInstanceByKey("LeaveApproval");  
        String procId = instance.getId();  
		return procId;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public List<TaskVO> getTasks(String userName, int taskType) throws Exception {
		// TODO Auto-generated method stub
		List<TaskVO> taskVOs = new ArrayList<TaskVO>();
		ProcessEngine processEngine = ProcessEngineConfiguration  
                .createProcessEngineConfigurationFromResource("activiti.cfg.xml")  
                .buildProcessEngine(); 
		
		//添加已完成任务
		if((taskType == 0) || (taskType == 1)){
			HistoryService hisService = processEngine.getHistoryService();
			List<HistoricTaskInstance> hisTasks = hisService.createHistoricTaskInstanceQuery().finished().taskAssignee(userName).list();
		    for (HistoricTaskInstance task : hisTasks) { 
		    	TaskVO taskVO = new TaskVO();
		    	taskVO.setTaskId(task.getId());
		    	taskVO.setProcessId(task.getProcessInstanceId());
		    	taskVO.setIsFinished(1);
		    	taskVOs.add(taskVO);
		    }
		}
		//添加未完成任务
		if((taskType == 0) || (taskType == 2)){
			TaskService taskService = processEngine.getTaskService();  
		    List<Task> tasks = taskService.createTaskQuery().taskCandidateOrAssigned(userName).list();
		    for (Task task : tasks) { 
		    	TaskVO taskVO = new TaskVO();
		    	taskVO.setTaskId(task.getId());
		    	taskVO.setProcessId(task.getProcessInstanceId());
		    	taskVO.setIsFinished(0);
		    	taskVOs.add(taskVO);
		    }
		}
		return taskVOs;
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public void completeTask(String userName, String taskId) throws Exception {
		// TODO Auto-generated method stub
		ProcessEngine processEngine = ProcessEngineConfiguration  
                .createProcessEngineConfigurationFromResource("activiti.cfg.xml")  
                .buildProcessEngine(); 
		TaskService taskService = processEngine.getTaskService();  
		//先认领任务
		taskService.claim(taskId, userName);  
		//再完成任务
		taskService.complete(taskId);  
	}

	/**
	 *  @author Administrator
	 */
	@Override
	public void rejectApproval(String userName, String procId) throws Exception {
		// TODO Auto-generated method stub
		ProcessEngine processEngine = ProcessEngineConfiguration  
                .createProcessEngineConfigurationFromResource("activiti.cfg.xml")  
                .buildProcessEngine();  
		processEngine.getRuntimeService().deleteProcessInstance(procId, userName);
	}
}
