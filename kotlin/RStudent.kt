import data.Student
import react.*
import react.dom.li
import react.dom.ol

interface RStudentProps : RProps {
    var student: Student

}


interface RStudentProps_spicok : RProps {
    var students: Array<Student>
}


fun RBuilder.rstudent(student: ArrayList<Student>) =
    child(
        functionalComponent<RStudentProps_spicok> {
            ol {
                student.map {
                    li {
                        +"${it.firstname} ${it.surname}"
                    }
                }
            }
        }
    ){
        attrs.students = student.toTypedArray()
    }
