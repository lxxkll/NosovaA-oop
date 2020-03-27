package component

import data.Student
import data.Subject
import data.subjectList
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.*
import kotlin.browser.document

package component

import data.Student
import data.Subject
import data.subjectList
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import kotlin.browser.document


interface AppLessonProps : RProps {
    var add_newsubject: (String) -> Any
}

interface AppLessonState : RState {
    var newsubject: String
}


class AppLessons : RComponent<AppLessonProps, AppLessonState>() {
    override fun RBuilder.render() {
        div {
            h3 {
                +"Введите название предмета"
            }

            input(type = InputType.text, name = "key")
            {
                attrs {
                    value = state.newsubject
                    onChangeFunction = ADD()
                }
            }
            input(type = InputType.submit){
                attrs.onClickFunction = {
                    props.add_newsubject(state.newsubject)
                }
            }

        }
    }

    private fun ADD(): (Event) -> Unit {
        return {
            val slovo = it.target // если нажали в поле для ввода  то считываем слово
                    as HTMLInputElement
            setState {
                newsubject = slovo.value
            }

        }
    }
}

fun RBuilder.applesson( add_newsubject:  (String) -> Any
) = child(AppLessons::class){
    attrs.add_newsubject=add_newsubject
}
