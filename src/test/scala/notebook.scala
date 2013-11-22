import java.net.URL
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import scala.language.reflectiveCalls
import scala.language.implicitConversions
import org.apache.commons.io.FileUtils
import org.apache.commons.io.input.ProxyInputStream
import org.apache.commons.io.output.ProxyOutputStream

package object notebook {
  
  def ready4test = new java.io.File("./tmp").mkdirs
  
  object TestHelper {
    
    implicit def toInputStream(file: File): InputStream = FileUtils.openInputStream(file)
    implicit def toInputStream(path: String): InputStream = FileUtils.openInputStream(new File(path))
    implicit def toInputStream(url: URL): InputStream = FileUtils.openInputStream(FileUtils.toFile(url))
    
    implicit def toOutputStream(file: File): OutputStream = FileUtils.openOutputStream(file)
    implicit def toOutputStream(path: String): OutputStream = FileUtils.openOutputStream(new File(path))
    implicit def toOutputStream(url: URL): OutputStream = FileUtils.openOutputStream(FileUtils.toFile(url))
    
    type Closable = {
      def close(): Unit
    }
    
    trait StreamProxy[A <: Closable] {
      val stream: A
      private[this] var closed = false
      
      def close(): Unit = {
        closed = true
        stream.close
      }
      
      def isClosed = closed
    }
    
    class InputStreamProxy(is: InputStream, raiseException: Boolean)
        extends ProxyInputStream(is) with StreamProxy[InputStream] {
      
      val stream = is
      
      override def close = super[StreamProxy].close
      
      private def readImpl(read: => Int) = if (raiseException) throw new TestException("read") else read
      
      override def read(): Int = readImpl(super.read)
      
      override def read(bts: Array[Byte]): Int = readImpl(super.read(bts))
      
      override def read(bts: Array[Byte], off: Int, len: Int): Int = readImpl(super.read(bts, off, len))
      
    }
    
    class OutputStreamProxy(os: OutputStream) extends ProxyOutputStream(os) with StreamProxy[OutputStream] {
      val stream = os
      override def close = super[StreamProxy].close
    }
    
    object StreamProxy {
      def newInputStream(is: InputStream, raiseException: Boolean = false) = new InputStreamProxy(is, raiseException)
      def newOutputStream(os: OutputStream) = new OutputStreamProxy(os)
    }
    
    class TestException(message: String) extends RuntimeException(message)
    
  }
  
}
