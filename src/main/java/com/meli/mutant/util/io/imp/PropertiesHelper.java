package com.meli.mutant.util.io.imp;

import org.apache.commons.configuration2.Configuration;

import com.meli.mutant.util.io.IPropertiesHelper;

public class PropertiesHelper implements IPropertiesHelper {

	private Configuration configuration;

	@Override
	public Object getObject(String propertyName) {
		return configuration.getProperty(propertyName);
	}

	@Override
	public String getString(String propertyName) {
		return configuration.getString(propertyName);
	}

	@Override
	public String getString(String propertyName, String defaultValue) {
		return configuration.getString(propertyName, defaultValue);
	}

	@Override
	public int getInt(String propertyName) {
		return configuration.getInt(propertyName);
	}

	@Override
	public int getInt(String propertyName, int defaultValue) {
		return configuration.getInt(propertyName, defaultValue);
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

}
