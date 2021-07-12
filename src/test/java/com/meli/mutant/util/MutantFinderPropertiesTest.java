package com.meli.mutant.util;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.meli.mutant.util.io.IPropertiesHelper;
import com.meli.mutant.util.io.imp.MutantFinderProperties;

@RunWith(MockitoJUnitRunner.class)
public class MutantFinderPropertiesTest {

	@InjectMocks
	private MutantFinderProperties mutantFinderProperties;

	@Mock
	private IPropertiesHelper propertiesHelper;

	@Test
	public void whenCallGetDbPwdThenReturnPwd() {
		// Act
		mutantFinderProperties.getDbPwd();

		// Assert
		verify(propertiesHelper, times(1)).getString(any());
	}

	@Test
	public void whenCallGetDbUserThenReturnUser() {
		// Act
		mutantFinderProperties.getDbUser();

		// Assert
		verify(propertiesHelper, times(1)).getString(any());
	}

	@Test
	public void whenCallGetDbStringThenReturnConnectionString() {
		// Act
		mutantFinderProperties.getJdbc();

		// Assert
		verify(propertiesHelper, times(1)).getString(any());
	}

}
