package notebook.implicits

import scala.language.implicitConversions

package object util {
  
  implicit def either2option[R](e: Either[_, R]): Option[R] = e.fold(l => None, r => Some(r))
  
  implicit def toOption[A](x: A): Option[A] = Some(x)
  
}
