package com.epweike.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring工具类，提供取得Spring配置文件中定义的Bean的方法<br>
 * 用于单元测试
 * 
 */
public class SpringUtil2 {
	/** 唯一实例 */
	private static SpringUtil2 INSTALL = null;
	/** Spring工厂接口 */
	private BeanFactory beanFactory = null;
	/** Spring配置文件 */
	private static final String SPRING_CFG = "classpath:applicationContext.xml";

	/** 私有构造器 */
	private SpringUtil2() {
	}

	/**
	 * 取得类的唯一实例
	 * 
	 * @return
	 */
	public synchronized static SpringUtil2 getInstance() {
		if (INSTALL == null) {
			INSTALL = new SpringUtil2();
		}
		return INSTALL;
	}

	/**
	 * 取得BeanFactory
	 */
	private synchronized BeanFactory getBeanFactory() {
		if (this.beanFactory == null) {
			this.beanFactory = new ClassPathXmlApplicationContext(SPRING_CFG);
		}
		return this.beanFactory;
	}

	/**
	 * 通过在Spring配置文件中定义的bean名称，从IOC容器中取得实例
	 * 
	 * @param beanName
	 *            bean名称
	 * @return bean名称对应实例Object，使用时需要强制类型转换
	 */
	public Object getBean(String beanName) throws NullPointerException {
		if (beanName == null) {
			throw new java.lang.NullPointerException("beanName不能为空!");
		}
		return this.getBeanFactory().getBean(beanName);
	}

}
