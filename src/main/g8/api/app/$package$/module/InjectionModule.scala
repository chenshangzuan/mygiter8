/*
 * Copyright (c) 2020-2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package $package$.module

import com.google.inject.AbstractModule
import play.api.inject.Injector

import javax.inject.{Inject, Singleton}

/** InjectionModule
  *
  * 获取 play 创建的 injector
  *
  * @author zhaihao
  * @version 1.0
  * @since 2021/12/12 01:02
  */
class InjectionModule extends AbstractModule {
  override def configure() = {
    bind(classOf[GlobalContext]).asEagerSingleton()
  }
}

@Singleton
class GlobalContext @Inject() (playBuiltinInjector: Injector) {
  GlobalContext.injectorRef = playBuiltinInjector
}

object GlobalContext {
  private var injectorRef: Injector = _

  def injector: Injector = injectorRef
}
