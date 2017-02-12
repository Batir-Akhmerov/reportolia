/**
 * 
 */
package com.reportolia.core.test.utils;

import com.reportolia.core.utils.CoreUtils;

import junit.framework.TestCase;

/**
 * The CoreUtilsTests class
 *
 * @author Batir Akhmerov
 * Created on Dec 22, 2016
 */
public class CoreUtilsTests extends TestCase {
	
	public class TestBean {
		private long id;
		private String name;
		
		public long getId() {
			return this.id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getName() {
			return this.name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public class TestBean2 {
		private long id;
		private String name;
		private TestBean bean;
		
		public long getId() {
			return this.id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getName() {
			return this.name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public TestBean getBean() {
			return this.bean;
		}
		public void setBean(TestBean bean) {
			this.bean = bean;
		}
	}
	protected void setUp(){
		
		
	}
	
	
	public void test_copyProperties() {
		TestBean bean = new TestBean();
		bean.setId(13L);
		bean.setName("bean name");
		
		TestBean2 bean2 = new TestBean2();
		
		CoreUtils.copyProperties(bean, bean2, "name", "id");
		assertEquals(bean.getId(), bean2.getId());
		assertEquals(bean.getName(), bean2.getName());
		
		TestBean nestedBean = new TestBean();
		nestedBean.setId(33L);
		nestedBean.setName("nested bean name");
		bean2.setBean(nestedBean);
		
		TestBean2 bean21 = new TestBean2();
		
		CoreUtils.copyProperties(bean2, bean21, "bean", "id");
		assertEquals(nestedBean, bean21.getBean());
		assertEquals(bean.getId(), bean21.getId());
		
	}
	
}
