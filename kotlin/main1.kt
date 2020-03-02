import data.Student
import data.studentList
import kotlinx.html.*
import kotlinx.html.InputType.radio


import kotlinx.html.dom.append
import kotlinx.html.js.*
import kotlinx.html.js.li
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement

import org.w3c.dom.events.Event


import kotlin.browser.document
import kotlin.dom.clear






var ascending = true




fun main() {
    document.getElementById("root")!!
        .append {

            h2 {
                attributes += "id" to "header"
                +"Сортировка"
                onClickFunction = onCLickFunction()
            }
            h1 {
                +"Список студентов"
            }

            ol {
                //attributes += "style" to "color:gray"
                attributes += "id" to "listStudents"
                studentList.map {
                    li {
                        attributes += "id" to it.firstname
                        +"${it.firstname} ${it.surname}"
                        onClickFunction = onClick2(it)
                    }
                }
            }

            p{
                input {
                    type =  radio
                    name = "color"
                    value = "blue"
                    onClickFunction = colorchange(value)
                }
                +"Blue"
                br
                input{
                    type =  radio
                    name = "color"
                    value = "red"
                    onClickFunction = colorchange(value)
                    }
                +"Red"
                br
                input{
                    type =  radio
                    name = "color"
                    value = "Yellow"
                    onClickFunction = colorchange(value)
                }
                +"Yellow"
            }
        }}




private fun colorchange(value: String): (Event) -> Unit {
    return {
        // val color = document.getElementById("value")!! as HTMLInputElement
        val div = document.getElementById("root")!!
        div.setAttribute("style", "color:${value}")
    }
}


private fun LI.onClick2(Student: Student): (Event) -> Unit {
    return {
        val student = document.getElementById(Student.firstname)!!
        if (Student.pris){
            student.setAttribute("style","color:pink")
            Student.pris = false
        }
        else
        {
            student.setAttribute("style","color:gray")
            Student.pris = true
        }
    }
}


private fun H2.onCLickFunction(): (Event) -> Unit {
    return {

        val listStudents = document.getElementById("listStudents")!!
        listStudents.clear()
        listStudents.append {
            if (ascending)

                studentList.sortBy { it.firstname }
            else
                studentList.sortByDescending { it.firstname }
            ascending = !ascending
            studentList.map {
                li {
                    attributes += "id" to it.firstname
                    +"${it.firstname} ${it.surname}"
                    onClickFunction = onClick2(it)
                }
            }
        }
    }
}


