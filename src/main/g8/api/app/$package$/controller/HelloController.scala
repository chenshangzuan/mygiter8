/*
 * Copyright (c) 2020-2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package $package$.controller

import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

/**
  * ReportController
  *
  * @author kled
  * @version 1.0
  * @since 2022/6/23 11:13
  */
@Singleton
class HelloController @Inject()(cc: ControllerComponents)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def hello(keyword: String) = Action.async {
    Future { Ok }
  }
}
