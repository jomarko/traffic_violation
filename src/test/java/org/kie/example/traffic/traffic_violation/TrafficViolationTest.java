package org.kie.example.traffic.traffic_violation;

import java.util.Map;
import java.util.HashMap;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TrafficViolationTest {

    private static final Logger logger = LoggerFactory.getLogger(TrafficViolationTest.class);

    @Test   
    public void testTrafficViolation() {

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();

        DMNRuntime dmnRuntime = KieRuntimeFactory.of(kieContainer.getKieBase()).get(DMNRuntime.class);

        String namespace = "https://github.com/kiegroup/drools/kie-dmn/_A4BCA8B8-CF08-433F-93B2-A2598F19ECFF";
        String modelName = "Traffic Violation";

        DMNModel dmnModel = dmnRuntime.getModel(namespace, modelName);

        
        final Map<String, Object> driver = new HashMap<>();
        driver.put("Points", 15);

        final Map<String, Object> violation = new HashMap<>();
        violation.put("Type", "speed");
        violation.put("Actual Speed", 135);
        violation.put("Speed Limit", 100);
        
        DMNContext dmnContext = dmnRuntime.newContext();  
        dmnContext.set("Driver", driver);
        dmnContext.set("Violation", violation);
        DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);  

        for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {  
            logger.info(
                "Decision: '" + dr.getDecisionName() + "', " +
                "Result: " + dr.getResult());        
            
            assertEquals(dr.getEvaluationStatus(), DMNDecisionResult.DecisionEvaluationStatus.SUCCEEDED);
         }
    }
}
