package com.meli.mutant.config.imp;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.meli.mutant.config.IMutantFinderConfig;
import com.meli.mutant.dao.IStatsDao;
import com.meli.mutant.dao.imp.StatsDao;
import com.meli.mutant.exception.MutantFinderException;
import com.meli.mutant.infraestructure.IDatabaseConnection;
import com.meli.mutant.infraestructure.imp.AWSProxyDatabaseConnection;
import com.meli.mutant.service.IMutantFinder;
import com.meli.mutant.service.imp.MutantFinder;
import com.meli.mutant.util.MatrixUtil;
import com.meli.mutant.util.io.IMutantFinderProperties;
import com.meli.mutant.util.io.IPropertiesHelper;
import com.meli.mutant.util.io.IPropertiesHelperFactory;
import com.meli.mutant.util.io.imp.MutantFinderProperties;
import com.meli.mutant.util.io.imp.PropertiesHelper;
import com.meli.mutant.util.io.imp.PropertiesHelperFactory;

@Configuration
@Import({ MutantFinderController.class })
public class MutantFinderConfig implements IMutantFinderConfig {

	@Autowired
	private ApplicationContext applicationContext;

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
		databaseConnection.setMutantFinderProperties(mutantFinderPropertiesBean());
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

	@Bean
	public IPropertiesHelperFactory propertiesHelperFactoryBean() {
		PropertiesHelperFactory propertiesHelperFactory = new PropertiesHelperFactory();
		propertiesHelperFactory.setApplicationContext(applicationContext);
		return propertiesHelperFactory;
	}

	@Bean
	public IMutantFinderProperties mutantFinderPropertiesBean() {
		IPropertiesHelperFactory propertiesHelperFactory = propertiesHelperFactoryBean();
		IPropertiesHelper propertiesHelper = propertiesHelperFactory
				.createHelper(MutantFinderProperties.MUTANT_FINDER_PROPERTIES_FILE);
		MutantFinderProperties mutantFinderProperties = new MutantFinderProperties();
		mutantFinderProperties.setPropertiesHelper(propertiesHelper);
		return mutantFinderProperties;
	}

	@Bean()
	@Scope(scopeName = "prototype")
	@Lazy
	public IPropertiesHelper propertiesHelperBean(String pathname) {
		PropertiesHelper propertiesHelper = new PropertiesHelper();
		propertiesHelper.setConfiguration(apacheConfigurationFileBean(pathname));
		return propertiesHelper;
	}

	@Bean
	@Scope(scopeName = "prototype")
	@Lazy
	public org.apache.commons.configuration2.Configuration apacheConfigurationFileBean(String pathname) {
		Configurations configurations = configurationsBean();
		org.apache.commons.configuration2.Configuration configurationFile = null;

		try {
			configurationFile = configurations.properties(fileBean(pathname));
		} catch (ConfigurationException e) {
			e.printStackTrace();
			throw new MutantFinderException(e.getMessage());
		}

		return configurationFile;
	}

	@Bean
	@Scope(scopeName = "prototype")
	@Lazy
	public URL fileBean(String pathname) {

		URL url = null;
		try {
			url = applicationContext.getResource(pathname).getURL();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Path path = Paths.get(url.toURI());

		// File file = new File(pathname);
		// return file;
		return url;
	}

	@Bean
	public Configurations configurationsBean() {
		Configurations configurations = new Configurations();
		return configurations;
	}

}
