package com.organisation.gatepassscanner.dto

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
    var department: String?
)