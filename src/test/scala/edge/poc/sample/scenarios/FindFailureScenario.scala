package edge.poc.sample.scenarios

import edge.poc.sample.GatlingAppConfig
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
 * A sample simulation which is meant to find the breaking point of a
 * POST REST endpoint
 *
 * Created by medge on 5/19/16.
 */
object FindFailureScenario extends Simulation with GatlingAppConfig {

	val CHUNK_SCALE_FACTOR = settings.chunkScaleFactor
	val RESPONSE_TIME_LIMIT = settings.responseTimeLimit

	def apply(countKey: String) = {
		exec(http(session => s"POST - Chunk[${sessionCount(session, countKey)}]")
			.post("/postTest")
			.body(StringBody(session => getDataChunkFor(sessionCount(session, countKey))))
			.check(status.in(200, 204))
			.check(responseTimeInMillis.lessThanOrEqual(RESPONSE_TIME_LIMIT))
		)
	}

	def sessionCount(session: Session, countKey: String): Int = session(countKey).as[Int]

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
	// TODO
	def getDataChunkFor(chunk: Int): String = {
		val scaledChunkSize = scaledChunkSize(chunk)

		(0 to scaledChunkSize).map { i =>
			s"$i"
		}.mkString("\n")
	}
}
