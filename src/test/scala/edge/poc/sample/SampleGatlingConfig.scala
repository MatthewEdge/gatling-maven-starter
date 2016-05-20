package edge.poc.sample

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

/**
 * Canonical configuration to be used by each simulation
 *
 * Created by medge on 5/19/16.
 */
trait SampleGatlingConfig extends SampleConfig {

	val httpConf = http
		.baseURL("http://localhost:8080")
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.doNotTrackHeader("1")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

	// Ramp users per second until the given limit. Pauses for 2 seconds between ramps
	val increasingUsers = (constantUsersPerSec(5) during(5.seconds)) :: (10 to 100 by 5).toList.flatMap { i =>
		List(
			nothingFor(2.seconds),
			constantUsersPerSec(i) during 5.seconds
		)
	}

}
