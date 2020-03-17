Интерфес представлен примерно и показана работа одного пункта меню



  Часть main отвечает за вывод меню на экран

 ```Kotlin
   import data.*
import kotlinx.html.LI
import kotlin.browser.document
import kotlinx.html.dom.append
import kotlinx.html.js.*
import kotlinx.html.li
import kotlinx.html.ol
import org.w3c.dom.events.Event
import kotlin.dom.clear


fun main() {
    document.getElementById("root")!!
        .append {

            h1 {
                +"Меню"
            }

            ol {
                //attributes += "style" to "color:gray"
                attributes += "id" to "Listmenu"
                MenuList.map {
                    li {
                        attributes += "id" to it.name
                        + it.name
                        onClickFunction = onClick(it)
                    }
                }
            }
        }
}
```

Данная функция реализует нажатие на пункт меню (первый пункт) и переход к следущему "окну" для выбора жанра

```Kotlin
private fun LI.onClick(menu: menu) : (Event) -> Unit {
    return {

        val MenuList = document.getElementById("Listmenu")!!
        MenuList.clear()
        MenuList.append {
        if (menu.cod == 1)
        {
            h1 {
                +"Жанры"
            }
            ol {
                //attributes += "style" to "color:gray"
                attributes += "id" to "genreList"
                genreList.map {
                    li {
                        attributes += "id" to it.name
                        + it.name
                        onClickFunction = onClick2(it)
                    }
                }
            }
        }

    }
}}

```

Данная функция реализует нажатие на пункт меню (первый пункт) и переход к следущему "окну" для выбора фильма


```Kotlin
private fun LI.onClick2(genre: Genre) : (Event) -> Unit {
    return {

        val genreList = document.getElementById("genreList")!!
        genreList.clear()
        genreList.append {
            if (genre.cod == 1) {
                h1 {
                +"Романтика"
            }
                ol {
                    attributes += "id" to "Romanfilm"
                    RomanList.map {
                        li {
                            attributes += "id" to it.name
                            +it.name
                            onClickFunction = onClick3(it)
                        }
                    }
                }
            } else {
                h1 {
                +"Комдия"
            }
                ol {
                    attributes += "id" to "Comedyfilm"
                    ComedyList.map {
                        li {
                            attributes += "id" to it.name
                            +it.name
                        }
                    }
                }
            }

        }
    }
}

```
Данная функция отвечает за окрашивание выбраного фильма (без дальнейшей возможности отметить как желаемое к просмотру)

```Kotlin
private fun LI.onClick3(film: film) : (Event) -> Unit {
    return {
        val films = document.getElementById(film.name)!!
        if (film.prosm){
            films.setAttribute("style","color:pink")
            film.prosm = false
            attributes += "id" to "film"


        }
        else
            film.prosm = true

    }
}
```


На рисунке 1 представлено основное меню

<img src = 1.jpg>

На рисунке 2 представлено меню жанров при нажатии на первый пункт общего меню

<img src = 2.jpg>

На рисунке 3 редставлен выбор фильмов определенного жанра

<img src = 33.jpg>