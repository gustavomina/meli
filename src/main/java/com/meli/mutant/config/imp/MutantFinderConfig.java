package com.meli.mutant.config.imp;

import java.sql.SQLException;

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
import com.meli.mutant.dao.IStatsDao;
import com.meli.mutant.dao.imp.StatsDao;
import com.meli.mutant.infraestructure.IDatabaseConnection;
import com.meli.mutant.infraestructure.imp.AWSProxyDatabaseConnection;
import com.meli.mutant.service.IMutantFinder;
import com.meli.mutant.service.imp.MutantFinder;
import com.meli.mutant.util.MatrixUtil;

@Configuration
@Import({ MutantFinderController.class })
public class MutantFinderConfig implements IMutantFinderConfig {

	@Bean
	public IMutantFinder mutantFinderBean() throws SQLException, Exception {
		MutantFinder mutant = new MutantFinder();

		mutant.setMatrixUtil(new MatrixUtil());
		mutant.setStatsDao(statsDaoBean());

		return mutant;
	}

	@Bean
	public IDatabaseConnection databaseConnectionBean() {
		AWSProxyDatabaseConnection databaseConnection = new AWSProxyDatabaseConnection();
		return databaseConnection;
	}

	@Bean
	public IStatsDao statsDaoBean() throws Exception, SQLException {
		StatsDao statsDao = new StatsDao();
		statsDao.setDatabaseConnection(databaseConnectionBean().getConnection());
		statsDao.setData(databaseConnectionBean());
		return statsDao;
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
