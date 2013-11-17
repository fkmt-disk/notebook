package notebook.core.response

import java.io.InputStream
import com.sun.net.httpserver.HttpExchange
import notebook.implicits.io._

private[core] abstract class Response(code: Int)(implicit exchg: HttpExchange) {
  
  val contentType: String
  
  val contentLength: Long
  
  def openStream: InputStream
  
  def send = try {
    exchg.getResponseHeaders.set("Content-Type", contentType)
    exchg.sendResponseHeaders(code, contentLength)
    (exchg.getResponseBody < openStream) match {
      case Right(_) => Right(s"$code")
      case Left(t)  => Left(t)
    }
  } catch {
    case t: Throwable => Left(t)
  }
  
}
