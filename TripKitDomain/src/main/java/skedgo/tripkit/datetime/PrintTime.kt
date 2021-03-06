package skedgo.tripkit.datetime

import org.joda.time.DateTime
import rx.Observable

/**
 * An UseCase to print time with respect of 24-hour
 * (or 12-hour) setting on users' device.
 */
interface PrintTime {
  fun execute(dateTime: DateTime): Observable<String>
}
