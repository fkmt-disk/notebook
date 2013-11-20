package notebook.implicits

import java.io._
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.EitherValues._
import org.apache.commons.io.FileUtils
import notebook.implicits.io._

class iospec extends FlatSpec with ShouldMatchers {
  
  notebook.ready4test
  import notebook.TestHelper._
  
  
  val TEXT = "表申能予"
  
  def toFile(url: String) = FileUtils.toFile(ClassLoader.getSystemResource(url))
  
  
  behavior of "FileWrapper#getString"
  
  it should "return Left[FileNotFoundException], when file not found" in {
    val file = new File("./tmp/iospec.txt") match {
      case x if x.exists => x.delete; x
      case x             => x
    }
    
    val either = new FileWrapper(file).getString()
    
    either.left.value.getClass shouldEqual classOf[FileNotFoundException]
  }
  
  it should "return Left[IOException], when applied to directory" in {
    val dir = new File("./tmp")
    
    val either = new FileWrapper(dir).getString()
    
    either.left.value.getClass shouldEqual classOf[IOException]
  }
  
  it should "return file contents (encoded by utf-8) wrapped by Right[String]" in {
    val file = toFile("iospec.utf8.txt")
    
    val either = new FileWrapper(file).getString()
    
    // vimでやるとわざわざnoeolしないと改行ついてしまうのでtirm。
    // 改行の有無はこのテストケースの主題ではないので無視する方向で（以下同様）。。。
    either.right.value.trim shouldEqual TEXT
  }
  
  it should "return file contents (encoded by euc-jp) wrapped by Right[String]" in {
    val file = toFile("iospec.eucjp.txt")
    
    val either = new FileWrapper(file).getString("euc-jp")
    
    either.right.value.trim shouldEqual new String(TEXT.getBytes("euc-jp"), "euc-jp")
  }
  
  it should "return file contents (encoded by sjis) wrapped by Right[String]" in {
    val file = toFile("iospec.sjis.txt")
    
    val either = new FileWrapper(file).getString("sjis")
    
    either.right.value.trim shouldEqual new String(TEXT.getBytes("sjis"), "sjis")
  }
  
  it should "implicitly apply to File" in {
    val file = toFile("iospec.utf8.txt")
    val either = file.getString()
    either.right.value.trim shouldEqual TEXT
  }
  
  
  behavior of "OutputStreamWrapper#redirect"
  
  it should "call #close, when any-code-block is successfully executed" in {
    val in = StreamProxy.newInputStream(toFile("iospec.utf8.txt"))
    val out = StreamProxy.newOutputStream("./tmp/iospec1.txt")
    
    val either = new OutputStreamWrapper(out) < in
    
    either.isRight shouldEqual true
    in.isClosed shouldEqual true
    out.isClosed shouldEqual true
  }
  
  it should "call #close, when any-code-block is aborted" in {
    val in = StreamProxy.newInputStream(toFile("iospec.utf8.txt"), true)
    val out = StreamProxy.newOutputStream("./tmp/iospec2.txt")
    
    val either = new OutputStreamWrapper(out) < in
    
    either.left.value.getClass shouldEqual classOf[TestException]
    in.isClosed shouldEqual true
    out.isClosed shouldEqual true
  }
  
  it should "redirect from in-file to out-file" in {
    val in = StreamProxy.newInputStream(toFile("iospec.utf8.txt"))
    val out = StreamProxy.newOutputStream("./tmp/iospec3.txt")
    
    val either = new OutputStreamWrapper(out) < in
    
    either.isRight shouldEqual true
    in.isClosed shouldEqual true
    out.isClosed shouldEqual true
    
    val result = FileUtils.readFileToString(new File("./tmp/iospec3.txt"))
    result.trim shouldEqual TEXT
  }
  
  it should "implicitly apply to OutputStream" in {
    val in = StreamProxy.newInputStream(toFile("iospec.utf8.txt"))
    val out = StreamProxy.newOutputStream("./tmp/iospec3.txt")
    
    val either = out < in
    
    either.isRight shouldEqual true
    in.isClosed shouldEqual true
    out.isClosed shouldEqual true
    
    val result = FileUtils.readFileToString(new File("./tmp/iospec3.txt"))
    result.trim shouldEqual TEXT
  }
  
}
