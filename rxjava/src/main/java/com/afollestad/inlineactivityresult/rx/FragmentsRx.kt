/**
 * Designed and developed by Aidan Follestad (@afollestad)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("unused")

package com.afollestad.inlineactivityresult.rx

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.afollestad.inlineactivityresult.ActivityResult
import com.afollestad.inlineactivityresult.startActivityForResult
import io.reactivex.Single

/**
 * Starts Activity [T] with extras in [extras], and the given [requestCode]. Emits the result.
 */
inline fun <reified T : Activity> Fragment.startActivityEmitResult(
  extras: Bundle = Bundle(),
  requestCode: Int = 77
): Single<ActivityResult> {
  val intent = Intent(context, T::class.java).putExtras(extras)
  return startActivityEmitResult(
      intent = intent,
      requestCode = requestCode
  )
}

/**
 * Starts an Activity that [intent] resolves to, with the given [requestCode]. Emits the result.
 */
fun Fragment.startActivityEmitResult(
  intent: Intent,
  requestCode: Int = 78
): Single<ActivityResult> {
  return Single.create { emitter ->
    startActivityForResult(
        intent = intent,
        requestCode = requestCode
    ) { success, data ->
      val result = ActivityResult(
          success = success,
          data = data
      )
      emitter.onSuccess(result)
    }
  }
}
