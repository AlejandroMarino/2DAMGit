package com.example.filmflows.common

object Constantes {

    //API
    const val BASE_URL = "https://api.themoviedb.org"
    const val apiKey = "api_key"
    const val API_KEY = "618c7a82620e0c4450b437bc5b602fd5"


    //DB
    const val appDb = "app.db"
    const val version = 1
    const val movieTable = "movies"
    const val seriesTable = "series"

    //img
    const val imageUrl = "https://image.tmdb.org/t/p/original"

    //app

    const val idMovie = "idMovie"
    const val idSeries = "idSeries"

    //queries

    const val queryGetMovies = "SELECT * FROM movies order by vote_average DESC"
    const val queryGetMovie = "SELECT * FROM movies WHERE id = :id"
    const val queryGetSeries = "SELECT * FROM series order by vote_average DESC"
    const val queryGetASeries = "SELECT * FROM series WHERE id = :id"

    //replies
    const val movieId = "movie_id"
    const val seriesId = "series_id"

    const val getPopularMovies = "/3/movie/popular"
    const val getMovie = "/3/movie/{movie_id}"

    const val getPopularSeries = "/3/tv/popular"
    const val getSeries = "/3/tv/{series_id}"
}