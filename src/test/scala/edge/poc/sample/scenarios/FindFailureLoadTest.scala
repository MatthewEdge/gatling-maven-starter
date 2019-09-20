package edge.poc.sample.scenarios

import edge.poc.sample.GatlingAppConfig
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration._

/**
 * @author medge
 * Load Test Runner for the FindFailure Scenario
 */
class FindFailureLoadTest extends Simulation with GatlingAppConfig {

	val CHUNK_SCALE_FACTOR = settings.chunkScaleFactor
	val RESPONSE_TIME_LIMIT = settings.responseTimeLimit
	val countKey = "count"

	setUp(
		scenario("FindFailure Load Test")
			  .asLongAs(_.isFailed == false, countKey) {
			  	exec(httpScenario(countKey))
				}
		  .inject(constantUsersPerSec(5) during 30.seconds) // 5 users/sec, every second, for X seconds total
	).protocols(httpConf(settings.baseUrl))

	def httpScenario(countKey: String): ChainBuilder = {
		exec(http(session => s"POST - Chunk[]")
			.get("/health")
			.check(status.is(200))
			.check(responseTimeInMillis.lte(RESPONSE_TIME_LIMIT))
		)
	}

}
