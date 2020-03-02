package data

import kotlin.browser.document


data class Student(
    val firstname: String,
    val surname: String,
    var pris: Boolean
)



val studentList =
    arrayListOf(
        Student ("Носова" , "Анна",true),
        Student ( "Дёмина", "Анастасия",true),
        Student("Буйволова","Екатерина",true)
    )