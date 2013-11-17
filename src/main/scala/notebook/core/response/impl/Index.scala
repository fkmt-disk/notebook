package notebook.core.response.impl

import java.io.File
import com.sun.net.httpserver.HttpExchange
import org.apache.commons.io.filefilter.FileFilterUtils
import org.apache.commons.io.filefilter.IOFileFilter
import notebook.core.response.Responder
import notebook.core.Config

private[core] object Index extends Responder {
  
  private[this] def filter(filter: IOFileFilter)(fileset: File*) = FileFilterUtils.filter(filter, fileset:_*)
  private[this] val filterDirs = filter(FileFilterUtils.directoryFileFilter) _
  private[this] val filterFiles = filter(FileFilterUtils.fileFileFilter) _
  
  override def makeResponse(implicit exchg: HttpExchange) = {
    val basedir = new File(Config.docroot, exchg.getRequestURI.getPath)
    val fileset = basedir.listFiles
    
    val dirs = filterDirs(fileset).sorted
    val files = filterFiles(fileset).sorted
    
    val html = (
      <div>
        <h3>{if(basedir.getName.length==0) "(root)" else basedir.getName}</h3>
        <ul>
          { dirs.map( f => <li><a href={s"${basedir.getName}/${f.getName}"}>{s"${f.getName}/"}</a></li>) }
          { files.map(f => <li><a href={s"${basedir.getName}/${f.getName}"}>{f.getName}</a></li>) }
        </ul>
      </div>
    ).toString
    
    TextResponse(200, Config.template.replaceAll("""\{content\}""", html))
  }
  
}
