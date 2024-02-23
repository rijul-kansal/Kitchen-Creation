package com.learning.zomatoclone.Model

data class UserProfileModel(
    var name:String?=null,
    var image:String?=null,
    var mobileNumber:String?=null,
    var email:String?=null,
    var interests:ArrayList<InterestModel>?= null
)