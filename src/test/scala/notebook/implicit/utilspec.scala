package notebook.implicits

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import notebook.implicits.util._

class utilspec extends FlatSpec with ShouldMatchers {
  
  notebook.ready4test
  
  
  behavior of "either2option"
  
  it should "return Some[x], when applied to Right[x]" in {
    either2option(Right("test")) shouldEqual Some("test")
  }
  
  it should "return None, when applied to Left[_]" in {
    either2option(Left("test")) shouldEqual None
  }
  
  it should "implicitly apply to Right[_]" in {
    val opt: Option[String] = Right("test")
    opt shouldEqual Some("test")
  }
  
  it should "implicitly apply to Left[_]" in {
    val opt: Option[String] = Left("test")
    opt shouldEqual None
  }
  
  
  behavior of "toOption"
  
  it should "return Some[any], when applied to any-value" in {
    toOption(1) shouldEqual Some(1)
    toOption(true) shouldEqual Some(true)
    toOption("text") shouldEqual Some("text")
    toOption(Seq(1,2,3)) shouldEqual Some(Seq(1,2,3))
  }
  
  it should "implicitly apply to any-value" in {
    val int: Option[Int] = 1
    val bool: Option[Boolean] = true
    val text: Option[String] = "text"
    val seq: Option[Seq[Int]] = Seq(1,2,3)
    int shouldEqual Some(1)
    bool shouldEqual Some(true)
    text shouldEqual Some("text")
    seq shouldEqual Some(Seq(1,2,3))
  }
  
}
