package com.meli.mutant.config.imp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.meli.mutant.config.IMutantFinderConfig;
import com.meli.mutant.service.IMutantFinder;
import com.meli.mutant.service.imp.MutantFinder;
import com.meli.mutant.util.MatrixUtil;

@Configuration
@Import({ MutantFinderController.class })
public class MutantFinderConfig implements IMutantFinderConfig {

	@Bean
	public IMutantFinder mutantFinderBean() {
		System.out.println("Bean IMutantFinder...");
		MutantFinder mutant = new MutantFinder();
		mutant.setMatrixUtil(new MatrixUtil());

		return mutant;
	}

	/*
	 * Create required HandlerMapping, to avoid several default HandlerMapping
	 * instances being created
	 */
	@Bean
	public HandlerMapping handlerMapping() {
		return new RequestMappingHandlerMapping();
	}

	/*
	 * Create required HandlerAdapter, to avoid several default HandlerAdapter
	 * instances being created
	 */
	@Bean
	public HandlerAdapter handlerAdapter() {
		return new RequestMappingHandlerAdapter();
	}

	/*
	 * optimization - avoids creating default exception resolvers; not required as
	 * the serverless container handles all exceptions
	 *
	 * By default, an ExceptionHandlerExceptionResolver is created which creates
	 * many dependent object, including an expensive ObjectMapper instance.
	 *
	 * To enable custom @ControllerAdvice classes remove this bean.
	 */
	@Bean
	public HandlerExceptionResolver handlerExceptionResolver() {
		return new HandlerExceptionResolver() {

			@Override
			public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
					Object handler, Exception ex) {
				return null;
			}
		};
	}

}
