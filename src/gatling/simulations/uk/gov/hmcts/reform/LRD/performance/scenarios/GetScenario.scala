package uk.gov.hmcts.reform.LRD.performance.scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.LRD.performance.scenarios.utils.Environment

object GetScenario {

  val feederFile = csv("OrgIDStructure.csv").circular
  val feederFile2 = csv("CCDCaseType.csv").circular

  val GetScenario = scenario("GetScenario")
    /*.exec(http(requestName="LRD_010_NoServiceCode")
      .get("/refdata/location/orgServices")
      .headers(Environment.headers_1)
      .check(status.is(200)))
    .pause(2)

    .feed(feederFile)
    .exec(http(requestName="LRD_020_ServiceCode")
      .get("/refdata/location/orgServices?service_code=${Service_Code}")
      .headers(Environment.headers_1)
      .check(status.is(200)))
    .pause(2)*/

    .feed(feederFile2)
    .exec(http(requestName="LRD_030_CCDCaseType")
      .get("/refdata/location/orgServices?service_code=${CCD_CASE_TYPE}")
      .headers(Environment.headers_1)
      .check(status.is(200)))
    .pause(2)

}
