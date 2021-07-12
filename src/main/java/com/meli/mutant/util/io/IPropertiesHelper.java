package com.meli.mutant.util.io;

public interface IPropertiesHelper {
	/**
	 * Method to get the value of a property in Object type.
	 * 
	 * @param propertyName that represents the key.
	 * @return property value obtained through property name.
	 */
	public Object getObject(String propertyName);

	/**
	 * Method to get the value of a property with String type.
	 * 
	 * @param propertyName that represents the key.
	 * @return property value obtained through property name.
	 */
	public String getString(String propertyName);

	/**
	 * Method to get the value of a property with String type. Returns the default
	 * value if the key is missing.
	 * 
	 * @param propertyName that represents the key.
	 * @param defaultValue the default value.
	 * @return property value obtained through property name.
	 */
	public String getString(String propertyName, String defaultValue);

	/**
	 * Method to get the value of a property with Integer type.
	 * 
	 * @param propertyName that represents the key.
	 * @return property value obtained through property name.
	 */
	public int getInt(String propertyName);

	/**
	 * Method to get the value of a property with Integer type. Returns the default
	 * value if the key is missing.
	 * 
	 * @param propertyName that represents the key.
	 * @param defaultValue the default value.
	 * @return property value obtained through property name.
	 */
	public int getInt(String propertyName, int defaultValue);

}
