
package slick.jdbc

import com.github.tminglei.slickpg._
import play.api.libs.json.{JsValue, Json}
import slick.basic.Capability

// AUTO-GENERATED FILE, DO NOT MODIFY
// GENERATED TIME: Sun Apr 24 17:27:56 CST 2022
trait PGProfile
    extends ExPostgresProfile
    with PgArraySupport
    with PgEnumSupport
    with PgDate2Support
    with PgRangeSupport
    with PgHStoreSupport
    with PgPlayJsonSupport
    with PgSearchSupport
    with PgPostGISSupport
    with PgNetSupport
    with PgLTreeSupport {

  // jsonb support is in postgres 9.4.0 onward; for 9.3.x use "json"
  def pgjson = "jsonb"

  // pg
  override protected def computeCapabilities: Set[Capability] =
    super.computeCapabilities + slick.jdbc.JdbcCapabilities.insertOrUpdate

  override val api = new MyAPI {}
  val API          = new MyAPI with PlayJsonPlainImplicits {}

  trait MyAPI
      extends API
      with ArrayImplicits
      with DateTimeImplicits
      with JsonImplicits
      with NetImplicits
      with LTreeImplicits
      with RangeImplicits
      with HStoreImplicits
      with SearchImplicits
      with SearchAssistants {
    implicit val strListTypeMapper = new SimpleArrayJdbcType[String]("text").to(_.toList)
    implicit val playJsonArrayTypeMapper =
      new AdvancedArrayJdbcType[JsValue](
        pgjson,
        s => utils.SimpleArrayUtils.fromString[JsValue](Json.parse)(s).orNull,
        v => utils.SimpleArrayUtils.mkString[JsValue](_.toString())(v)
      ).to(_.toList)
  }
}

object PGProfile extends PGProfile
