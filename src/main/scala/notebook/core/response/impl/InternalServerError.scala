package notebook.core.response.impl

import com.sun.net.httpserver.HttpExchange
import notebook.core.response.Responder

private[core] object InternalServerError extends Responder {
  
  override def makeResponse(implicit exchg: HttpExchange) = TextResponse(500, "Internal Server Error")
  
}
