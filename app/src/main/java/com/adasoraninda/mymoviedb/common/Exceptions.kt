package com.adasoraninda.mymoviedb.common

class ServerException @JvmOverloads constructor(
    message: String? = null
) : Exception(message = message)

class Failure @JvmOverloads constructor(
    message: String? = null
) : Exception(message = message)
