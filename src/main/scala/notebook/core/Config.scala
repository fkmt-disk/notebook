package notebook.core

import java.io.File
import com.typesafe.config.ConfigFactory
import com.typesafe.config.{Config => Cnf}
import notebook.implicits.config._
import notebook.implicits.util._
import notebook.implicits.io._

object Config {
  
  private[this] val config = {
    val userConf = ConfigFactory.load("application.conf").get[Cnf]("notebook").fold(l => ConfigFactory.empty, r => r)
    val defConf = ConfigFactory.load("application.defaults.conf").get[Cnf]("notebook").get
    userConf.withFallback(defConf).asInstanceOf[Cnf]
  }
  
  val port = config.get[Int]("port").get
  
  val docroot: File = {
    val file = new File(config.get[String]("docroot").get)
    if (file.exists == false) {
      sys.error(s"docroot(${file.getPath}) not found")
      sys.exit(1)
    }
    file.getAbsoluteFile
  }
  
  val backlog = config.get[Int]("backlog").get
  
  val threadpool = config.get[Int]("threadpool").get
  
  val template: String = {
    val file = new File(docroot, config.get[String]("template").get)
    file.getString() match {
      case Left(x) =>
        sys.error(s"template(${file.getPath}) load failed: $x")
        sys.exit(1)
      case Right(x) =>
        x
    }
  }
  
}
