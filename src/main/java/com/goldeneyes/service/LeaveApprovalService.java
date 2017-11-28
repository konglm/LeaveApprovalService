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

package com.goldeneyes.service;

import java.util.List;

import com.goldeneyes.vo.TaskVO;

/**
 * @author Administrator
 *
 */
public interface LeaveApprovalService {
	/**
	 * 申请启动流程
	 * @return
	 * @throws Exception
	 */
	public String launchApplication() throws Exception;
	/**
	 * 获取任务列表
	 * @param userName
	 * @param taskType 0:全部 1:已完成 2:未完成
	 * @return
	 * @throws Exception
	 */
	public List<TaskVO> getTasks(String userName, int taskType) throws Exception;
	/**
	 * 完成任务
	 * @param userName
	 * @param taskId
	 * @throws Exception
	 */
	public void completeTask(String userName, String taskId) throws Exception;
	/**
	 * 驳回申请
	 * @param userName
	 * @param procId
	 * @return
	 * @throws Exception
	 */
	public void rejectApproval(String userName, String procId) throws Exception;

}
