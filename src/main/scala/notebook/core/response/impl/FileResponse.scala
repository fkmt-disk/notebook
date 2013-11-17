package notebook.core.response.impl

import java.io.File
import com.sun.net.httpserver.HttpExchange
import org.apache.commons.io.FileUtils
import notebook.core.response.Response
import notebook.core.response.ContentType

private[core] case class FileResponse(code: Int, content: File)(implicit exchg: HttpExchange) extends Response(code) {
  
  val contentType = ContentType.find(content.getName).contentType
  
  val contentLength = content.length
  
  override def openStream = FileUtils.openInputStream(content)
  
}
