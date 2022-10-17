import sbt._
import sbtrelease.ReleaseStateTransformations._

credentials       += Credentials(Path.userHome / ".sbt" / "sonatype_credential")
publishMavenStyle := true
licenses          := Seq("MPL2" -> url("https://www.mozilla.org/en-US/MPL/2.0/"))

releaseCrossBuild           := true
releaseIgnoreUntrackedFiles := true
releaseTagComment           := s"🔖 release ${(ThisBuild / version).value}"
releaseCommitMessage        := s"🔖 release ${(ThisBuild / version).value}"
releaseNextCommitMessage    := s"🚧 prepare ${(ThisBuild / version).value}"

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  releaseStepTask(Project("api", file("api")) / Docker / publish),
  commitReleaseVersion,
  tagRelease,
  setNextVersion,
  commitNextVersion
)
