package notebook.core.response

import com.sun.net.httpserver.HttpExchange

private[core] trait Responder {
  
  def makeResponse(implicit exchg: HttpExchange): Response
  
}
