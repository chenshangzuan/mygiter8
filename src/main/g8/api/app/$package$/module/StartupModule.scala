/*
 * Copyright (c) 2020-2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package $package$.module

import com.google.inject.AbstractModule
import com.typesafe.scalalogging.StrictLogging

import java.time.LocalDate
import javax.inject.{Inject, Singleton}

/** StartupModule
  *
  * play application context 启动之前执行，port 尚未绑定， 开发模式略有不同，port 是先于 play application context 前绑定的。
  *
  * @author zhaihao
  * @version 1.0
  * @since 2021/12/12 00:54
  */
class StartupModule extends AbstractModule {
  override def configure() = {
    bind(classOf[Startup]).asEagerSingleton()
  }
}

@Singleton
class Startup @Inject() (lifecycle: ApplicationLifecycle, env: Environment, config: Configuration)
    extends StrictLogging {
  // format: off
  val logo =
    s"""
       |\${white("/*")}                                                                         \${white("")}\${white("*\\\\")}
       |\${white("**")}                  \${RB_YELLOW(".__")}                                                    \${white("**")}
       |\${white("**")}    \${RB_RED("____")}  \${RB_ORANGE("_______ ")}\${RB_YELLOW("|__|")}  \${RB_GREEN("______")}  \${RB_INDIGO("____")}    \${RB_VIOLET("____")}     ORISON.                 \${white("**")}
       |\${white("**")}   \${RB_RED("/  _ \\\\")} \${RB_ORANGE("\\\\_  __ \\\\")}\${RB_YELLOW("|  |")} \${RB_GREEN("/  ___/")} \${RB_INDIGO("/  _ \\\\")}  \${RB_VIOLET("/    \\\\")}    (c) 2017-\${LocalDate.now.getYear}           \${white("**")}
       |\${white("**")}  \${RB_RED("(  <_> )")} \${RB_ORANGE("|  | \\\\/")}\${RB_YELLOW("|  |")} \${RB_GREEN("\\\\___ \\\\")} \${RB_INDIGO("(  <_> )")}\${RB_VIOLET("|   |  \\\\")}   https://orison.ooon.me  \${white("**")}
       |\${white("**")}   \${RB_RED("\\\\____/")}  \${RB_ORANGE("|__|")}   \${RB_YELLOW("|__|")}\${RB_GREEN("/____  >")} \${RB_INDIGO("\\\\____/")} \${RB_VIOLET("|___|  /")}   Play Mode : \${RB_YELLOW(env.mode.toString.padEnd(4, ' '))}        \${white("**")}
       |\${white("**")}                          \${RB_GREEN("\\\\/")}              \${RB_VIOLET("\\\\/")}     Config Env: \${RB_ORANGE(config.get[String]("env").capitalize.padEnd(4, ' '))}        \${white("**")}
       |\${white("**")}\${"".padEnd(73, ' ')}\${white("**")}
       |\${white("\\\\*")}\${"".padEnd(73, ' ')}\${white("*/")}
       |""".stripMargin
  // format: off
  logo.split("\n").foreach(line => logger.info(line))
  logger.info("")
}
