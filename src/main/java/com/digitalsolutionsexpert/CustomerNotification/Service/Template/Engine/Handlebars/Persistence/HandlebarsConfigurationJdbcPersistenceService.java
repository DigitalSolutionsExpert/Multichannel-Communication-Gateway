package com.digitalsolutionsexpert.CustomerNotification.Service.Template.Engine.Handlebars.Persistence;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.DataValidator.DataValidatorFactory;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.Persistence.PersistenceServiceException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HandlebarsConfigurationJdbcPersistenceService extends HandlebarsConfigurationBasePersistenceService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public HandlebarsConfigurationJdbcPersistenceService(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException {
        super(applicationConfiguration, path, name);
    }

    @Override
    public Map<Number, HandlebarsTemplate> retrieveFormatTemplates() throws PersistenceServiceException {
        Map<Number, HandlebarsTemplate> dbFormatTemplateMap = new ConcurrentHashMap<Number, HandlebarsTemplate>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            DataSource ds = this.getApplicationConfiguration().getDataSource((String) this.getProperties().get("datasource-name"));
            DataValidatorFactory dataValidatorFactory = new DataValidatorFactory();
            connection = ds.getConnection();
            statement = connection.createStatement();
            String tableName = (String) this.getProperties().getConfigurationSubtree("table").get("table-format-template");
            String strQuery = "SELECT T.* FROM " + tableName + " T WHERE T.TECHNOLOGY = 'HANDLEBARS' ORDER BY T.ID";
            resultSet = statement.executeQuery(strQuery);
            while (resultSet.next()) {
                HandlebarsTemplate handlebarsTemplate = new HandlebarsTemplate();

                handlebarsTemplate.setId(resultSet.getLong("ID"));
                handlebarsTemplate.setCode(resultSet.getString("CODE"));
                handlebarsTemplate.setName(resultSet.getString("NAME"));
                handlebarsTemplate.setDescription(resultSet.getString("DESCRIPTION"));

                handlebarsTemplate.setType(resultSet.getString("TYPE"));
                handlebarsTemplate.setContent(resultSet.getClob("CONTENT") == null ? null : IOUtils.toString(resultSet.getClob("CONTENT").getAsciiStream(), "UTF8"));

                handlebarsTemplate.setCreateDate(resultSet.getDate("CREATE_DATE"));
                handlebarsTemplate.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
                handlebarsTemplate.setCreateBy(resultSet.getString("CREATE_BY"));
                handlebarsTemplate.setUpdateBy(resultSet.getString("UPDATE_BY"));

                dbFormatTemplateMap.put(handlebarsTemplate.getId(), handlebarsTemplate);
            }
        } catch (Exception e) {
            throw new PersistenceServiceException(e);
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) {
            }
            resultSet = null;
            try {
                statement.close();
            } catch (Exception e) {
            }
            statement = null;
            try {
                connection.close();
            } catch (Exception e) {
            }
            connection = null;
        }
        return dbFormatTemplateMap;
    }

}
