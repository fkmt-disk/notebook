package notebook.core.parser

import scala.util.control.Exception._

private[core] abstract class Parser {
  
  val pattern = """(?ms)/(\w+)/>>>(.*?)<<<""".r
  
  def parse(text: String): String = catching(classOf[Exception]).either {
    val sb = new StringBuilder
    
    val idx = pattern.findAllMatchIn(text).foldLeft(0) { (i, m) =>
      sb append parseImpl(text.slice(i, m.start))
      sb append Extension.lookup(s".${m.group(1)}").map( _.parser.parse(m group 2) ).getOrElse(m.matched)
      m.end
    }
    
    sb append parseImpl(text.substring(idx))
    
    sb.toString
  }.fold(l => l.toString, r => r)
  
  protected def parseImpl(text: String): String
  
}
