package uk.gov.hmcts.reform.LRD.performance.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.LRD.performance.scenarios.utils._
import uk.gov.hmcts.reform.LRD.performance.scenarios._

class LRDSimulation extends Simulation{
  
  val rampUpDurationMins = 2
  val rampDownDurationMins = 2
  val testDurationMins = 60
  val HourlyTarget:Double = 600
  val RatePerSec = HourlyTarget / 3600

  val httpProtocol = http.proxy(Proxy("proxyout.reform.hmcts.net", 8080).httpsPort(8080))
    .baseUrl(Environment.baseURL)

  val LRDScenario = scenario("RoleAssignmentScenario").repeat(1)
    {
        exec(IDAMHelper.getIdamToken)
        .exec(S2SHelper.S2SAuthToken)
        .exec(GetScenario.GetScenario)
    }

  setUp(LRDScenario.inject(
      rampUsersPerSec(0.00) to (RatePerSec) during (rampUpDurationMins minutes),
      constantUsersPerSec(RatePerSec) during (testDurationMins minutes),
      rampUsersPerSec(RatePerSec) to (0.00) during (rampDownDurationMins minutes)
    )).protocols(httpProtocol)
}
