package edge.poc.sample

import com.typesafe.config._

import scala.reflect.ClassTag

/**
 * Abstraction over Typesafe's Config object which reads in application-specific properties into typed variables.
 * Keeps settings keys in one place for easier maintenance
 *
 * @author medge
 */
object Settings {

	val env = sys.props.get("env").map(_.toUpperCase).getOrElse("DEV")

	// Get the appropriate environment-based config based on the env prop
	lazy val config = ConfigFactory.load().getConfig(env)

	def apply() = new Settings(config)

}

/**
 * Attempts to extract relevant App-specific configurations to type-safe variables
 * for consumption by client classes
 *
 * @param config Config
 */
class Settings(config: Config) extends RichConfig(config) {

	// FindFailure Settings
	val chunkScaleFactor = get[Int]("app.chunkScaleFactor").getOrElse(1000)
	val responseTimeLimit = get[Int]("app.response.limit.millis").getOrElse(3000) // 3 seconds

}

/**
 * Enhancements to Typesafe's Config object inspired by their own documentation
 *
 * @param config Config
 */
class RichConfig(config: Config) {

	/**
	 * Get the value at the given key, attempt to cast to the given type, and then wrap in an Option
	 *
	 * @param key String
	 * @tparam T Type to attempt to cast to
	 * @return Option[T] - None if the key is not found
	 */
	def get[T : ClassTag](key: String): Option[T] = {
		try {
			val result = config.getAnyRef(key).asInstanceOf[T]

			Some(result)
		} catch {
			// Not Found
			case failed: ConfigException.Missing => None
		}
	}

	/**
	 * Get the value at the given key and attempt to cast it to the given type.
	 *
	 * Note: this is a more strict version of get[T] because it throws a SettingsException if the key is not found
	 *
	 * @param key Config path to required property
	 * @tparam T Type to attempt to cast value to
	 * @return T
	 * @throws SettingsException if the key could not be found or casting to the given type failed
	 */
	@throws[SettingsException]
	def getRequired[T : ClassTag](key: String): T = get[T](key) match {
		case Some(obj) => obj
		case None => throw new SettingsException(s"Key $key not found in any configuration file!")
	}

}

/**
 * Exception for Settings-related failures
 *
 * @param message String
 * @param cause Throwable
 */
class SettingsException(message: String, cause: Throwable = null) extends Exception(message, cause)
