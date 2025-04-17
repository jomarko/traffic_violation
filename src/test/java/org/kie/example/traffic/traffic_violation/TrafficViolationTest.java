package org.kie.example.traffic.traffic_violation;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import org.kie.dmn.core.api.DMNFactory;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNResult;
import org.kie.server.api.model.ServiceResponse;



public class TrafficViolationTest {
    @Test
    public void testDmnEvaluation() {
      TrafficViolationContainerConnectionConf.initialize();
      
      final Map<String, Object> driver = new HashMap<>();
      driver.put("Points", 15);

      final Map<String, Object> violation = new HashMap<>();
      violation.put("Type", "speed");
      violation.put("Actual Speed", 135);
      violation.put("Speed Limit", 100);
      
      final DMNContext c = DMNFactory.newContext();
      c.set("Driver", driver);
      c.set("Violation", violation);

      ServiceResponse<DMNResult> res = TrafficViolationContainerConnectionConf.evaluateTrafficViolationDmn(c);
      assertEquals(ServiceResponse.ResponseType.SUCCESS, res.getType());
        
      DMNResult dmnResult = res.getResult();
      assertEquals(dmnResult.hasErrors(), false);
      assertEquals("Yes", dmnResult.getDecisionResultByName("Should the driver be suspended?").getResult());

    }
}
