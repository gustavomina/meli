package com.meli.mutant.util.io;

public interface IPropertiesHelperFactory {

	/**
	 * This method creates a {@link IPropertiesHelper} from a property file stored
	 * in the file system. The file system path where properties files are stored is
	 * defined through OSF_PARAM_FILES_PATH environment variable.
	 * 
	 * @param fileName to read for creating {@link IPropertiesHelper}.
	 * @return {@link IPropertiesHelper} created from file read.
	 */
	public IPropertiesHelper createHelper(String fileName);
}
