package com.azrinurvani.imagevista.domain.repository

interface Downloader {

    fun downloadFile(url : String, fileName: String?)
}