package component


import react.*
import react.dom.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import data.*

interface LessonProps : RProps {
    var subject: Subject
    var present: Boolean
    var onClick: (Event)->Unit
}

val fLesson =
    functionalComponent<LessonProps> {
        span (
            if(it.present) "present" else "absent"
        ){
            +it.subject.name
            attrs.onClickFunction = it.onClick
        }
    }

fun RBuilder.lesson(
    subject: Subject,
    present: Boolean,
    onClick: (Event)->Unit
) = child(fLesson) {
    attrs.subject = subject
    attrs.present = present
    attrs.onClick = onClick
}