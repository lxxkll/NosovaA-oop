package component


import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.*
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import kotlin.browser.document



interface AppLessonProps : RProps {

    var newsubject: (String) -> Any
}

var add_newsubject: String = ""

val addLessons =
    functionalComponent<AppLessonProps> { props ->


        div {
            h3 {
                +"Введите название предмета"

            }
            input(type = InputType.text, name = "inp_1")
            {
                attrs {
                    onChangeFunction = {
                        val a = it.target as HTMLInputElement
                        add_newsubject = a.value
                            //console.log(add_newsubject)
                    }
                }
            }

            input(type = InputType.submit) {
                attrs.onClickFunction =
                    {
                        props.newsubject (add_newsubject)

                    }
            }
        }
    }








fun RBuilder.applesson(
     newsubject: (String) -> Any
) = child(addLessons){
    attrs.newsubject= newsubject
}




