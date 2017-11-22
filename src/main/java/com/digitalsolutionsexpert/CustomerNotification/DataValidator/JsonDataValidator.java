package com.digitalsolutionsexpert.CustomerNotification.DataValidator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonDataValidator extends BaseDataValidator {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private boolean valid;
    private JsonSchema schema;

    public JsonDataValidator(String validator) {
        super(validator);
        this.valid = false;
        this.schema = null;
        if (validator != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.byDefault();
                JsonNode jsonSchemaNode = objectMapper.readTree(validator);
                JsonSchema jsonSchema = jsonSchemaFactory.getJsonSchema(jsonSchemaNode);
                this.schema = jsonSchema;
                this.valid = true;
            } catch (IOException e) {
                logger.error(e.getStackTrace()[0].getMethodName(),e);
            } catch (ProcessingException e) {
                logger.error(e.getStackTrace()[0].getMethodName(), e);
            }
        } else {
            this.valid = true;
        }
    }

    public DataValidationReport validate(Object data) {
        boolean reportSuccess = false;
        List<String> reportMessageList = new ArrayList<String>();
        if (this.schema == null) {
            reportSuccess = true;
        } else {
            try {
                ProcessingReport report = this.schema.validate((JsonNode) data);
                reportSuccess = report.isSuccess();
                Iterator it = report.iterator();
                while (it.hasNext()) {
                    ProcessingMessage processingMessage = (ProcessingMessage) it.next();
                    reportMessageList.add(processingMessage.getMessage());
                }
            } catch (ProcessingException e) {
                reportMessageList.add(e.getMessage());
            }
        }
        DataValidationReport dataValidationReport = new DataValidationReport(reportSuccess, reportMessageList);
        return dataValidationReport;
    }

    public boolean isValid() {
        return this.valid;
    }

    @Override
    public JsonNode convertToValidationFormat(Object payload) throws Exception {
        return this.convertToProcessingFormat(payload);
    }

    @Override
    public JsonNode convertToProcessingFormat(Object payload) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        if (payload == null) {
            return null;
        }
        if (payload instanceof JsonNode) {
            return (JsonNode) payload;
        }
        if (payload instanceof String) {
            return objectMapper.readTree((String) payload);
        }
        throw new Exception("Method convertToProcessingFormat does not have support for [" + payload.getClass().getSimpleName() + "] class.");
    }
}
