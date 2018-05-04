package imdb.assignment.umesh0492.data.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class SearchDataClass(val Search: List<MoviesDataClass>, val totalResults: String)
