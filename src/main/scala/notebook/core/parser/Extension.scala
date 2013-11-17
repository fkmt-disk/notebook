package notebook.core.parser

import notebook.implicits.util._

private[core] object Extension {
  
  case object Textile extends Extension(".tt", Parser.Textile)
  
  case object MediaWiki extends Extension(".wiki", Parser.MediaWiki)
  
  case object Scaml extends Extension(".scaml", Parser.Scaml)
  
  val values = Array(Textile, MediaWiki, Scaml)
  
  def lookup(text: String): Option[Extension] = text match {
    case x if x.endsWith(Textile.ext)   => Textile
    case x if x.endsWith(MediaWiki.ext) => MediaWiki
    case x if x.endsWith(Scaml.ext)     => Scaml
    case _                              => None
  }
  
}

private[core] sealed abstract class Extension($ext: String, $parser: Parser) {
  
  val ext = $ext
  
  val parser = $parser
  
  val name = toString
  
}
