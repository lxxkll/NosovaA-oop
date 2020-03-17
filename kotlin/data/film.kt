package data



data class film(
    val name : String,
    val cast : String,
    val date: Int,
    var prosm : Boolean
)


val RomanList =
    arrayListOf(
        film ("Танцуй сердцем","Райан Бенсетти, Алексия Джордано",2019, true),
        film ("В метре друг от друга","Хейли Лу Ричардсон, Коул Спроус",2019,true),
        film("Просто добавь романтики","Мэган Фэйхи, Люк Макфарлейн",2019,true)

    )

val ComedyList =
    arrayListOf(
        film ("Одноклассники","Адам Сэндлер, Кевин Джеймс",2010,true),
        film ("Та ещё парочка","Шарлиз Терон, Сет Роген",2019,true),
        film("Однажды в... Голивуде","Леонардо Ди Каприо, Брэд Питт",2019,true)

    )

