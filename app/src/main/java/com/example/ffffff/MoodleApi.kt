package com.example.ffffff

import retrofit2.http.GET
import retrofit2.http.Query

data class TokenResponse(val token: String)
data class Course(val id: Int, val fullname: String)
data class CourseResponse(val courses: List<Course>)

interface MoodleApi {
    @GET("webservice/rest/server.php")
    suspend fun getToken(
        @Query("wstoken") token: String,
        @Query("wsfunction") function: String = "core_webservice_get_site_info",
        @Query("moodlewsrestformat") format: String = "json"
    ): TokenResponse

    @GET("webservice/rest/server.php")
    suspend fun getCourses(
        @Query("wstoken") token: String,
        @Query("wsfunction") function: String = "core_enrol_get_users_courses",
        @Query("userid") userId: Int,
        @Query("moodlewsrestformat") format: String = "json"
    ): CourseResponse
}
