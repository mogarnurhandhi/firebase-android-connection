package com.example.firebase_databaseproject

class Users {
    var inputNama: String? = null
    var inputTanggal: String? = null
    var inputjnsKelamin: String? = null
    var inputNoTelepon: String? = null
    var inputEmail: String? = null
    var inputPassword: String? = null

    constructor(){}
    constructor(
        inputNama: String?,
        inputTanggal: String?,
        inputjnsKelamin: String?,
        inputNoTelepon: String?,
        inputEmail: String?,
        inputPassword: String?
    ) {
        this.inputNama = inputNama
        this.inputTanggal = inputTanggal
        this.inputjnsKelamin = inputjnsKelamin
        this.inputNoTelepon = inputNoTelepon
        this.inputEmail = inputEmail
        this.inputPassword = inputPassword
    }
}