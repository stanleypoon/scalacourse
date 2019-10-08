import sbt.Keys.sourceDirectory

// coverageMinimum := 50
// coverageFailOnMinimum := true
// coverageExcludedPackages := "<empty>;xyz.*;.*abc.*;aaa\\.bbb\\..*"
// javaOptions in Test ++= Seq("-Xmx12g", "-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n")


lazy val commonSettings = Seq(
  version := "0.1.0",
  scalaVersion := "2.11.8",
  // turn on coverage
  // to generage report
  //   > sbt PROJECT/test and then 
  //   > sbt PROJECT/coverageReport
  coverageEnabled := true,
  EclipseKeys.withSource := true,
  // parallelExecution in test := false,
  test in assembly := {},
  assemblyMergeStrategy in assembly := {
   case PathList("META-INF", xs @ _*) => MergeStrategy.discard
   case x => MergeStrategy.first
  }
)

lazy val all = project
  .in(file("."))
  .settings(commonSettings)
  .disablePlugins(AssemblyPlugin)
  .aggregate(
    common_util,
    applications
  )

lazy val common_util = project
  .in(file("common_util"))
  .settings(
    name := "common_util",
    commonSettings,
    libraryDependencies ++= common_util_dep
  )

lazy val applications = project
   .in(file("applications"))
   .settings(
     name :=  "applications",
     commonSettings,
    libraryDependencies ++= common_util_dep
   )
   .dependsOn(
       common_util
   )

lazy val common_util_dep = Seq(

//  "org.apache.spark" % "spark-sql_2.11" % "2.2.1",
//  "org.apache.spark" % "spark-core_2.11" % "2.2.1",
//  "org.apache.spark" % "spark-mllib_2.11" % "2.2.1",
  
//*********** test only ****************
  "org.mockito" % "mockito-core" % "1.8.5" % "test",
  "junit" % "junit" % "4.10" % "test",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.4" % "test")
