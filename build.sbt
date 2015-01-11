name := "forumdatabase"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)     

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.30"

libraryDependencies += "net.sf.flexjson" % "flexjson" % "2.1"



play.Project.playJavaSettings
