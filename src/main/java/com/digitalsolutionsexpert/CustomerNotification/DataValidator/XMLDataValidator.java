package com.digitalsolutionsexpert.CustomerNotification.DataValidator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class XMLDataValidator extends BaseDataValidator {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private boolean valid;
    private Schema schema;

    public XMLDataValidator(String validator) {
        super(validator);
        this.valid = false;
        this.schema = null;
        try {
            SchemaFactory xmlSchemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema xmlSchema = xmlSchemaFactory.newSchema(new StreamSource(new StringReader(validator)));
            this.schema = xmlSchema;
            this.valid = true;
        } catch (SAXException e) {
            logger.error(e.getStackTrace()[0].getMethodName(), e);
        }
    }

    public DataValidationReport validate(Object data) {
        boolean reportSuccess = false;
        List<String> reportMessageList = new ArrayList<String>();
        try {
            Validator validator = schema.newValidator();
            Result result = null;
            validator.validate(new DOMSource((Node) data));
            reportSuccess = true;
        } catch (SAXException e) {
            reportMessageList.add(e.getMessage());
        } catch (IOException e) {
            reportMessageList.add(e.getMessage());
        }
        DataValidationReport dataValidationReport = new DataValidationReport(reportSuccess, reportMessageList);
        return dataValidationReport;
    }

    public boolean isValid() {
        return this.valid;
    }

    @Override
    public JsonNode convertToProcessingFormat(Object payload) throws Exception {
        if (payload == null) {
            return null;
        }
        String payloadString = null;
        if (payload instanceof Node) {
            StringWriter sw = new StringWriter();
            try {
                Transformer t = TransformerFactory.newInstance().newTransformer();
                t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                t.transform(new DOMSource((Node) payload), new StreamResult(sw));
                payloadString = sw.toString();
            } catch (TransformerException e) {
                throw e;
            }
        }
        if (payload instanceof String || payloadString != null) {
            payloadString = payloadString != null ? payloadString : (String) payload;
            try {
                XmlMapper xmlMapper = new XmlMapper();
                return xmlMapper.readValue(payloadString, JsonNode.class);
            } catch (Exception e) {
                throw e;
            }
        }
        throw new Exception("Method convertToProcessingFormat does not have support for [" + payload.getClass().getSimpleName() + "] class.");
    }

    @Override
    public Node convertToValidationFormat(Object payload) throws Exception {
        if (payload == null) {
            return null;
        }
        if (payload instanceof String) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder;
                builder = factory.newDocumentBuilder();
                return builder.parse(new InputSource(new StringReader((String) payload)));
            } catch (Exception e) {
                throw e;
            }
        }
        throw new Exception("Method convertToValidationFormat does not have support for [" + payload.getClass().getSimpleName() + "] class.");
    }
}
