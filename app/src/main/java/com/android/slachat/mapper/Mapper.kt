package com.android.slachat.mapper

interface Mapper<in InputModel, out OutputModel> {
    fun mapModel(model: InputModel): OutputModel
}