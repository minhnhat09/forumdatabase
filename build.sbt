import com.github.play2war.plugin._

name := "forumdatabase"

version := "1.0-SNAPSHOT"

Play2WarPlugin.play2WarSettings

Play2WarKeys.servletVersion := "3.0"



libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)     

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.30"

libraryDependencies += "net.sf.flexjson" % "flexjson" % "2.1"

libraryDependencies += "com.github.play2war" % "play2-war-core-common_2.10" % "1.2"

play.Project.playJavaSettings
