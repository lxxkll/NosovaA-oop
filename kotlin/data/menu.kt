package data

data class menu(
    val name : String,
    val cod : Int
)

val MenuList =
    arrayListOf(
        menu("Список всех фильмов",1),
        menu("Фильмы к просмотру",2),
        menu("Просмотренные фильмы",3)
    )