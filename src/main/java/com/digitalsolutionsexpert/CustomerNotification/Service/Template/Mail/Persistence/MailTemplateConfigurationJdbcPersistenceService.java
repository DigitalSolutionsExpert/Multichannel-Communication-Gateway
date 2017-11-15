package com.digitalsolutionsexpert.CustomerNotification.Service.Template.Mail.Persistence;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.DataValidator.DataValidatorFactory;
import com.digitalsolutionsexpert.CustomerNotification.DataValidator.IDataValidator;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.Persistence.PersistenceServiceException;
import org.apache.commons.io.IOUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MailTemplateConfigurationJdbcPersistenceService extends BaseMailTemplateConfigurationPersistenceService {

    public MailTemplateConfigurationJdbcPersistenceService(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException {
        super(applicationConfiguration, path, name);
    }

    @Override
    public Map<Number, MailTemplate> retrieveMailTemplates() throws PersistenceServiceException {
        Map<Number, MailTemplate> dbMailTemplateMap = new ConcurrentHashMap<Number, MailTemplate>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            DataSource ds = this.getApplicationConfiguration().getDataSource((String) this.getProperties().get("datasource-name"));
            DataValidatorFactory dataValidatorFactory = new DataValidatorFactory();
            connection = ds.getConnection();
            statement = connection.createStatement();
            String tableName = (String) this.getProperties().getConfigurationSubtree("table").get("table-mail-template");
            String strQuery = "SELECT T.* FROM " + tableName + " T ORDER BY T.ID";
            resultSet = statement.executeQuery(strQuery);
            while (resultSet.next()) {
                MailTemplate mailTemplate = new MailTemplate();

                mailTemplate.setId(resultSet.getLong("ID"));
                mailTemplate.setCode(resultSet.getString("CODE"));
                mailTemplate.setName(resultSet.getString("NAME"));
                mailTemplate.setDescription(resultSet.getString("DESCRIPTION"));
                mailTemplate.setStatus(resultSet.getString("STATUS"));

                mailTemplate.setFromFormatId(resultSet.getBigDecimal("FROM_FMT_ID"));
                mailTemplate.setSenderFormatId(resultSet.getBigDecimal("SENDER_FMT_ID"));
                mailTemplate.setToFormatId(resultSet.getBigDecimal("TO_FMT_ID"));
                mailTemplate.setCcFormatId(resultSet.getBigDecimal("CC_FMT_ID"));
                mailTemplate.setBccFormatId(resultSet.getBigDecimal("BCC_FMT_ID"));
                mailTemplate.setSubjectFormatId(resultSet.getBigDecimal("SUBJECT_FMT_ID"));
                mailTemplate.setBodyFormatId(resultSet.getBigDecimal("BODY_FMT_ID"));
                mailTemplate.setBodyFormat(resultSet.getString("BODY_FORMAT"));
                mailTemplate.setAttachementFormatId(resultSet.getBigDecimal("ATTACHMENTS_FMT_ID"));

                String dataValidatorContent = resultSet.getClob("DATA_VALIDATOR") == null ? null : IOUtils.toString(resultSet.getClob("DATA_VALIDATOR").getAsciiStream(), "UTF8");
                String dataValidatorType = resultSet.getString("DATA_TYPE");
                IDataValidator dataValidator = dataValidatorFactory.create(dataValidatorContent, dataValidatorType);
                mailTemplate.setDataValidator(dataValidator);

                mailTemplate.setCreateDate(resultSet.getDate("CREATE_DATE"));
                mailTemplate.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
                mailTemplate.setCreateBy(resultSet.getString("CREATE_BY"));
                mailTemplate.setUpdateBy(resultSet.getString("UPDATE_BY"));

                dbMailTemplateMap.put(mailTemplate.getId(), mailTemplate);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        return dbMailTemplateMap;
    }
}
