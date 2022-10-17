/*
 * Copyright (c) 2020-2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package $package$.utils

import com.github.blemale.scaffeine.{AsyncLoadingCache, LoadingCache, Scaffeine}
import play.api.routing.Router
import play.api.test.FakeRequest

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

/** Cache
 * 不使用 play cache 有两个考虑，
 * 一个是在 play 外使用，
 * 一个是 play cache 没有提供 Caffeine 基于 size 的控制，只有时间控制
 * object 的在play 外使用，class 的在play中使用 guice 注入
 *
 * @author zhaihao
 * @version 1.0
 * @since 2021/12/12 01:15
 */
object Cache {
//    private val userRepo: UserRepository = GlobalContext.injector.instanceOf[UserRepository]

}

@Singleton
class PlayCacheWithProvider @Inject() (router: Router) {
  val optionCache: LoadingCache[String, List[String]] = Scaffeine()
    .maximumSize(2000)
    .build((uri: String) => {
      for (m <- methodList if router.handlerFor(FakeRequest(m, s"/\$uri")).isDefined) yield m
    })
  private val methodList = List("GET", "POST", "PUT", "DELETE", "PATCH")
}

@Singleton
class PlayCache @Inject() (implicit ec: ExecutionContext) {
  val settings: AsyncLoadingCache[String, String] = Scaffeine()
    .maximumSize(100)
    .buildAsyncFuture((key: String) => {
      Future(key)
    })

}
