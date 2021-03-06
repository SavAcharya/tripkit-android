package skedgo.tripkit.datetime

import android.content.Context
import android.text.format.DateFormat
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import rx.Observable
import rx.schedulers.Schedulers.computation
import java.util.*

internal class PrintTimeImpl constructor(
    private val context: Context
) : PrintTime {
  private val patternFor24Hour = "H:mm"
  private val patternFor12Hour = "h:mm a"

  override fun execute(dateTime: DateTime): Observable<String>
      = Observable
      .fromCallable {
        val formatter = if (DateFormat.is24HourFormat(context)) {
          DateTimeFormat.forPattern(patternFor24Hour).withLocale(Locale.getDefault())
        } else {
          DateTimeFormat.forPattern(patternFor12Hour).withLocale(Locale.getDefault())
        }
        formatter.print(dateTime)
      }
      .subscribeOn(computation())
}