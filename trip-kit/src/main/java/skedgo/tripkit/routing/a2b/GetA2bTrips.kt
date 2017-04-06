package skedgo.tripkit.routing.a2b

import com.skedgo.android.common.model.Location
import com.skedgo.android.common.model.Query
import com.skedgo.android.common.model.TimeTag
import com.skedgo.android.common.model.TripGroup
import com.skedgo.android.tripkit.RouteService
import org.joda.time.DateTime
import rx.Observable
import skedgo.tripkit.routing.a2b.RequestTime.*
import java.util.concurrent.TimeUnit

open class GetA2bTrips internal constructor(
    private val routeService: RouteService
) {
  open fun execute(request: A2bRoutingRequest): Observable<List<TripGroup>>
      = Observable
      .fromCallable {
        val origin = Location(request.origin.first, request.origin.second)
        origin.address = request.originAddress

        val destination = Location(request.destination.first, request.destination.second)
        destination.address = request.destinationAddress

        val query = Query()
        query.fromLocation = origin
        query.toLocation = destination
        val time = request.time
        when (time) {
          is DepartAfter -> query.setTimeTag(TimeTag.createForLeaveAfter(time.dateTime.toSeconds()))
          is ArriveBefore -> query.setTimeTag(TimeTag.createForArriveBy(time.dateTime.toSeconds()))
          is DepartNow -> query.setTimeTag(TimeTag.createForLeaveAfter(time.getNow().toSeconds()))
        }
        query
      }
      .flatMap { routeService.routeAsync(it) }
}

internal fun DateTime.toSeconds() = TimeUnit.MILLISECONDS.toSeconds(millis)