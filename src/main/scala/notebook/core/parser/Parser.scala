package notebook.core.parser

import scala.util.control.Exception._

private[core] abstract class Parser {
  
  def parse(text: String): String = (catching(classOf[Exception]) either parseImpl(text)).fold(l => l.toString, r => r)
  
  protected def parseImpl(text: String): String
  
}
