package notebook.core.parser

import java.io.StringWriter
import net.java.textilej.parser.MarkupParser
import net.java.textilej.parser.builder.HtmlDocumentBuilder
import net.java.textilej.parser.markup.textile.TextileDialect
import net.java.textilej.parser.markup.mediawiki.MediaWikiDialect

private[core] object Parser {
  
  object Textile extends Parser {
    override def parse(text: String): String = {
      val sw = new StringWriter
      
      val builder = new HtmlDocumentBuilder(sw)
      builder.setEmitAsDocument(false)
      
      val parser = new MarkupParser(new TextileDialect)
      parser.setBuilder(builder)
      parser.parse(text)
      
      sw.toString
    }
  }
  
  object MediaWiki extends Parser {
    override def parse(text: String): String = {
      val sw = new StringWriter
      
      val builder = new HtmlDocumentBuilder(sw)
      builder.setEmitAsDocument(false)
      
      val parser = new MarkupParser(new MediaWikiDialect)
      parser.setBuilder(builder)
      parser.parse(text)
      
      sw.toString
    }
  }
  
  object Scaml extends Parser {
    override def parse(text: String): String = {
      // TODO
      text
    }
  }
  
}

private[core] sealed abstract class Parser {
  
  def parse(text: String): String
  
}
