package io.gatling.eureka

/**
 * Netflix/Eureka Gatling integration.
 *
 * @autor diegopacheco
 *
 */
object Predef {

  def eureka(url: String, appName: String): EurekaProtocolBuilder =
    new EurekaProtocolBuilder(url, appName)

}
