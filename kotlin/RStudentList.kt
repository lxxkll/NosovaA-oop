import data.Student
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.li
import react.dom.ol

interface RStudentListProps : RProps {
    var students: Array<Student>

}

fun RBuilder.rstudentlist(students: Array<Student>, present: Array<Boolean> , onClick:  (Int) -> (Event) -> Unit) =
    child(functionalComponent<RStudentListProps> {props ->
        props.students.mapIndexed {index, student ->
            li {
                rstudent(student,present[index],onClick(index))
            }
        }
    }){
        attrs.students = students
    }

