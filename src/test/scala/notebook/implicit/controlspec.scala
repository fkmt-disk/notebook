package notebook.implicits

import java.io.OutputStream
import java.io.FileOutputStream
import java.io.IOException
import scala.language.implicitConversions
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.EitherValues._
import notebook.implicits.control._

class controlspec extends FlatSpec with ShouldMatchers {
  
  notebook.ready4test
  import notebook.TestHelper._
  
  
  behavior of "ClosableWrapper#using"
  
  it should "call #close, when any-code-block is successfully executed" in {
    val out = StreamProxy.newOutputStream("./tmp/controlspec1.txt")
    
    val either = new ClosableWrapper(out).using { o => "success" }
    
    either.right.value shouldEqual "success"
    out.isClosed shouldEqual true
  }
  
  it should "call #close, when any-code-block is aborted" in {
    val out = StreamProxy.newOutputStream("./tmp/controlspec2.txt")
    
    val either = new ClosableWrapper(out).using { o => throw new TestException("test") }
    
    either.left.value.getClass shouldEqual classOf[TestException]
    either.left.value.getMessage shouldEqual "test"
    out.isClosed shouldEqual true
  }
  
  it should "implicitly apply to any-closable-resource" in {
    val out = StreamProxy.newOutputStream("./tmp/controlspec3.txt")
    
    val either = out.using { o => "success" }
    
    either.right.value shouldEqual "success"
    out.isClosed shouldEqual true
  }
  
}
