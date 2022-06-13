package com.example.trendingnow.utils

data class DataSource<out T>(val status:Status, val dataset: T?, val message: String? ){

    companion object {

        fun <T> success(data: T?): DataSource<T> {
            return DataSource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): DataSource<T> {
            return DataSource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): DataSource<T> {
            return DataSource(Status.LOADING, data, null)
        }

    }
}
