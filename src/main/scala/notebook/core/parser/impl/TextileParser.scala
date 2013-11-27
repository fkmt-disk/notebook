package notebook.core.parser.impl

import java.io.StringWriter
import java.util.regex.Pattern
import net.java.textilej.parser.MarkupParser
import net.java.textilej.parser.builder.HtmlDocumentBuilder
import net.java.textilej.parser.markup.textile.TextileDialect
import notebook.core.parser.Parser
import notebook.core.parser.Extension

private[core] object TextileParser extends Parser {
  
  private[this] val pattern = Pattern.compile("""(?ms)/(\w+)/>>>(.*)<<<""")
  
  override def parseImpl(text: String): String = {
    
    val sw = new StringWriter
    
    val builder = new HtmlDocumentBuilder(sw)
    builder.setEmitAsDocument(false)
    
    val parser = new MarkupParser(new TextileDialect)
    parser.setBuilder(builder)
    parser.parse( parseEmbedFragment(text) )
    
    sw.toString
  }
  
  private[this] def parseEmbedFragment(text: String) = {
    val sb = new StringBuffer
    val m = pattern.matcher(text)
    
    while (m.find) {
      val ext = m.group(1)
      val term = m.group(2)
      val parsed = Extension.lookup(s".$ext") match {
        case Some(x) =>
          x.parser.parse(term)
        case None =>
          m.group
      }
      m.appendReplacement(sb, parsed)
    }
    
    m.appendTail(sb)
    
    sb.toString
  }
  
}
