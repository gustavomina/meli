package com.meli.mutant.util.io.imp;

import org.springframework.context.ApplicationContext;

import com.meli.mutant.util.io.IPropertiesHelper;
import com.meli.mutant.util.io.IPropertiesHelperFactory;

public class PropertiesHelperFactory implements IPropertiesHelperFactory {

	private static final String PROPERTIES_HELPER_BEAN_NAME = "propertiesHelperBean";

	private ApplicationContext applicationContext;

	@Override
	public IPropertiesHelper createHelper(String fileName) {
		IPropertiesHelper propertiesHelper = (IPropertiesHelper) applicationContext.getBean(PROPERTIES_HELPER_BEAN_NAME,
				fileName);
		return propertiesHelper;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
