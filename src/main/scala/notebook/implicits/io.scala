package notebook.implicits

import java.io.File
import java.io.InputStream
import java.io.OutputStream
import scala.util.control.Exception._
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import notebook.implicits.control._

package object io {
  
  implicit class FileWrapper(file: File) {
    
    def getString(charset: String = "utf-8"): Either[Throwable, String] = try {
      FileUtils.openInputStream(file).using { fis =>
        IOUtils.toString(fis, charset)
      }
    }
    catch {
      case t: Throwable => Left(t)
    }
    
  }
  
  implicit class OutputStreamWrapper(os: OutputStream) {
    
    def <(is: InputStream): Either[Throwable, _] = allCatch andFinally {
      IOUtils.closeQuietly(os)
      IOUtils.closeQuietly(is)
    } either {
      IOUtils.copy(is, os)
    }
    
  }
  
}
