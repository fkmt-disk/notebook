package notebook.implicits

import scala.util.control.Exception._
import scala.language.reflectiveCalls

package object control {
  
  private[this] type Closable = {
    def close(): Unit
  }
  
  implicit class ClosableResource[A <: Closable](resource: A) {
    def using[B](block: (A) => B): Either[Throwable, B] = allCatch andFinally {
      resource.close
    } either {
      block(resource)
    }
  }
  
}
