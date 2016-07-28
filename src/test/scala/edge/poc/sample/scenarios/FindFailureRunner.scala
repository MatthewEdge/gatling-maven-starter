package edge.poc.sample.scenarios

import edge.poc.sample.GatlingAppConfig
import io.gatling.core.Predef._

import scala.concurrent.duration._

/**
 * @author medge
 */
trait FindFailureRunner extends Simulation with GatlingAppConfig{

	val baseUrl = settings

}

/**
 * Load Test Runner for the FindFailure Scenario
 */
class FindFailureLoadTest extends FindFailureRunner {

	val countKey = "count"

	setUp(
		scenario("FindFailure Load Test")
			  .asLongAs(_.isFailed == false, countKey) {
			  	exec(FindFailureScenario(countKey))
				}
		  .inject(constantUsersPerSec(5) during 10.seconds) // 5 users/sec, every second, for 10 seconds total (a.k.a 50 users total)
	)

}
