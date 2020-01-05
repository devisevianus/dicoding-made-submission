package com.stickearn.dicodingmadesubmission1.model

import java.io.Serializable

/**
 * Created by devis on 2019-12-08
 */

class MovieMdl(
    val poster: ByteArray?,
    val title: String?,
    val date: String?,
    val rating: String?,
    val director: String?,
    val overview: String?
) : Serializable