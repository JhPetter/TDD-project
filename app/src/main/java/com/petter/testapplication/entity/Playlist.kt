package com.petter.testapplication.entity

data class Playlist(
    var id: Int,
    var name: String,
    var category: String,
    var image: Int,
    var description: String = ""
)