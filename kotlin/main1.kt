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
                    +"Комедия"
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
