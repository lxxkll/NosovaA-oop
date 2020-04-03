package component


import react.*
import react.dom.*
import org.w3c.dom.events.Event
import data.*


interface StudentFullProps : RProps {
    var subject: Array<Subject>
    var student: Student
    var presents: Array<Boolean>
    var onClick: (Int) -> (Event) -> Unit
}

val fStudentFull =
    functionalComponent<StudentFullProps> {
        h3 { +"${it.student.firstname} ${it.student.surname}" }
        ul {
            it.subject.mapIndexed { index, subject ->
                li {
                    lesson(subject, it.presents[index], it.onClick(index))
                }
            }
        }
    }

fun RBuilder.studentFull(
    subject: Array<Subject>,
    student: Student,
    presents: Array<Boolean>,
    onClick: (Int) -> (Event) -> Unit
) = child(fStudentFull){
    attrs.subject = subject
    attrs.student = student
    attrs.presents = presents
    attrs.onClick = onClick
}