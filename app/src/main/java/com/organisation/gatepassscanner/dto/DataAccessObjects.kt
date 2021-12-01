package com.organisation.gatepassscanner.dto

import com.organisation.gatepassscanner.R

data class StaffDetails (
    var id: String,
    var phoneNumber: String,
    var fullName: String,
    var emailAddress: String,
    var imageUrl: String,
    var department:String,
    var position:String,
)
data class LoginStatus(
    var isLoggedIn : Boolean,
    var roles: String?
)
data class UserLogin (
    val username: String,
    val password: String
)

enum class UrlData(var message: Int) {
    BASE_URL(R.string.base_url),
}

data class ErrorDetails(
    val details: String
)
data class LoginResponse(

    val accessToken:String,
    val userId:String,
    val firstName:String,
    val emailAddress:String,
    val lastName:String,
    val phoneNumber:String,
    val imageUrl:String,
    val roles:List<String>,
    val administration: DbAdministration?
)
data class DbAdministration(
    val departmentName: String?,
    val positionName: String?,
    val timezoneName: String?
)