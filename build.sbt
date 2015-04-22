name := "base"

version := "0.2.4"

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  Seq(
    "io.buildo"      %% "spray-autoproductformat" % "0.2",
    "io.buildo"      %% "ingredients-logging"    % "0.3",
    "org.slf4j"      %  "slf4j-api"     % "1.7.7",
    "com.typesafe"   %  "config"        % "1.2.1",
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "io.spray"       %% "spray-can"     % sprayV,
    "io.spray"       %% "spray-routing-shapeless2" % sprayV,
    "io.spray"       %% "spray-httpx"   % sprayV,
    "org.scalaz"     %%  "scalaz-core"  % "7.1.1",
    "joda-time"      %  "joda-time"     % "2.3",
    "com.github.nscala-time" %% "nscala-time" % "1.2.0",
    "org.scalatest"  %% "scalatest"     % "2.2.0" % "test",
    "org.mockito"    %  "mockito-all"   % "1.9.5" % "test"
  )
}
