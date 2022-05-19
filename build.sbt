name := "poquemaune"
scalaVersion := "3.1.1"

enablePlugins(ScalaNativePlugin)

nativeMode := "debug" // "release-full"
nativeLTO := "none" // "thin"

// SFML dependency
nativeLinkingOptions := Seq(
	"-lcsfml-audio",
	"-lcsfml-graphics", 
	"-lcsfml-network",
	"-lcsfml-system",
	"-lcsfml-window"
)

resolvers += Resolver.githubPackages("lafeychine")
libraryDependencies += "io.github.lafeychine" %%% "scala-native-sfml" % "0.1.0"

githubTokenSource := TokenSource.GitConfig("github.token")
