package org.kie.example.traffic.traffic_violation;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.Map;
import java.util.HashMap;

import org.kie.dmn.core.api.DMNFactory;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNResult;
import org.kie.server.api.model.ServiceResponse;



public class TrafficViolationTest {
    @Test
    public void testDmnEvaluation() {
      MyKieServerConnectionConf.initialize();
      
      final Map<String, Object> driver = new HashMap<>();
      driver.put("Points", java.math.BigDecimal.valueOf(100));

      final Map<String, Object> violation = new HashMap<>();
      driver.put("Type", "speed");
      driver.put("Actual Speed", 135);
      driver.put("Speed Limit", 100);
      
      final DMNContext c = DMNFactory.newContext();
      c.set("Driver", driver);
      c.set("Violation", violation);

      ServiceResponse<DMNResult> res = MyKieServerConnectionConf.evaluateDMN("traffic-violation_1.0.0-SNAPSHOT", "https://github.com/kiegroup/drools/kie-dmn/_A4BCA8B8-CF08-433F-93B2-A2598F19ECFF", "Traffic Violation", c);
      assertEquals(ServiceResponse.ResponseType.SUCCESS, res.getType());
        
      DMNResult dmnResult = res.getResult();
      assertEquals("Yes", dmnResult.getDecisionResultByName("Should the driver be suspended?").getResult());

    }
}
