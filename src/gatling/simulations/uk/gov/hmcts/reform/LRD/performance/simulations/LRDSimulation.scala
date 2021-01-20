package uk.gov.hmcts.reform.LRD.performance.simulations

 import io.gatling.core.Predef._
 import io.gatling.http.Predef._
 import uk.gov.hmcts.reform.LRD.performance.scenarios.utils._
 import uk.gov.hmcts.reform.LRD.performance.scenarios._

 class LRDSimulation extends Simulation{

   val httpProtocol = http.proxy(Proxy("proxyout.reform.hmcts.net", 8080).httpsPort(8080))
     .baseUrl(Environment.baseURL)

   val LRDScenario = scenario("LRDScenario").repeat(1)
     {
         exec(IDAMHelper.getIdamToken)
         .exec(S2SHelper.S2SAuthToken)
         .exec(GetScenario.GetScenario)
     }

   setUp(LRDScenario.inject(rampUsers(10) during(300))).protocols(httpProtocol)
 }
