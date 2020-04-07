package component


import react.*
import react.dom.*
import org.w3c.dom.events.Event
import data.*

interface LessonListFullProps : RProps {
    var subject: Array<Subject>
    var students: Array<Student>
    var presents: Array<Array<Boolean>>
    var onClick: (Int) -> (Int) -> (Event) -> Unit
}

val fLessonListFull =
    functionalComponent<LessonListFullProps> {
        h2 { +"Subject" }
        it.subject.mapIndexed{index, subject ->
            lessonFull(
                subject,
                it.students,
                it.presents[index],
                it.onClick(index))
        }
    }


fun RBuilder.lessonListFull(
    subject: Array<Subject>,
    students: Array<Student>,
    presents: Array<Array<Boolean>>,
    onClick: (Int) -> (Int) -> (Event) -> Unit
) = child(fLessonListFull) {
    attrs.subject = subject
    attrs.students = students
    attrs.presents = presents
    attrs.onClick = onClick
}