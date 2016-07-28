package edge.poc.sample

import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
 * Canonical configuration to be used by each simulation
 *
 * Created by medge on 5/19/16.
 */
trait GatlingAppConfig {

	val settings = Settings()

	def httpConf(baseUrl: String) =
		http
			.baseURL(baseUrl)
			.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
			.doNotTrackHeader("1")
			.acceptLanguageHeader("en-US,en;q=0.5")
			.acceptEncodingHeader("gzip, deflate")
			.userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

}
