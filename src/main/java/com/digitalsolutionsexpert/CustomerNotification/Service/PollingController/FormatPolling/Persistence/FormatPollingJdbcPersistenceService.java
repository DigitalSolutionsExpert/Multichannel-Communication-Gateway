package com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling.Persistence;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling.FormatPollingRequest;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling.FormatPollingResponse;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling.FormatPollingStatus;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.Persistence.BasePollingPersistenceService;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.Persistence.BasePollingPersistenceServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FormatPollingJdbcPersistenceService extends BasePollingPersistenceService<FormatPollingRequest, FormatPollingResponse, FormatPollingStatus> {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static final String STATUS_READY = "READY";
    public static final String STATUS_INPROC = "INPROC";
    public static final String STATUS_INPROC_RSN_RECEIVED = "RECEIVED";
    public static final String STATUS_INPROC_RSN_BEGIN_SEND = "BGNSEND";

    public static final String STATUS_SENT = "SUCCESS";
    public static final String STATUS_SENT_RSN = null;

    public static final String STATUS_ERROR = "ERROR";

    public FormatPollingJdbcPersistenceService(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        super(applicationConfiguration, path, name);
    }

    @Override
    public List<FormatPollingRequest> poll(int size, String serviceName) throws BasePollingPersistenceServiceException {
        List<FormatPollingRequest> formatPollingRequestList = new ArrayList<FormatPollingRequest>();
        if (size > 0) {
            DataSource ds = this.getApplicationConfiguration().getDataSource((String) this.getProperties().get("datasource-name"));
            //System.out.println("start executing poll with size [" + size + " ] on thread [" + Thread.currentThread().toString() + "]");
            Connection connection = null;
            Statement statement = null;
            PreparedStatement statementUpdate = null;
            ResultSet resultSet = null;
            try {
                connection = ds.getConnection();
                statement = connection.createStatement(
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                statement.setFetchSize(size);
                statement.setMaxRows(size);
                String tableName = (String) this.getProperties().getConfigurationSubtree("table").get("table-format-requests");
                int releaseSeconds = Integer.valueOf(String.valueOf(this.getProperties().getConfigurationSubtree("table").get("table-format-requests-release-seconds", "360")));
                String strQuery =
                        "SELECT " +
                                //" R.ROWID, " +
                                " R.ID, " +
                                " R.template_id, R.request_data, " +
                                " R.business_key_str01, R.business_key_str02, R.business_key_str03, " +
                                " R.business_key_num01, R.business_key_num02, R.business_key_num03, " +
                                " R.business_key_dat01, R.business_key_dat02, R.business_key_dat03, " +
                                " R.status_code, R.status_code_rsn, R.status_description, R.status_date, " +
                                " R.SERV_CODE, " +
                                " R.create_date, R.update_date, " +
                                " R.create_by, R.update_by " +
                                "  FROM " + tableName + " R" +
                                "  WHERE    (   R.STATUS_CODE = '" + STATUS_READY + "' " +
                                "                   OR " +
                                "              (R.STATUS_CODE = '" + STATUS_INPROC + "' AND R.STATUS_CODE_RSN = '" + STATUS_INPROC_RSN_RECEIVED + "' AND STATUS_DATE < SYSDATE - " + releaseSeconds + "*1.0/24/3600 )" +
                                "           )" +
                                "    AND (R.SERV_CODE IS NULL OR R.SERV_CODE = '" + serviceName + "')" +
                                //"    AND ROWNUM < " + (size + 1) + "" +
                                "  ORDER BY R.ID " +
                                "  FOR UPDATE " +
                                "  SKIP LOCKED" +
                                "";
                String strUpdate =
                        "UPDATE " + tableName + " " +
                                " SET SERV_CODE = ?, " +
                                "     STATUS_CODE = ?, " +
                                "     STATUS_CODE_RSN = ?, " +
                                "     STATUS_DATE = SYSDATE, " +
                                "     UPDATE_DATE = SYSDATE  " +
                                " WHERE ID = ?";

                statementUpdate = connection.prepareStatement(strUpdate);

                NumberFormat nf2 = new DecimalFormat("00");
                resultSet = statement.executeQuery(strQuery);

                int updatedCount = 0;
                while (resultSet.next()) {
                    FormatPollingRequest formatPollingRequest = new FormatPollingRequest();
                    formatPollingRequest.setId(resultSet.getLong("ID"));
                    formatPollingRequest.setTemplateId(resultSet.getLong("TEMPLATE_ID"));
                    formatPollingRequest.setRequestData(resultSet.getString("REQUEST_DATA"));

                    String[] businessKeyStringArray = new String[3];
                    for (int i = 0; i < businessKeyStringArray.length; i++) {
                        businessKeyStringArray[i] = resultSet.getString("BUSINESS_KEY_STR" + nf2.format(i + 1));
                    }
                    formatPollingRequest.setBusinessKeyString(businessKeyStringArray);

                    Number[] businessKeyNumberArray = new Number[3];
                    for (int i = 0; i < businessKeyNumberArray.length; i++) {
                        businessKeyNumberArray[i] = resultSet.getDouble("BUSINESS_KEY_NUM" + nf2.format(i + 1));
                    }
                    formatPollingRequest.setBusinessKeyNumber(businessKeyNumberArray);

                    Date[] businessKeyDateArray = new Date[3];
                    for (int i = 0; i < businessKeyDateArray.length; i++) {
                        businessKeyDateArray[i] = resultSet.getDate("BUSINESS_KEY_DAT" + nf2.format(i + 1));
                    }
                    formatPollingRequest.setBusinessKeyNumber(businessKeyNumberArray);

                    formatPollingRequest.setStatusCode(resultSet.getString("STATUS_CODE"));
                    formatPollingRequest.setStatusCodeRsn(resultSet.getString("STATUS_CODE_RSN"));
                    formatPollingRequest.setStatusDescription(resultSet.getString("STATUS_DESCRIPTION"));
                    formatPollingRequest.setStatusDate(resultSet.getDate("STATUS_DATE"));

                    formatPollingRequest.setServiceCode(resultSet.getString("SERV_CODE"));

                    formatPollingRequest.setCreateDate(resultSet.getDate("CREATE_DATE"));
                    formatPollingRequest.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
                    formatPollingRequest.setCreateBy(resultSet.getString("CREATE_BY"));
                    formatPollingRequest.setUpdateBy(resultSet.getString("UPDATE_BY"));

                    //mark object to be added to memory
                    formatPollingRequest.setStatusDate(new Date());
                    formatPollingRequest.setServiceCode(serviceName);
                    formatPollingRequest.setStatusCode(STATUS_INPROC);
                    formatPollingRequest.setStatusCodeRsn(STATUS_INPROC_RSN_RECEIVED);
                    formatPollingRequest.setStatusDescription(null);

                    /*
                    //update record back to database
                    resultSet.updateString("SERV_CODE", formatPollingRequest.getServiceCode());
                    resultSet.updateString("STATUS_CODE", formatPollingRequest.getStatusCode());
                    resultSet.updateString("STATUS_CODE_RSN", formatPollingRequest.getStatusCodeRsn());
                    resultSet.updateDate("STATUS_DATE", new java.sql.Date(formatPollingRequest.getStatusDate().getTime()));
                    resultSet.updateDate("UPDATE_DATE", new java.sql.Date(formatPollingRequest.getStatusDate().getTime()));
                    resultSet.updateLong("ID", resultSet.getLong("ID"));
                    //resultSet.updateString("UPDATE_BY", "");
                    resultSet.updateRow();

                    formatPollingRequestList.add(formatPollingRequest);
                    */

                    //statementUpdate.clearParameters();
                    statementUpdate.setString(1, formatPollingRequest.getServiceCode());
                    statementUpdate.setString(2, formatPollingRequest.getStatusCode());
                    statementUpdate.setString(3, formatPollingRequest.getStatusCodeRsn());
                    statementUpdate.setBigDecimal(4, BigDecimal.valueOf(formatPollingRequest.getId().longValue()));
                    statementUpdate.addBatch();
                    formatPollingRequestList.add(formatPollingRequest);
                    updatedCount++;

                    /*
                    int updatedCount = statementUpdate.executeUpdate();
                       if (updatedCount > 0) {
                            formatPollingRequestList.add(formatPollingRequest);
                        }
                    */
                }
                if (updatedCount > 0) {
                    int[] affectedRecords = statementUpdate.executeBatch();
                    int updatedBatchCount = 0;
                    for (int i = 0; i < affectedRecords.length; i++) {
                        updatedBatchCount = updatedBatchCount + ((affectedRecords[i] == Statement.SUCCESS_NO_INFO || affectedRecords[i] == 1 /*driver returned the updated rows*/) ? 1 : 0);
                    }
                    if (updatedCount != updatedBatchCount) {
                        formatPollingRequestList.clear();
                        connection.rollback();
                        logger.info("Unable to poll requests [updatedCount(" + updatedCount + ") != updatedBatchCount(" + updatedBatchCount + ")] on thread[" + Thread.currentThread().toString() + "]");
                    } else {
                        connection.commit();
                    }
                } else {
                    connection.rollback();
                    logger.info("Unable to poll requests [updatedCount(" + updatedCount + ")] on thread[" + Thread.currentThread().toString() + "]");
                }
            } catch (SQLException e) {
                try {
                    logger.error(e.getStackTrace()[0].getMethodName(), e);
                } catch (Exception ex) {
                }
                formatPollingRequestList.clear();

                try {
                    connection.rollback();
                } catch (Exception ex) {
                }

            } finally {
                try {
                    statementUpdate.close();
                } catch (SQLException e) {
                }

                statementUpdate = null;

                try {
                    resultSet.close();
                } catch (Exception ex) {
                }

                resultSet = null;

                try {
                    statement.close();
                } catch (Exception ex) {
                }
                statement = null;

                try {
                    connection.close();
                } catch (Exception ex) {
                }
                connection = null;
            }
        }
        return formatPollingRequestList;
    }

    @Override
    public void updateStatus(FormatPollingRequest formatPollingRequest, FormatPollingResponse formatPollingResponse, FormatPollingStatus formatPollingStatus) throws BasePollingPersistenceServiceException {
        boolean success = false;
        DataSource ds = this.getApplicationConfiguration().getDataSource((String) this.getProperties().get("datasource-name"));
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ds.getConnection();
            String tableName = (String) this.getProperties().getConfigurationSubtree("table").get("table-format-requests");
            String strUpdate = "UPDATE " + tableName + " R SET R.STATUS_CODE = ?, R.STATUS_CODE_RSN = ?, R.STATUS_DESCRIPTION = ?, R.STATUS_DATE = SYSDATE, R.UPDATE_DATE = SYSDATE WHERE R.ID = ?";
            preparedStatement = connection.prepareStatement(strUpdate);
            preparedStatement.setString(1, formatPollingStatus.getStatus());
            preparedStatement.setString(2, formatPollingStatus.getStatusReason());
            String description = formatPollingStatus.getDescription();
            if (description != null && description.length() > 4000) {
                description = description.substring(0, 3999);
            }
            preparedStatement.setString(3, description);
            preparedStatement.setBigDecimal(4, BigDecimal.valueOf(formatPollingRequest.getId().doubleValue()));
            preparedStatement.executeUpdate();
            connection.commit();
            success = true;
        } catch (SQLException e) {
            success = false;
            //logger.error(e.getStackTrace()[0].getMethodName(), e);
            throw new BasePollingPersistenceServiceException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception ex) {
            }
            preparedStatement = null;

            try {
                connection.close();
            } catch (Exception ex) {
            }
            connection = null;
        }
    }

    @Override
    public void moveToHistory(int size) throws BasePollingPersistenceServiceException {
        boolean success = false;
        DataSource ds = this.getApplicationConfiguration().getDataSource((String) this.getProperties().get("datasource-name"));
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ds.getConnection();
            String tableName = (String) this.getProperties().getConfigurationSubtree("table").get("table-format-requests");
            String tableNameHistory = (String) this.getProperties().getConfigurationSubtree("table").get("table-format-requests-history");

            String strMove =
                    "DECLARE \n" +
                            "    TYPE ROWID_TABLE IS TABLE OF VARCHAR2(50); \n" +
                            "    intSize NUMBER := ?;\n" +
                            "    arrRowId ROWID_TABLE; \n" +
                            "BEGIN\n" +
                            "    SELECT T.ROWID\n" +
                            "        BULK COLLECT INTO arrRowId\n" +
                            "        FROM " + tableName + " T\n" +
                            "        WHERE T.STATUS_CODE IN ('" + STATUS_SENT + "', '" + STATUS_ERROR + "')\n" +
                            "          AND ROWNUM <= intSize; \n" +
                            "    FORALL I IN arrRowId.FIRST .. arrRowId.LAST \n" +
                            "        INSERT INTO " + tableNameHistory + " \n" +
                            "            SELECT * FROM EGW_REQUESTS T\n" +
                            "            WHERE T.ROWID = arrRowId(I);\n" +
                            "    FORALL I IN arrRowId.FIRST .. arrRowId.LAST \n" +
                            "        DELETE            \n" +
                            "            FROM " + tableName + " T\n" +
                            "            WHERE T.ROWID = arrRowId(I);\n" +
                            "    COMMIT; \n" +
                            "END;";
            preparedStatement = connection.prepareStatement(strMove);
            preparedStatement.setLong(1, size);
            preparedStatement.execute();
            connection.commit();
            success = true;
        } catch (SQLException e) {
            success = false;
            logger.error(e.getStackTrace()[0].getMethodName(), e);
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception ex) {
            }
            preparedStatement = null;

            try {
                connection.close();
            } catch (Exception ex) {
            }
            connection = null;
        }
    }
}