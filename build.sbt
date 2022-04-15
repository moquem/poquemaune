name := "poquemaune"
scalaVersion := "3.1.1"

enablePlugins(ScalaNativePlugin)

nativeMode := "debug" // "release-full"
nativeLTO := "none" // "thin"
