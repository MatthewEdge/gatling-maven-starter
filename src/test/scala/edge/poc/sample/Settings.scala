package edge.poc.sample

object Settings {
  val baseUrl = sys.env.getOrElse("baseUrl", "http://localhost:8080")
  val chunkScaleFactor = Integer.getInteger("chunkScaleFactor", 100).toInt
  val responseTimeLimit = Integer.getInteger("responseTimeLimit", 3000).toInt
}
