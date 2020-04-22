package data



data class Subject(
    val name: String
)
{
    override fun toString(): String = name
}


val subjectList =
    arrayOf(
        Subject ("Объектно-ориентированное программирование"),
        Subject("Технологии обработки информации"),
        Subject("Электротехника")

    )