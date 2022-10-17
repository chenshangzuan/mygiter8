/*
 * Copyright (c) 2020-2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package $package$.controller.extension

import play.api.libs.typedmap.TypedKey

/**
 * RequestExt
 *
 * @author zhaihao
 * @version 1.0
 * @since 2022/3/8 17:33
 */
object RequestExt {
  val userId = TypedKey.apply[Long]("user_id")
}
