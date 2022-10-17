/*
 * Copyright (c) 2020-2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package $package$.controller.exception

import $package$.filter.ColorLog
import com.typesafe.scalalogging.StrictLogging
import console.Colors
import play.api._
import play.api.http.DefaultHttpErrorHandler
import play.api.libs.json._
import play.api.mvc.Results._
import play.api.mvc._

import javax.inject._
import scala.concurrent.Future

/** ErrorHandle
 *
 * @author zhaihao
 * @version 1.0
 * @since 2021/12/12 00:09
 */
@Singleton
class ErrorHandle @Inject() (env: Environment)(implicit configuration: Configuration)
    extends DefaultHttpErrorHandler
    with StrictLogging {

  import $package$.filter.ColorLog._

  override def onServerError(request: RequestHeader, exception: Throwable) = {
    // server 端会解析两次 jwt，暂时无法避免
    val userInfo = extractJwt(request).toString()
    logger.error(
      s"request id \${Colors.blue(request.id.toString)} user \${Colors.magenta(userInfo)} occurs server exception:",
      exception
    )

    exception match {
      case e: BizError =>
        log(request, 299, userInfo)
        Future.successful(Results.Status(299)(Json.obj("message" -> e.getMessage)))
      case e: JsResultException =>
        log(request, 400, userInfo)
        val errField = e.errors.head
        if (env.mode == Mode.Prod) {
          Future.successful(BadRequest(Json.obj("message" -> "请求 json 不正确")))
        } else {
          Future.successful(
            BadRequest(Json.obj("message" -> (errField._2.head.messages.head + ": " + errField._1.toString())))
          )
        }
      case e: Exception =>
        if (env.mode == Mode.Prod) {
          Future.successful(InternalServerError(Json.obj("message" -> "服务器内部错误")))
        } else {
          Future.successful(InternalServerError(Json.obj("message" -> e.getMessage)))
        }

    }
  }

  override def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    statusCode match {
      case 404 =>
        Future.successful(
          Results.Status(statusCode)(Json.obj("message" -> s"\${request.uri} Not Found"))
        )

      case 415 =>
        Future.successful(
          Results.Status(statusCode)(
            Json.obj("message" -> s"\${request.headers.get("Content-Type")} not support")
          )
        )

      case _ => Future.successful(Results.Status(statusCode)(Json.obj("message" -> message)))
    }
  }

}
