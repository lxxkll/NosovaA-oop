package component


import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*



val addLessons =
    functionalComponent<RProps> {
        div {
            h3 {
                +"Введите название предмета"
            }
            input(type = InputType.text)
            {
                attrs.id = "subject"
               attrs.placeholder = "При удалении ввод цифры"

            }
        }
    }

fun RBuilder.addLessons(
) = child(
    withDisplayName("lessonsAdd", addLessons)
) {}



