package com.learning.zomatoclone.Model.Categories

data class Category(
    var idCategory: String? = null, // 1
    var strCategory: String? = null, // Beef
    var strCategoryDescription: String? = null, // Beef is the culinary name for meat from cattle, particularly skeletal muscle. Humans have been eating beef since prehistoric times.[1] Beef is a source of high-quality protein and essential nutrients.[2]
    var strCategoryThumb: String? = null // https://www.themealdb.com/images/category/beef.png
)