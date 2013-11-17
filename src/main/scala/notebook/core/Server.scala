package notebook.core

import java.net.InetSocketAddress
import java.util.concurrent.Executors
import java.util.concurrent.ExecutorService
import com.sun.net.httpserver.HttpServer
import com.sun.net.httpserver.HttpExchange
import notebook.core.Config._

object Server {
  
  def start {
    val server = HttpServer.create(new InetSocketAddress(port), backlog)
    server.createContext("/", new Handler)
    server.setExecutor(Executors.newFixedThreadPool(threadpool))
    server.start
    
    println(s"notebook server started on port=$port")
    println("shutdown by 'GET /shutdown'")
  }
  
  def stop(implicit exchg: HttpExchange) {
    exchg.getHttpContext.getServer.stop(3)
    exchg.getHttpContext.getServer.getExecutor.asInstanceOf[ExecutorService].shutdownNow
    
    println("shutdown")
  }
  
}
