package edge.poc.sample

/**
 * Created by medge on 5/19/16.
 */
trait SampleConfig {

	// Extract ENVIRONMENT variables
	val CHUNK_SCALE_FACTOR = sys.props.get("chunk.scale.factor").map(_.toInt).getOrElse(1000)

	val RESPONSE_TIME_LIMIT = sys.props.get("response.threshold.ms").map(_.toInt).getOrElse(60000)

}
