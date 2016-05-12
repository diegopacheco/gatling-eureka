# gatling-eureka
gatling-eureka module: Integrates Gatling with Netflix/Eureka. This module resolve IPs using Netflix/Eureka(https://github.com/Netflix/eureka).

# How to Build

Download the git repository code and build with sbt.

```scala
sbt compile package
```

# How to Install in Gatling?

Download gatling and place the the jar(gatling-eureka/target/scala-2.11/gatling-eureka_2.11-1.0.jar) in $GATLING_HOME/lib/

# Sample code? 

BasicSimulationEureka.scala
```scala
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

import io.gatling.eureka.Predef._
import io.gatling.eureka._

class BasicSimulationEureka extends Simulation {

  val httpConf = http
    .baseURL("http://requestb.in") 
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val headers = Map("Content-Type" -> "application/x-www-form-urlencoded")

  val eurekaIP = eureka("http://127.0.0.1:8080/eureka","EUREKA").ip

  val scn = scenario("Test Eureka Request Basic") 
    .exec(
      http("POST eureka IP")
        .post("/158u0j51")
        .headers(headers)
        .formParam("by", "Diego Pacheco") 
        .formParam("Eureka_IP", eurekaIP) 
    )

  setUp(scn.inject(atOnceUsers(1)).protocols(httpConf))
}
```

Them go to $GATLING_HOME and run $bin/gatling.sh 

Have Fun.

PS: Why this code is not on Gatling repo? 
https://github.com/gatling/gatling/pull/3014

Cheers, <BR>
Diego Pacheco