package data




data class Student(
    val firstname: String,
    val surname: String
)



val studentList =
    arrayOf(
        Student ("Носова" , "Анна"),
        Student ( "Дёмина", "Анастасия"),
        Student("Буйволова","Екатерина")
    )