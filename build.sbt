organization  := "io.buildo"

name := "nozzle"

version := "0.6.0-SNAPSHOT"

scalaVersion  := "2.11.7"

scalacOptions := Seq(
  "-unchecked",
  "-deprecation",
  "-encoding",
  "utf8")

resolvers ++= Seq(
  "buildo mvn" at "https://raw.github.com/buildo/mvn/master/releases",
  "bintray buildo/maven" at "http://dl.bintray.com/buildo/maven"
)

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  Seq(
    "io.buildo"      %% "ingredients-logging"    % "0.6.0",
    "io.buildo"      %% "ingredients-jsend"      % "0.4.0",
    "com.typesafe.akka" %% "akka-actor"          % akkaV,
    "io.spray"       %% "spray-can"              % sprayV,
    "io.spray"       %% "spray-routing-shapeless2" % sprayV,
    "io.spray"       %% "spray-httpx"            % sprayV,
    "org.scalaz"     %% "scalaz-core"            % "7.2.0"
  )
}

Boilerplate.settings

