/*
 * Copyright (c) 2020-2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package $package$.filter

import akka.stream.Materializer
import $package$.controller.extension.RequestExt
import com.typesafe.scalalogging.StrictLogging
import console.Colors
import pdi.jwt.JwtSession._
import play.api.Configuration
import play.api.http.Status.UNAUTHORIZED
import play.api.libs.json.Json
import play.api.libs.typedmap.TypedKey
import play.api.mvc.Results.Unauthorized
import play.api.mvc.{Filter, RequestHeader, Result}

import java.time.Clock
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

/**
 * LogTokenFilter
 *
 * @author zhaihao
 * @version 1.0
 * @since 2021/12/17 21:44
 */
@Singleton
class LogTokenFilter @Inject() (implicit val mat: Materializer, ec: ExecutionContext, config: Configuration)
    extends Filter
    with StrictLogging {

  import ColorLog._

  private val withoutAuthUrl = "/api/v1/login"
  implicit val clock         = Clock.systemUTC

  override def apply(nextFilter: RequestHeader => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    val startTime = System.currentTimeMillis

    val idAndName = extractJwt(requestHeader)
    val newReq    = requestHeader.addAttr(RequestExt.userId, idAndName._1)
    val userInfo  = idAndName.toString()

    def filterCombine = {
      nextFilter(newReq).map { result =>
        val endTime     = System.currentTimeMillis
        val requestTime = endTime - startTime
        val id          = newReq.id.toString

        logger.info(
          s"request id \${Colors.blue(id)} user \${Colors.magenta(userInfo)} \${Colors
            .yellow(newReq.method)} \${Colors
            .cyan(newReq.uri)} took \${colorCost(requestTime)} returned \${ColorLog.color(result.header.status)}"
        )
        result.withHeaders("Request-Time" -> (requestTime.toString + " ms"), "Request-Id" -> id)
      }
    }

    if (newReq.uri == withoutAuthUrl || newReq.method == "OPTIONS") {
      filterCombine
    } else if (idAndName._1 == 0) {
      log(newReq, UNAUTHORIZED, userInfo)
      Future.successful(Unauthorized(Json.obj("message" -> "用户未登陆或token已失效")))
    } else {
      filterCombine
    }
  }

}

object ColorLog extends StrictLogging {
  implicit val clock: Clock = Clock.systemUTC()

  def extractJwt(requestHeader: RequestHeader)(implicit configuration: Configuration): (Long, String) = {
    val jwt    = requestHeader.jwtSession
    val userId = jwt.getAs[Long]("id").getOrElse(0L)
    val name   = jwt.getAs[String]("name").getOrElse("")
    (userId, name)
  }

  def log(request: RequestHeader, status: Int, userInfo: String) = {
    logger.error(
      s"request id \${Colors.blue(request.id.toString)} user \${Colors.magenta(userInfo)} \${Colors
        .yellow(request.method)} \${Colors
        .cyan(request.uri)} returned \${ColorLog.color(status)}"
    )
  }

  def color(status: Int): String = {
    if (status < 300) Colors.green(status.toString)
    else if (status < 400) Colors.yellow(status.toString)
    else if (status < 500) Colors.magenta(status.toString)
    else Colors.red(status.toString)
  }

  def colorCost(time: Long): String = {
    if (time < 300) Colors.green(time.toString + " ms")
    else if (time < 600) Colors.yellow(time.toString + " ms")
    else if (time < 900) Colors.magenta(time.toString + " ms")
    else Colors.red(time.toString + " ms")
  }
}
