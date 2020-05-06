package data

data class Lesson(
    val name: String
){
    override fun toString(): String = name
}

val lessonsList = arrayOf(
    Lesson("Объектно-ориентированное программирование"),
    Lesson("Технологии обработки информации"),
    Lesson("Электротехника")
)