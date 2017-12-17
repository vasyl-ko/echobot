val root = Project("echobot", file("."))
  .settings(
    version := "0.1",
    scalaVersion := "2.12.4",
    libraryDependencies ++= Seq(
      "info.mukel" %% "telegrambot4s" % "3.0.14",
      "com.typesafe.akka" %% "akka-actor" % "2.4.19",
      "com.typesafe.akka" %% "akka-testkit" % "2.4.19" % Test
    )
  )
