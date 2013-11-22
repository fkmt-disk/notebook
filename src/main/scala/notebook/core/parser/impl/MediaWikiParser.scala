package notebook.core.parser.impl

import java.io.StringWriter
import net.java.textilej.parser.MarkupParser
import net.java.textilej.parser.builder.HtmlDocumentBuilder
import net.java.textilej.parser.markup.mediawiki.MediaWikiDialect
import notebook.core.parser.Parser

private[core] object MediaWikiParser extends Parser {
  
  override def parseImpl(text: String): String = {
    val sw = new StringWriter
    
    val builder = new HtmlDocumentBuilder(sw)
    builder.setEmitAsDocument(false)
    
    val parser = new MarkupParser(new MediaWikiDialect)
    parser.setBuilder(builder)
    parser.parse(text)
    
    sw.toString
  }
  
}
