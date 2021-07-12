package com.meli.mutant.util.io.imp;

import com.meli.mutant.util.io.IMutantFinderProperties;
import com.meli.mutant.util.io.IPropertiesHelper;

public class MutantFinderProperties implements IMutantFinderProperties {

	// Properties file
	public static final String MUTANT_FINDER_PROPERTIES_FILE = "mutantFinder.properties";

	public static final String JDBC = "mutantFinder.jdbc";
	public static final String DB_USER = "mutantFinder.db.user";
	public static final String DB_PWD = "mutantFinder.db.pwd";

	// Injection
	private IPropertiesHelper propertiesHelper;

	@Override
	public String getJdbc() {
		return propertiesHelper.getString(JDBC);
	}

	@Override
	public String getDbUser() {
		return propertiesHelper.getString(DB_USER);
	}

	@Override
	public String getDbPwd() {
		return propertiesHelper.getString(DB_PWD);
	}

	public void setPropertiesHelper(IPropertiesHelper propertiesHelper) {
		this.propertiesHelper = propertiesHelper;
	}

}
