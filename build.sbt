name := "notebook"

version := "1.0"

scalaVersion := "2.10.3"

scalacOptions ++= Seq("-deprecation", "-feature")

fork := true

unmanagedResourceDirectories in Compile <+= baseDirectory { base => base/"conf" }

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.10.3"

libraryDependencies += "com.typesafe" % "config" % "1.0.2"

libraryDependencies += "net.java" % "textile-j" % "2.2"

libraryDependencies += "org.fusesource.scalate" % "scalate-core_2.10" % "1.6.1"

libraryDependencies += "commons-io" % "commons-io" % "2.4"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "1.9.2"
