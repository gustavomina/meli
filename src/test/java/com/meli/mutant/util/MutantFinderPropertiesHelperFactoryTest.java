package com.meli.mutant.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import com.meli.mutant.exception.MutantFinderException;
import com.meli.mutant.util.io.IPropertiesHelper;
import com.meli.mutant.util.io.imp.PropertiesHelper;
import com.meli.mutant.util.io.imp.PropertiesHelperFactory;

@RunWith(MockitoJUnitRunner.class)
public class MutantFinderPropertiesHelperFactoryTest {
	private PropertiesHelperFactory propertiesHelperFactory;

	@Mock
	private ApplicationContext applicationContext;

	private PropertiesHelper propertiesHelper;

	@Before
	public void before() {

		propertiesHelper = new PropertiesHelper();

		propertiesHelperFactory = new PropertiesHelperFactory();
	}

	@Test
	public void whenFileExistsThenReturnHelper() {
		String propertiesFile = "dummy.properties";

		when(applicationContext.getBean(anyString(), anyString())).thenReturn(propertiesHelper);

		propertiesHelperFactory.setApplicationContext(applicationContext);

		IPropertiesHelper createdHelper = propertiesHelperFactory.createHelper(propertiesFile);
		assertEquals(createdHelper, this.propertiesHelper);
	}

	@Test(expected = MutantFinderException.class)
	public void whenFileDoesNotExistThenReturnHelper() {
		String propertiesFile = "doesnotexist.properties";

		when(applicationContext.getBean(anyString(), anyString())).thenThrow(new MutantFinderException(""));
		propertiesHelperFactory.setApplicationContext(applicationContext);

		propertiesHelperFactory.createHelper(propertiesFile);
	}

}
