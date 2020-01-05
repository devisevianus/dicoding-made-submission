package com.stickearn.dicodingmadesubmission1.base

/**
 * Created by devis on 2020-01-05
 */

sealed class BaseViewState<out R> {
    object Loading: BaseViewState<Nothing>()
    data class Error(val errorMessage: String?): BaseViewState<Nothing>()
    data class Success<T>(val data: T?): BaseViewState<T>()
}