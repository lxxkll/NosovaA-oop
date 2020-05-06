package data

data class Student (
    val firstname: String,
    val surname: String
) {
    override fun toString(): String =
        "$firstname $surname"
}

val studentList =
    arrayOf(
        Student("Анна", "Носова"),
        Student("Анастасия", "Дёмина"),
        Student("Екатерина", "Буйволова")
    )