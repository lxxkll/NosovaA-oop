package data

data class Lesson(
    val name: String
) {
    override fun toString(): String = name
}

fun lessonsList() = arrayOf(
    Lesson("Объектно-ориентированное программирование"),
    Lesson("Численные методы"),
    Lesson("Электротехника")
)