/*
 * Copyright (c) 2020-2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package $package$

import $package$.controller.exception.BizSuccess
import play.api.libs.json.{JsPath, JsValue, Json, OWrites, Writes}
import play.api.mvc.Results.Ok
import types.|._

import scala.concurrent.{ExecutionContext, Future}

/**
  * package
  *
  * @author zhaihao
  * @version 1.0
  * @since 2022/1/24 18:41
  */
package object controller {

  implicit class FutureUnionExt(a: Future[JsValue] | Future[Vector[JsValue]] | Future[Option[JsValue]])(implicit
      ec:                          ExecutionContext
  ) {
    def ok = a.asInstanceOf[Future[_]].map {
      case data: JsValue                    => Ok(data)
      case data: Vector[JsValue @unchecked] => Ok(Json.toJson(data))
      case data: Option[JsValue @unchecked] => Ok(Json.toJson(data))
    }
  }

  implicit val bizSuccessFormat = Json.format[BizSuccess]
}
