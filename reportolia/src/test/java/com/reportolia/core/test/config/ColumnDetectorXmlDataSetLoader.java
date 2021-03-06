/**
 * 
 */
package com.reportolia.core.test.config;

import java.io.InputStream;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.springframework.core.io.Resource;

import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;

/**
 * The ColumnDetectorXmlDataSetLoader class
 *
 * @author Batir Akhmerov
 * Created on Dec 4, 2015
 */
public class ColumnDetectorXmlDataSetLoader extends FlatXmlDataSetLoader {
	@Override
	protected IDataSet createDataSet(Resource resource) throws Exception {
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		InputStream inputStream = resource.getInputStream();
		try {
			return builder.build(inputStream);
		} finally {
			inputStream.close();
		}
	}
}
