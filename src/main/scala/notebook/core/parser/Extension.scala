package notebook.core.parser

import notebook.core.parser.impl._
import notebook.implicits.util._

private[core] object Extension {
  
  case object Textile extends Extension(".tt", TextileParser)
  
  case object MediaWiki extends Extension(".wiki", MediaWikiParser)
  
  case object Scaml extends Extension(".scaml", ScamlParser)
  
  val values = Array(Textile, MediaWiki, Scaml)
  
  def lookup(text: String): Option[Extension] = values.find(x => text.endsWith(x.ext))
  
}

private[core] sealed abstract class Extension($ext: String, $parser: Parser) {
  
  val ext = $ext
  
  val parser = $parser
  
  val name = toString
  
}
