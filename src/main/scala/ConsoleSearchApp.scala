
object ConsoleSearchApp extends App {
  try {
    val dictionaryFilesRoot = scala.io.Source.fromFile(args(0), "utf-8")

  } catch {
    case e: Exception => println("an exception occurred: " + e.getMessage)
  }
}
