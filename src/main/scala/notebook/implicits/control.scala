package notebook.implicits

import scala.util.control.Exception._
import scala.language.reflectiveCalls

package object control {
  
  private[this] type Closable = {
    def close(): Unit
  }
  
  implicit class ClosableWrapper[A <: Closable](closable: A) {
    def using[B](block: (A) => B): Either[Throwable, B] = catching(classOf[Exception]) andFinally {
      closable.close
    } either {
      block(closable)
    }
  }
  
}
