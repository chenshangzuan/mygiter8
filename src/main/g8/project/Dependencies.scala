/*
 * Copyright (c) 2021.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

import sbt._

/**
  * Dependencies
  *
  * @author zhaihao
  * @version 1.0
  * @since 2021/12/12 02:09
  */
object Dependencies extends AutoPlugin {
  override def requires = empty
  override def trigger  = allRequirements

  object autoImport {

    lazy val domain_jar      = "com.zenlayer.zdata.api"             %% "domain"                     % "1.0.0"
    lazy val orison          = "me.ooon"                %% "orison"                     % "0.1.9"
    lazy val nscala          = "com.github.nscala-time" %% "nscala-time"                % "2.30.0"
    lazy val os_lib          = "com.lihaoyi"            %% "os-lib"                     % "0.7.7"
    lazy val squants         = "org.typelevel"          %% "squants"                    % "1.7.4"
    lazy val typesafe_config = "com.typesafe"            % "config"                     % "1.4.1"
    lazy val argon2          = "de.mkammerer"            % "argon2-jvm"                 % "2.11"
    lazy val play_json       = "com.typesafe.play"      %% "play-json"                  % "2.9.2"
    lazy val requests        = "com.lihaoyi"            %% "requests"                   % "0.6.9"
    lazy val par             = "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.3"
    lazy val hikari          = "com.zaxxer"              % "HikariCP"                   % "4.0.3"
    lazy val pg              = "org.postgresql"          % "postgresql"                 % "42.2.23"
    lazy val scaffeine       = "com.github.blemale"     %% "scaffeine"                  % "4.1.0"
    lazy val play_slick      = "com.typesafe.play"      %% "play-slick"                 % "5.0.2"
    lazy val jwt             = "com.github.jwt-scala"   %% "jwt-play"                   % "9.0.5"
    lazy val mysql           = "mysql"                   % "mysql-connector-java"       % "8.0.29"
    lazy val reflections     = "org.reflections"         % "reflections"                % "0.10.2"
    lazy val clickhouse      = "com.github.housepower"   % "clickhouse-native-jdbc"     % "2.6.5"

    lazy val slick_dep = Seq(
      "com.typesafe.slick"  %% "slick"              % "3.3.3",
      "com.github.tminglei" %% "slick-pg"           % "0.20.3",
      "com.github.tminglei" %% "slick-pg_play-json" % "0.20.3",
      "com.github.tminglei" %% "slick-pg_jts_lt"    % "0.20.3",
      "com.typesafe.slick"  %% "slick-hikaricp"     % "3.3.3"
    )

    lazy val log = Seq(
      "org.slf4j"                   % "log4j-over-slf4j" % "1.7.36",
      "com.typesafe.scala-logging" %% "scala-logging"    % "3.9.5",
      "ch.qos.logback"              % "logback-classic"  % "1.2.11"
    )

    lazy val scalatest = Seq(
      "org.scalatest" %% "scalatest-core"           % "3.2.12" % Test,
      "org.scalatest"  % "scalatest-compatible"     % "3.2.12" % Test,
      "org.scalatest" %% "scalatest-diagrams"       % "3.2.12" % Test,
      "org.scalatest" %% "scalatest-matchers-core"  % "3.2.12" % Test,
      "org.scalatest" %% "scalatest-shouldmatchers" % "3.2.12" % Test,
      "org.scalatest" %% "scalatest-freespec"       % "3.2.12" % Test
    )

    lazy val java_mail = Seq(
      "javax.mail"   % "javax.mail-api" % "1.6.2",
      "com.sun.mail" % "javax.mail"     % "1.6.2"
    )

    lazy val overrideDeps = Seq(
      "org.scala-lang.modules" %% "scala-xml" % "2.0.1"
    )

    lazy val excludeDeps = Seq(
//      "org.scala-lang"           % "scala-compiler",
      "org.seleniumhq.selenium"  % "htmlunit-driver",
      "org.seleniumhq.selenium"  % "selenium-support",
      "org.seleniumhq.selenium"  % "selenium-api",
      "org.seleniumhq.selenium"  % "selenium-remote-driver",
      "org.seleniumhq.selenium"  % "selenium-firefox-driver",
      "org.fluentlenium"         % "fluentlenium-core",
      "com.google.code.findbugs" % "jsr305",
      "com.google.protobuf"      % "protobuf-java",
      "com.novocode"             % "junit-interface",
      "junit"                    % "junit",
      "org.slf4j"                % "slf4j-log4j12",
      "log4j"                    % "log4j"
//      "com.google.guava"         % "guava",
//      "com.typesafe.play"       %% "twirl-api",
//      "com.typesafe.play"       %% "play-akka-http-server",
//      "com.typesafe.play"       %% "play-streams",
//      "org.javassist"            % "javassist",

    )
  }

}
