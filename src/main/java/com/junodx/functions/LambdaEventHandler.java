package com.junodx.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import com.amazonaws.services.rdsdata.AWSRDSData;
import com.amazonaws.services.rdsdata.AWSRDSDataClient;
import com.amazonaws.services.rdsdata.model.*;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.junodx.api.connectors.lims.elements.client.payloads.GetSamplePayload;
import com.junodx.api.connectors.lims.elements.entities.ElementsReport;
import com.junodx.api.controllers.lab.actions.TestReportUpdateActions;
import com.junodx.api.controllers.payloads.ClientCredentialsPayload;
import com.junodx.api.controllers.payloads.OAuth2TokenDTO;
import com.junodx.api.controllers.payloads.ReportConfigurationPayload;
import com.junodx.api.controllers.payloads.TestReportUpdateRequest;
import com.junodx.api.models.configuration.lims.LIMSConfigurationEntity;
import com.junodx.api.models.configuration.lims.ReportTypes;
import com.junodx.api.models.core.types.PASS_FAIL;
import com.junodx.api.models.laboratory.BatchRun;
import com.junodx.api.models.laboratory.Kit;
import com.junodx.api.models.laboratory.TestReport;
import com.junodx.api.models.laboratory.TestRun;
import com.junodx.api.models.laboratory.reports.FSTRawData;
import com.junodx.api.models.laboratory.reports.NIPSBasicRawData;
import com.junodx.api.models.laboratory.reports.RawData;
import com.junodx.api.models.laboratory.reports.Report;
import com.junodx.api.models.laboratory.tests.*;
import com.junodx.api.models.laboratory.tests.types.GenderResultType;
import com.junodx.api.models.laboratory.tests.types.QCType;
import com.junodx.api.models.laboratory.tests.types.SCAResultType;
import com.junodx.api.models.laboratory.tests.types.SnpIdentityType;
import com.junodx.api.models.laboratory.types.ConfidenceIndexType;
import com.junodx.api.models.laboratory.types.ReportConfiguration;
import com.junodx.api.models.laboratory.types.ReportType;
import com.junodx.api.models.laboratory.tests.types.POSITIVE_NEGATIVE;
import com.junodx.api.services.mail.MailService;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

public class LambdaEventHandler  implements RequestHandler<SNSEvent, Object> {

    String applicationConfigurationFileName = "application.properties";

    LambdaLogger logger;

    private ObjectMapper mapper;
    private Properties props;
    private InputStream inStream;

    public LambdaEventHandler() {
        mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter();
        props = new Properties();
        try {
            loadConfiguration();
        } catch(Exception e){

        }
    }

    @Override
    public Object handleRequest(SNSEvent event, Context context)
    {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception occurred " + e.getMessage());
        }

        return "200 OK: ";
    }

    public void loadConfiguration() throws IOException {
        try {
            inStream = getClass().getClassLoader().getResourceAsStream(applicationConfigurationFileName);
            if (inStream != null) {
                props.load(inStream);

                //...load properties here

            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            inStream.close();
        }
    }
}

