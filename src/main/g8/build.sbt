import EnvPlugin.autoImport.env
import com.typesafe.sbt.packager.docker.{DockerChmodType, DockerPermissionStrategy}
import play.sbt.PlayImport.PlayKeys._
Global / organization    := "$organization_name$"
ThisBuild / scalaVersion := "$scala_version$"

lazy val api = (project in file("api"))
  .enablePlugins(PlayLayoutPlugin, PlayService, EnvPlugin, AshScriptPlugin)
  .settings(
    Compile / unmanagedResourceDirectories += (Compile / resourceDirectory).value.getParentFile / "common",
    Compile / resourceDirectory            := (Compile / resourceDirectory).value / env.value.toString,
    libraryDependencies ++= Seq(guice,
                                play_slick,
                                argon2,
                                orison,
                                clickhouse,
                                domain_jar,
                                jwt,
                                playTest,
                                scaffeine,
                                mysql,
                                reflections
    ),
    libraryDependencies ++= Seq(slick_dep, scalatest, log, java_mail).flatten,
    dependencyOverrides ++= overrideDeps,
    excludeDependencies ++= excludeDeps,
    Universal / packageZipTarball / universalArchiveOptions :=
      (Seq("--exclude", "*~") ++ (Universal / packageZipTarball / universalArchiveOptions).value),
    includeDocumentationInBinary           := false,
    generateAssetsJar                      := false,
    Compile / packageDoc / publishArtifact := false,
    Universal / mappings := {
      val universalMappings = (Universal / mappings).value
      universalMappings filter { case (_, name) => !Seq("scala-compiler").exists(name.contains) }
    },
    dockerChmodType          := DockerChmodType.UserGroupWriteExecute,
    dockerPermissionStrategy := DockerPermissionStrategy.CopyChown,
    Docker / packageName     := "$image_name$",
    Docker / version         := sys.env.getOrElse("BUILD_NUMBER", (ThisBuild / version).value),
    Docker / daemonUserUid   := None,
    Docker / daemonUser      := "daemon",
    dockerExposedPorts       := Seq(9000),
    dockerBaseImage          := "harbor.hz.zenlayer.net/zendata/openjdk:8-jre-alpine-argon2",
    dockerRepository         := Some("harbor.hz.zenlayer.net/zendata"),
    dockerUpdateLatest       := true
  )

lazy val domain = (project in file("domain"))
  .settings(
    version := "1.0.0",
    organization := "$package$",
    libraryDependencies ++= Seq(slick_dep).flatten,
    libraryDependencies ++= Seq(play_json)
  )
