package notebook.implicits

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.EitherValues._
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigException.{WrongType, Missing}
import notebook.implicits.config._

class configspec extends FlatSpec with ShouldMatchers {
  
  notebook.ready4test
  
  
  val config = ConfigFactory.load("test.conf").getConfig("test")
  
  
  behavior of "ConfigWrapper#get[...]"
  
  it should "return Left[Missing], when value is absent" in {
    config.get[String]("absent").left.value.getClass shouldEqual classOf[Missing]
  }
  
  it should "return Left[WrongType], when actual-type and expected-type is different" in {
    config.get[Int]("text").left.value.getClass shouldEqual classOf[WrongType]
  }
  
  it should "return Left[WrongType], when actual-type is Long, but expected-type is Int" in {
    config.get[Int]("num").left.value.getClass shouldEqual classOf[WrongType]
  }
  
  it should "return Left[UnsupportedOperationException], when expect-type is unsupported" in {
    config.get[java.util.Date]("date").left.value.getClass shouldEqual classOf[UnsupportedOperationException]
  }
  
  it should "return Right[Boolean]" in {
    config.get[Boolean]("bool").right.value.getClass shouldEqual classOf[Boolean]
  }
  
  it should "return Right[List[Boolean]]" in {
    // reflectionでどうにかならんか...
    val klass = config.get[List[Boolean]]("bools").right.get(0).getClass
    assert(classOf[Boolean] isAssignableFrom klass)
  }
  
  it should "return Right[Config]" in {
    assert(classOf[Config] isAssignableFrom config.get[Config]("conf").right.value.getClass)
  }
  
  it should "return Right[List[Config]]" in {
    val klass = config.get[List[Config]]("confs").right.get(0).getClass
    assert(classOf[Config] isAssignableFrom klass)
  }
  
  it should "return Right[Double]" in {
    config.get[Double]("numD").right.value.getClass shouldEqual classOf[Double]
  }
  
  it should "return Right[List[Double]]" in {
    val klass = config.get[List[Double]]("numDs").right.get(0).getClass
    assert(classOf[Double] isAssignableFrom klass)
  }
  
  it should "return Right[Int]" in {
    config.get[Int]("numI").right.value.getClass shouldEqual classOf[Int]
  }
  
  it should "return Right[List[Int]]" in {
    val klass = config.get[List[Int]]("numIs").right.get(0).getClass
    assert(classOf[Int] isAssignableFrom klass)
  }
  
  it should "return Right[Long]" in {
    config.get[Long]("numL").right.value.getClass shouldEqual classOf[Long]
  }
  
  it should "return Right[List[Long]]" in {
    val klass = config.get[List[Long]]("numLs").right.get(0).getClass
    assert(classOf[Long] isAssignableFrom klass)
  }
  
  it should "return Right[String]" in {
    config.get[String]("text").right.value.getClass shouldEqual classOf[String]
  }
  
  it should "return Right[List[String]]" in {
    val klass = config.get[List[String]]("texts").right.get(0).getClass
    assert(classOf[String] isAssignableFrom klass)
  }
  
}
