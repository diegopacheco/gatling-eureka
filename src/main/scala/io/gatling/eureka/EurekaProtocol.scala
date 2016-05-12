package io.gatling.eureka

import scala.io.Codec
import java.util.zip.GZIPInputStream
import java.io.FileInputStream
import java.io.BufferedInputStream
import scala.io.Source
import scala.language.reflectiveCalls
import java.net.HttpURLConnection
import java.net.URL
import xml.{ NodeSeq, Elem }

/**
 * Netflix/Eureka Gatling integration.
 *
 * @autor diegopacheco
 *
 */
class EurekaProtocolBuilder(url: String, appName: String) {

  implicit def closingSource(source: Source) = new {
    val lines = source.getLines()
    var isOpen = true
    def closeAfterGetLines() = new Iterator[String] {
      def hasNext = isOpen && hasNextAndCloseIfDone
      def next() = {
        val line = lines.next()
        hasNextAndCloseIfDone
        line
      }
      private def hasNextAndCloseIfDone = if (lines.hasNext) true else { source.close(); isOpen = false; false }
    }
  }

  def ip(): String = {
    val eurekaRequestURL: String = url + "/v2/apps/" + appName

    val u = new URL(eurekaRequestURL);
    val uc = u.openConnection();
    val gzInputStream = new GZIPInputStream(new BufferedInputStream(uc.getInputStream))
    val eurekaXMLResponse = Source.fromInputStream(gzInputStream).closeAfterGetLines().mkString
    val eurekaXML = scala.xml.XML.loadString(eurekaXMLResponse)

    val ip: String = (eurekaXML \ "instance" \ "ipAddr").text
    return ip

  }

}

object MainAppTest extends App {
  val ip = new EurekaProtocolBuilder("http://127.0.0.1:8080/eureka", "EUREKA").ip()
  println("IP: " + ip)
}

