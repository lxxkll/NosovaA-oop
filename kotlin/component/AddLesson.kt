package component


import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.*
import org.w3c.dom.HTMLInputElement

import react.*
import react.dom.*
import kotlin.browser.document



interface AppLessonProps : RProps {
    var newsubject: (String) -> Any
}



val addLessons =
    functionalComponent<AppLessonProps> { props ->
        div {
            h3 {
                +"Введите название предмета"
            }
            input(type = InputType.text, name = "inp_1")
            {
                attrs.id = "subject"

            }

            input(type = InputType.submit, name = "inp_2") {
                attrs.onClickFunction =
                    {
                        val subject = document.getElementById("subject")
                                as HTMLInputElement
                        val tmp = subject.value
                        //console.log(tmp)
                        props.newsubject(tmp)
                    }
                }
            }
        }



fun RBuilder.addlesson(
     newsubject: (String) -> Any
) = child(addLessons){
    attrs.newsubject= newsubject
}




