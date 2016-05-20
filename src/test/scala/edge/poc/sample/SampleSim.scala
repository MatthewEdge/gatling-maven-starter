package edge.poc.sample

import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
 * Created by medge on 5/19/16.
 */
class SampleSim extends Simulation with SampleGatlingConfig {

	val scn = scenario("BasicSimulation")
		.exec(http("GET /")
		.get("/"))

	setUp(
		scn.inject(atOnceUsers(1))
	).protocols(httpConf)
}