val swing = "org.scala-lang.modules" %% "scala-swing" % "3.0.0"

scalaVersion := "3.1.1"

lazy val root = (project in file(".")).settings(
    name := "My Project",
    libraryDependencies += swing,
    Compile / unmanagedJars += file("lib/tablelayout.jar")
)
