package imdb.assignment.umesh0492.network

interface NetworkCallListener<T> {
    fun onFailure()
    fun onSuccess(data: T)

}
