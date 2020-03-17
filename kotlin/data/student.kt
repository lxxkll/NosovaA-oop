package data

import kotlin.browser.document


data class Student(
    val firstname: String,
    val surname: String
)



val studentList =
    arrayListOf(
        Student ("Носова" , "Анна"),
        Student ( "Дёмина", "Анастасия"),
        Student("Буйволова","Екатерина")
    )