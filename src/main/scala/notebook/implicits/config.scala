package notebook.implicits

import scala.util.control.Exception._
import scala.reflect.runtime.universe._
import scala.collection.JavaConverters._
import com.typesafe.config.Config

package object config {
  
  implicit class ConfigWrapper(config: Config) {
    def get[A: TypeTag](key: String): Either[Throwable, A] = typeOf[A] match {
      case typ if typ =:= typeOf[Boolean] =>
        catching(classOf[Exception]).either( config.getBoolean(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[List[Boolean]] =>
        catching(classOf[Exception]).either( config.getBooleanList(key).asScala.toList.asInstanceOf[A] )
      case typ if typ =:= typeOf[Config] =>
        catching(classOf[Exception]).either( config.getConfig(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[List[Config]] =>
        catching(classOf[Exception]).either( config.getConfigList(key).asScala.toList.asInstanceOf[A] )
      case typ if typ =:= typeOf[Double] =>
        catching(classOf[Exception]).either( config.getDouble(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[List[Double]] =>
        catching(classOf[Exception]).either( config.getDoubleList(key).asScala.toList.asInstanceOf[A] )
      case typ if typ =:= typeOf[Int] =>
        catching(classOf[Exception]).either( config.getInt(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[List[Int]] =>
        catching(classOf[Exception]).either( config.getIntList(key).asScala.toList.asInstanceOf[A] )
      case typ if typ =:= typeOf[Long] =>
        catching(classOf[Exception]).either( config.getLong(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[List[Long]] =>
        catching(classOf[Exception]).either( config.getLongList(key).asScala.toList.asInstanceOf[A] )
      case typ if typ =:= typeOf[String] =>
        catching(classOf[Exception]).either( config.getString(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[List[String]] =>
        catching(classOf[Exception]).either( config.getStringList(key).asScala.toList.asInstanceOf[A] )
      case typ =>
        Left(new UnsupportedOperationException(s"unsupported type: $typ"))
    }
  }
  
}
