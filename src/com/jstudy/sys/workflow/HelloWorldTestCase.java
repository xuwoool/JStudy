package com.jstudy.sys.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.jbpm.api.Configuration;
import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.api.history.HistoryActivityInstanceQuery;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.history.HistoryProcessInstanceQuery;
import org.jbpm.api.model.Activity;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorldTestCase extends TestCase {
	
	private static final Logger log = LoggerFactory.getLogger(HelloWorldTestCase.class);
	
	private ProcessEngine processEngine = null;
	
	private RepositoryService repositoryService = null;
	private ExecutionService executionService = null;
	private TaskService taskService = null;
	private HistoryService historyService = null;
	
	private ProcessInstance processInstance = null;
	private HistoryProcessInstanceQuery historyProcessInstanceQuery = null;
	private HistoryActivityInstanceQuery historyActivityInstanceQuery = null;
	
	@Override
	protected void setUp() throws Exception {
//		获取流程引擎
		processEngine = new Configuration().setResource("jbpm.cfg.xml").buildProcessEngine();
		repositoryService = processEngine.getRepositoryService();
		executionService = processEngine.getExecutionService();
		taskService = processEngine.getTaskService();
		historyService = processEngine.getHistoryService();
		super.setUp();
	}
	/**
	 * 发布流程
	 */
	public void test01DeployWorkFLow() {
		repositoryService.createDeployment().addResourceFromClasspath("com/jstudy/sys/workflow/HelloWorld.jpdl.xml").deploy();
	}
	/**
	 * 启动流程实例
	 */
	public void test02StartProcessInstance() {

//		启动流程实例(带业务键)
		executionService.startProcessInstanceByKey("HelloWorld","123");
		
//		启动流程实例(带业务键+参数)
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("taskFlag", 124);
		variables.put("amonut", 51);
		variables.put("citycode", "21");
		executionService.startProcessInstanceByKey("HelloWorld", variables, "124");
	}
	/**
	 * 启动流程实例，模拟流程流转过程
	 */
	public void test03SignalExecution() {
//		启动流程实例
		processInstance = executionService.startProcessInstanceByKey("HelloWorld");
		String pid = processInstance.getId();
//		获取当前活动的节点1
		Execution executionInState = processInstance.findActiveExecutionIn("state1");
		Assert.assertNotNull(executionInState);
//		执行节点1
		executionService.signalExecutionById(executionInState.getId(),"to task1");
		
//		查看是否进入下一活动节点2
		processInstance = executionService.findProcessInstanceById(pid);
		assertTrue(processInstance.isActive("task1"));
		
		processInstance = executionService.findProcessInstanceById(pid);
		Execution exectionInTask = processInstance.findActiveExecutionIn("task1");
		Assert.assertNotNull(exectionInTask);
		
//		执行节点2
		executionService.signalExecutionById(exectionInTask.getId(), "to state2");
	}
	/**
	 * 执行任务
	 */
	public void test04CompleteTask() {
		Task task = taskService.findPersonalTasks("tianhw").get(0);
		taskService.completeTask(task.getId());
		
	}
	/**
	 * 执行指定节点
	 */
	public void test05SignalExecution() {
//		processInstance = executionService.startProcessInstanceById("50001");
		processInstance = executionService.findProcessInstanceById("HelloWorld.123");
		Set<String> activityNames = processInstance.findActiveActivityNames();
		for (String name : activityNames) {
			log.info("当前活动节点=" + name);
		}
		Execution execution = processInstance.findActiveExecutionIn("state1");
		executionService.signalExecutionById(execution.getId());
	}
	/**
	 * 获取流程定义的节点
	 */
	public void test06findActivityNames() {
		ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().processDefinitionId("HelloWorld-3").uniqueResult();
		ProcessDefinitionImpl definitionImpl = (ProcessDefinitionImpl)definition;
		for (Activity activity : definitionImpl.getActivities()) {
			log.info("定义节点=" + activity.getName());
		}
	}
	/**
	 * 获取历史流程实例
	 */
	public void test07findHistoryProcessInstance() {
		historyProcessInstanceQuery = historyService.createHistoryProcessInstanceQuery().processDefinitionId("HelloWorld-3");
		log.info("产生流程个数="+historyProcessInstanceQuery.count());
		List<HistoryProcessInstance> historyProcessInstances =  historyProcessInstanceQuery.list();
		for (HistoryProcessInstance instance : historyProcessInstances) {
			log.info("历史实例ID=" + instance.getProcessInstanceId());
		}
	}
	/**
	 * 获取指定实例的历史活动节点
	 */
	public void test08findHistoryActivityInstance() {
		historyActivityInstanceQuery = historyService.createHistoryActivityInstanceQuery();
		historyActivityInstanceQuery = historyActivityInstanceQuery.processInstanceId("HelloWorld.123");
		log.info("已处理节点个数=" + historyActivityInstanceQuery.count());
		List<HistoryActivityInstance> historyActivityInstances = historyActivityInstanceQuery.list();
		for (HistoryActivityInstance instance :historyActivityInstances) {
			log.info("历史活动节点=" + instance.getActivityName());
		}
	}
	/**
	 * 根据满足的条件转移
	 */
	public void test09decision() {
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("content", "to state2");
		ProcessInstance processInstance = executionService.startProcessInstanceByKey("HelloWorld", variables, "123");
		String pid = processInstance.getId();
		
		processInstance = executionService.findProcessInstanceById(pid);
		assertTrue(processInstance.isActive("state2"));
	}
	public void test10createTask() {
		ProcessInstance processInstance = executionService.startProcessInstanceByKey("HelloWorld","123");
		ExecutionImpl executionImpl = (ExecutionImpl) processInstance.getProcessInstance();
		TransitionImpl transitionImpl = executionImpl.getActivity().createOutgoingTransition();
		TaskImpl task = new TaskImpl();
		task.setAssignee("tianhw"); //指定处理人
		task.setName("传输工程立项审批");
		task.setProcessInstance((ExecutionImpl) processInstance.getProcessInstance());
		taskService.saveTask(task);
	}
}
