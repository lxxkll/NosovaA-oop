package data

data class Student(
    val firstname: String,
    val surname: String
) {
    override fun toString(): String =
        "$firstname $surname"
}

fun studentList() =
    arrayOf(
        Student("Анна","Носова"),
        Student("Анастасия", "Демина"),
        Student("Екатерина", "Буйволова")
    )