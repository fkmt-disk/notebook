package notebook.core.response

private[core] object ContentType {
  
  case object Css extends ContentType(".css", "text/css")
  case object JavaScript extends ContentType(".js", "text/javascript")
  case object Html extends ContentType(".html", "text/html")
  case object Jpeg extends ContentType(".jpg", "image/jpeg")
  case object Png extends ContentType(".png", "image/png")
  case object Gif extends ContentType(".gif", "image/gif")
  case object Other extends ContentType("", "application/octet-stream")
  
  val values = Array(Css, JavaScript, Html, Jpeg, Png, Gif, Other)
  
  def find(text: String): ContentType = values.find(x => text.endsWith(x.ext)).get
  
}

private[core] sealed abstract class ContentType($ext: String, $contentType: String) {
  
  val ext = $ext
  
  val contentType = $contentType
  
  val name = toString
  
}
