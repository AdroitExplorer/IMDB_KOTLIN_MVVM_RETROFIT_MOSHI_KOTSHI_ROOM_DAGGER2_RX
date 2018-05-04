package imdb.assignment.umesh0492.util.rx

import io.reactivex.Scheduler

interface SchedulerProvider{
    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}
