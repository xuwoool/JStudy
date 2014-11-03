package com.jstudy.sys.workflow;

import org.jbpm.api.Configuration;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;

import junit.framework.TestCase;

import com.jstudy.sys.vo.CityProjectForm;

public class CityProjectTestCase extends TestCase {
	private ProcessEngine processEngine;
	
	private RepositoryService repositoryService;
	
	private CityProjectForm form = null;
	
	@Override
	protected void setUp() throws Exception {
		processEngine = new Configuration().setResource("jbpm.cfg.xml").buildProcessEngine();
		
		repositoryService = processEngine.getRepositoryService();
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	/**
	 * 发布流程
	 */
	public void test01deployWorkFlow() {
		repositoryService.createDeployment().addResourceFromClasspath("com/jstudy/sys/workflow/CityProject.jpdl.xml").deploy();
	}
	/**
	 * 填写表单
	 */
	private void writeForm() {
		form = new CityProjectForm();
		
		form.setProjectCd("B1423601");
		form.setApprovalName("关于太湖路-山西路的立项");
		form.setCityCode("21");
		form.setCreateUser("tianhw");
		form.setProjectInvAt(200.00);
		form.setTaskFlag(1000L);
	}
	
	public void test03submit() {
		writeForm();
	}
}
