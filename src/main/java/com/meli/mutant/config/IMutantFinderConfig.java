package com.meli.mutant.config;

import org.springframework.context.annotation.Bean;

import com.meli.mutant.service.IMutantFinder;

public interface IMutantFinderConfig {

	@Bean
	public IMutantFinder mutantFinderBean();
}
