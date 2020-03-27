package component


import react.*
import react.dom.*
import org.w3c.dom.events.Event
import data.*

interface LessonFullProps : RProps {
    var subject: Subject
    var students: Array<Student>
    var presents: Array<Boolean>
    var onClick: (Int) -> (Event) -> Unit
}

val fLessonFull =
    functionalComponent<LessonFullProps> {
        h3 { +it.subject.name }
        ul {
            it.students.mapIndexed { index, student ->
                li {
                    student(student, it.presents[index], it.onClick(index))
                }
            }
        }
    }

fun RBuilder.lessonFull(
    subject: Subject,
    students: Array<Student>,
    presents: Array<Boolean>,
    onClick: (Int) -> (Event) -> Unit
) = child(fLessonFull){
    attrs.subject = subject
    attrs.students = students
    attrs.presents = presents
    attrs.onClick = onClick
}