package component

import react.*
import react.dom.*
import org.w3c.dom.events.Event
import data.*

interface StudentListFullProps : RProps {
    var subject: Array<Subject>
    var students: Array<Student>
    var presents: Array<Array<Boolean>>
    var onClick: (Int) -> (Int) -> (Event) -> Unit
}

val fStudentListFull =
    functionalComponent<StudentListFullProps> {
        h2 { +"Students" }
        it.students.mapIndexed{index, student ->
            studentFull(
                it.subject,
                student,
                it.presents[index],
                it.onClick(index))
        }
    }

fun RBuilder.studentListFull(
    subject: Array<Subject>,
    students: Array<Student>,
    presents: Array<Array<Boolean>>,
    onClick: (Int) -> (Int) -> (Event) -> Unit
) = child(fStudentListFull) {
    attrs.subject = subject
    attrs.students = students
    attrs.presents = presents
    attrs.onClick = onClick
}