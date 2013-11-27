package notebook.core.parser.impl

import java.io.File
import org.fusesource.scalate.TemplateEngine
import notebook.core.parser.Parser

private[core] object JadeParser extends Parser {
  
  private[this] val engine = {
    val engine = new TemplateEngine
    engine.workingDirectory = new File("./tmp")
    engine.allowReload = true
    engine.allowCaching = false
    engine
  }
  
  override def parseImpl(text: String) = engine.layout(engine.compileText("jade", text).source)
  
}
