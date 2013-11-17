package notebook.implicits

import scala.util.control.Exception._
import scala.reflect.runtime.universe._
import com.typesafe.config.Config

package object config {
  
  implicit class ConfigWrapper(config: Config) {
    def get[A: TypeTag](key: String): Either[Throwable, A] = typeOf[A] match {
      case typ if typ =:= typeOf[Boolean] =>
        allCatch.either( config.getBoolean(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[List[Boolean]] =>
        allCatch.either( config.getBooleanList(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[Config] =>
        allCatch.either( config.getConfig(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[List[Config]] =>
        allCatch.either( config.getConfigList(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[Double] =>
        allCatch.either( config.getDouble(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[List[Double]] =>
        allCatch.either( config.getDoubleList(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[Int] =>
        allCatch.either( config.getInt(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[List[Int]] =>
        allCatch.either( config.getIntList(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[Long] =>
        allCatch.either( config.getLong(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[List[Long]] =>
        allCatch.either( config.getLongList(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[String] =>
        allCatch.either( config.getString(key).asInstanceOf[A] )
      case typ if typ =:= typeOf[List[String]] =>
        allCatch.either( config.getStringList(key).asInstanceOf[A] )
      case typ =>
        Left(new UnsupportedOperationException(s"unsupported type: $typ"))
    }
  }
  
}
