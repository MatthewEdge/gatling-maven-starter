package edge.poc.sample

import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
 * A sample simulation which is meant to find the breaking point of a
 * POST REST endpoint
 *
 * Created by medge on 5/19/16.
 */
class SampleFindFailure extends Simulation with SampleGatlingConfig {

	val COUNT_KEY = "count"
	def sessionCount(session: Session): Int = session("count").as[Int]

	/**
	 * Scale a chunk number to a chunk range
	 *
	 * @param chunk Chunk number
	 * @return String
	 */
	def scaledChunkSize(chunk: Int) = (chunk + 1) * CHUNK_SCALE_FACTOR

	/**
	 * Return a generated chunk of data of size (chunk + 1) * scaleFactor
	 *
	 * @param chunk Chunk number
	 * @return String
	 */
	def getDataChunkFor(chunk: Int): String = {
		"" // TODO
	}

	val scn = scenario("Ramp until failure")
		.asLongAs(!_.isFailed, COUNT_KEY) {
			exec(http(session => s"POST - Chunk[${sessionCount(session)}]")
				.post("/postTest")
				.body(StringBody(session => getDataChunkFor(sessionCount(session))))
				.check(status.in(200, 204))
				.check(responseTimeInMillis.lessThanOrEqual(RESPONSE_TIME_LIMIT))
			)
		}

	scn
		.inject(increasingUsers)
		.protocols(httpConf)

}
