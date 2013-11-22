package notebook.core.response.impl

import com.sun.net.httpserver.HttpExchange
import notebook.core.response.Responder

private[core] object NotFound extends Responder {
  
  override def makeResponse(implicit exchg: HttpExchange) = TextResponse(404, "Not Found")
  
}
