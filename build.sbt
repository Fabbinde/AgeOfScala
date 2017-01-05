name := "Age of Scala"

version := "1.1.0"

scalaVersion := "2.11.8"
 
 resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
 
 libraryDependencies ++= Seq(
	 "com.typesafe" % "config" % "1.2.1",
	 "com.github.nscala-time" %% "nscala-time" % "2.12.0",
	 "com.typesafe.akka" %% "akka-actor" % "2.4.4",
	 "com.lambdaworks" %% "jacks" % "2.3.3",
	 "com.typesafe.play" % "play_2.11" % "2.5.3",
	 "org.scalafx" %% "scalafx" % "8.0.102-R11",
	 "junit" % "junit" % "4.4" % "test",
	 "org.scalactic" %% "scalactic" % "3.0.1",
	 "org.scalatest" %% "scalatest" % "3.0.1" % "test",
	 "org.specs2" %% "specs2-core" % "3.8.6" % "test"
 )
 

scalacOptions in Test ++= Seq(
	"-Yrangepos"
)

fork in run := true