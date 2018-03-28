
name := "JDictLib"

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.5",
  "com.al333z" % "anti-xml_2.12" % "0.7.11",
  "com.chuusai" %% "shapeless" % "2.3.2",
  "com.jsuereth" %% "scala-arm" % "2.0",
  "commons-io" % "commons-io" % "2.6"
)

// akka
lazy val akkaHttpVersion = "10.0.9"
lazy val akkaVersion    = "2.5.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream"          % akkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
  "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % Test)