package com.example.filmflows.data.modelo

import com.example.filmflows.domain.modelo.Movie
import com.example.filmflows.domain.modelo.Series

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title ?: "No title",
        overview = overview ?: "No overview",
        releaseDate = release_date,
        voteAverage = vote_average,
        posterPath = poster_path,
    )
}

fun Movie.toMovieEntity():MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        release_date = releaseDate,
        vote_average = voteAverage,
        poster_path = posterPath,
    )
}

fun SeriesEntity.toSeries() : Series {
    return Series(
        id = id,
        name = name ?: "No title",
        overview = overview ?: "No overview",
        firstAirDate = first_air_date,
        voteAverage = vote_average,
        posterPath = poster_path,
    )
}

fun Series.toSeriesEntity() : SeriesEntity {
    return SeriesEntity(
        id = id,
        name = name,
        overview = overview,
        first_air_date = firstAirDate,
        vote_average = voteAverage,
        poster_path = posterPath,
    )
}