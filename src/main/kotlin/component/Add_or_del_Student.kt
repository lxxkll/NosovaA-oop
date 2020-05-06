package component


import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.id
import react.*
import react.dom.*





val addstudent =
    functionalComponent<RProps > {
        div {

            p{
                h3 {
                    +"Введите имя и фамилию студента"
                }
                input(type = InputType.text)
                {
                    attrs.id = "student"
                    attrs.placeholder = "При удалении ввод цифры"
                }
            }

        }

    }


fun RBuilder.addstudent(
) = child(
    withDisplayName("studentsAdd", addstudent)
) {}

