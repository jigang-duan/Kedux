package com.tclcom.oneshop.MovieDemo.Services.Entity

data class MovieEntity (val count: Int, val start: Int, val total: Int, val subjects: List<SubjectsEntity>?, val title: String?)

data class SubjectsEntity (
        val rating: RatingEntity?,
        val genres: List<String>?,
        val title: String? = null,
        val casts: List<CastsEntity>?,
        val collect_count: Int,
        val original_title: String?,
        val subtype: String?,
        val directors: List<DirectorsEntity>?,
        val year: String? = null,
        val images: ImagesEntity?,
        val alt: String?,
        val id: String?
)

data class RatingEntity(val max: Int, val average: Double, val stars: String?, val min: Int)

data class ImagesEntity(val small: String?, val large: String?, val medium: String?)

data class DirectorsEntity(val alt: String?, val avatars: AvatarsEntity?, val name: String?, val id: String?)

data class CastsEntity(val alt: String?, val avatars: AvatarsEntity?, val name: String?, val id: String?)

data class AvatarsEntity(val small: String?, val large: String?, val medium: String?)

