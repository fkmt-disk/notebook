package notebook.core.response.impl

import java.io.File
import com.sun.net.httpserver.HttpExchange
import notebook.core.response.Responder
import notebook.core.parser.Extension
import notebook.core.parser.Parser
import notebook.core.Config
import notebook.implicits.io._

private[core] object Contents extends Responder {
  
  override def makeResponse(implicit exchg: HttpExchange) = {
    val file = new File(Config.docroot, exchg.getRequestURI.getPath)
    Extension.lookup(file.getName) match {
      case Some(x) => TextResponse(200, markup(file, x.parser))
      case None    => FileResponse(200, file)
    }
  }
  
  private[this] def markup(file: File, parser: Parser) = file.getString() match {
    case Right(x) => Config.template.replaceAll("""\{content\}""", parser.parse(x))
    case Left(x)  => s"parse failed: ${x.toString}"
  }
  
}
