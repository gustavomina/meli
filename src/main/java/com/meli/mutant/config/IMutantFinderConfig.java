package com.meli.mutant.config;

import java.sql.SQLException;

import org.springframework.context.annotation.Bean;

import com.meli.mutant.service.IMutantFinder;
import com.meli.mutant.util.io.IMutantFinderProperties;
import com.meli.mutant.util.io.IPropertiesHelperFactory;

public interface IMutantFinderConfig {

	@Bean
	public IMutantFinder mutantFinderBean() throws SQLException, Exception;

	@Bean
	public IPropertiesHelperFactory propertiesHelperFactoryBean();

	@Bean
	public IMutantFinderProperties mutantFinderPropertiesBean();

}
