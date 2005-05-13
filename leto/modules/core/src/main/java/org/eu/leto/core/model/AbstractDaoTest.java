package org.eu.leto.core.model;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


public abstract class AbstractDaoTest extends
        AbstractDependencyInjectionSpringContextTests {
    private final Log log = LogFactory.getLog(getClass());
    private DataSource dataSource;


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    protected final void onTearDown() throws Exception {
        setDirty();
        onTearDownInternal();
    }


    protected void onTearDownInternal() throws Exception {
    }


    @Override
    protected final void onSetUp() throws Exception {
        onSetUpInternal();
    }


    protected void onSetUpInternal() throws Exception {
    }


    @Override
    protected final String[] getConfigLocations() {
        final List<String> configLocations = new ArrayList<String>();
        configLocations.add("classpath:/dao-applicationContext.xml");
        configLocations.addAll(Arrays.asList(getAdditionnalConfigLocations()));

        return configLocations.toArray(new String[configLocations.size()]);
    }


    protected String[] getAdditionnalConfigLocations() {
        return new String[0];
    }


    protected void loadData(String dataLocation) throws Exception {
        final Resource resource = new ClassPathResource(dataLocation);
        if (dataLocation == null) {
            throw new NullArgumentException("dataLocation");
        }
        log.info("Loading data from '" + dataLocation + "'");

        final IDataSet dataSet;
        if (resource.getFilename().endsWith(".xls")) {
            dataSet = new XlsDataSet(resource.getInputStream());
        } else {
            dataSet = new XmlDataSet(resource.getInputStream());
        }

        assertNotNull("DataSource is not configured", dataSource);

        Connection conn = null;
        try {
            // we get a new connection to the database, possibly in a
            // transaction
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            final IDatabaseConnection dbConn = new DatabaseConnection(conn);
            dbConn.getConfig().setProperty(
                    DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
                    new HsqlDataTypeFactory());

            DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);

            // everything is alright: we commit the transaction
            conn.commit();
        } catch (Exception e) {
            try {
                // we encountered some error: we cancel the transaction
                conn.rollback();
            } catch (Exception ignore) {
            }

            throw new RuntimeException("Error while loading data from '"
                    + dataLocation + "'", e);
        } finally {
            if (conn != null) {
                try {
                    // whatever happens, we close the connection
                    conn.close();
                } catch (Exception ignore) {
                }
            }
        }
    }
}
