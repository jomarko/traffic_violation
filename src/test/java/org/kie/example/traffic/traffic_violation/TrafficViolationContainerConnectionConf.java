package org.kie.example.traffic.traffic_violation;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNResult;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.DMNServicesClient;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

public class TrafficViolationContainerConnectionConf {

  private static final String URL = "http://localhost:8080/kie-server/services/rest/server";
  private static final String USER = "kieadmin";
  private static final String PASSWORD = "kieadmin1234;";
  private static final String CONTAINER_ID = "traffic-violation_1.0.0-SNAPSHOT";
  private static final String DMN_NAMESPACE = "https://github.com/kiegroup/drools/kie-dmn/_A4BCA8B8-CF08-433F-93B2-A2598F19ECFF";
  private static final String DMN_MODEL_NAME = "Traffic Violation";

  private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;

  private static KieServicesConfiguration conf;
  private static KieServicesClient kieServicesClient;

  private static DMNServicesClient dmnClient;

  public static void initialize() {
    conf = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD);

    //If you use custom classes, such as Obj.class, add them to the configuration.
    // Set<Class<?>> extraClassList = new HashSet<Class<?>>();
    // extraClassList.add(Map.class);
    // extraClassList.add(HashMap.class);
    // extraClassList.add(String.class);
    // extraClassList.add(java.math.BigDecimal.class);
    // conf.addExtraClasses(extraClassList);

    conf.setMarshallingFormat(FORMAT);
    kieServicesClient = KieServicesFactory.newKieServicesClient(conf);
    dmnClient = kieServicesClient.getServicesClient(DMNServicesClient.class);
  }

  public static ServiceResponse<DMNResult> evaluateTrafficViolationDmn(final DMNContext context) {
    // return dmnClient.evaluateAll(CONTAINER_ID, DMN_NAMESPACE, DMN_MODEL_NAME, context);
    return dmnClient.evaluateAll(CONTAINER_ID, context);
  }
  // public void disposeAndCreateContainer() {
  //   System.out.println("== Disposing and creating containers ==");

  //   // Retrieve list of KIE containers
  //   List<KieContainerResource> kieContainers = kieServicesClient.listContainers().getResult().getContainers();
  //   if (kieContainers.size() == 0) {
  //       System.out.println("No containers available...");
  //       return;
  //   }

  //   kieContainers.forEach(container -> {
  //     // Dispose KIE container
      
  //     String containerId = container.getContainerId();
  //     ServiceResponse<Void> responseDispose = kieServicesClient.disposeContainer(containerId);
  //     if (responseDispose.getType() == ServiceResponse.ResponseType.FAILURE) {
  //         System.out.println("Error disposing " + containerId + ". Message: ");
  //         System.out.println(responseDispose.getMsg());
  //         return;
  //     }
  //     System.out.println("Success Disposing container " + containerId);
  //     System.out.println("Trying to recreate the container...");

  //     // Re-create KIE container
  //     ServiceResponse<KieContainerResource> createResponse = kieServicesClient.createContainer(containerId, container);
  //     if(createResponse.getType() == ServiceResponse.ResponseType.FAILURE) {
  //         System.out.println("Error creating " + containerId + ". Message: ");
  //         System.out.println(responseDispose.getMsg());
  //         return;
  //     }
  //     System.out.println("Container recreated with success!");
  //   });
  // }
}