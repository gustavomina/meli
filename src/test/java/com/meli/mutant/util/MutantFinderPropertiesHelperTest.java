package com.meli.mutant.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;

import org.apache.commons.configuration2.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.meli.mutant.util.io.imp.PropertiesHelper;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class MutantFinderPropertiesHelperTest {

	private PropertiesHelper propertiesHelper;

	@Mock
	private Configuration configuration;

	private String propertyName = "helper.property.name";
	private String expectedValue = "HelperPropertyValue";

	@Before
	public void before() {
		propertiesHelper = new PropertiesHelper();
	}

	@Test
	public void whenPropertyExistsThenReturnValue() {

		when(configuration.getString(propertyName)).thenReturn(expectedValue);

		propertiesHelper.setConfiguration(configuration);

		String returnedValue;

		returnedValue = propertiesHelper.getString(propertyName);

		assertEquals(expectedValue, returnedValue);
	}

	@Test
	public void whenPropertyIsInteger() {

		when(configuration.getInt("helper.dummy")).thenReturn(1);

		propertiesHelper.setConfiguration(configuration);

		int returnedValue;

		returnedValue = propertiesHelper.getInt("helper.dummy");

		assertEquals(1, returnedValue);
	}

	@Test
	public void whenPropertyIsObject() {

		when(configuration.getProperty("helper.dummy")).thenReturn(propertiesHelper);

		propertiesHelper.setConfiguration(configuration);

		Object returnedValue;

		returnedValue = propertiesHelper.getObject("helper.dummy");

		assertEquals(propertiesHelper, returnedValue);
	}
}
