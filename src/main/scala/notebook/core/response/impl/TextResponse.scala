package notebook.core.response.impl

import com.sun.net.httpserver.HttpExchange
import org.apache.commons.io.IOUtils
import notebook.core.response.Response
import notebook.core.response.ContentType

private[core] case class TextResponse(code: Int, content: String)(implicit exchg: HttpExchange) extends Response(code) {
  
  val contentType = s"${ContentType.Html.contentType};charset=utf-8"
  
  val contentLength: Long = content.getBytes.length
  
  override def openStream = IOUtils.toInputStream(content)
  
}
