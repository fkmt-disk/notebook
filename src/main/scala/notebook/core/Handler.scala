package notebook.core

import java.io.File
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpExchange
import notebook.core.response.impl._

class Handler extends HttpHandler {
  
  override def handle(exchg: HttpExchange) = handleImpl(exchg)
  
  private[this] def handleImpl(implicit exchg: HttpExchange) {
    println(s"[${Thread.currentThread.getName}] ${exchg.getRequestMethod} ${exchg.getRequestURI}")
    
    val status = exchg.getRequestURI.getPath match {
      case "/shutdown" =>
        Server.stop
        Right("shutdown")
      case x if new File(Config.docroot, x).exists == false =>
        NotFound.makeResponse.send
      case x if new File(Config.docroot, x).isFile =>
        Contents.makeResponse.send
      case x if new File(Config.docroot, x).isDirectory =>
        Index.makeResponse.send
      case _ =>
        InternalServerError.makeResponse.send
    }
    
    println(s"[${Thread.currentThread.getName}] => $status")
  }
  
}
