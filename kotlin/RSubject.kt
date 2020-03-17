
import data.Student
import data.Subject
import data.studentList
import data.subjectList
import org.w3c.dom.events.Event
import react.*
import react.dom.h1
import react.dom.li
import react.dom.ol

interface RSubjectProps : RProps {
    var subject: Array<Subject>
    var listStudent :Array<Student>

}



interface RSubjectState : RState {
   var present: Array<Boolean>
}

class RSubject : RComponent<RSubjectProps, RSubjectState>() {
    override fun componentWillMount() {
        state.apply {
            present = Array(props.listStudent.size) { false }
        }
    }
    fun RBuilder.onIndex(): (Int) -> (Event) -> Unit = {
        onClick(it)
    }
    override fun RBuilder.render() {
               props.subject.map {
                   + it.name
                   ol {
                       rstudentlist(props.listStudent, state.present, onIndex())
                   }
               }
    }

            fun RBuilder.onClick(index: Int): (Event) -> Unit = {
                setState {
                    present[index] = !present[index]
                }
            }
        }



fun RBuilder.rsubject(subject:  ArrayList<Subject> ) =
    child(RSubject::class)
    {
        attrs.subject = subject.toTypedArray()
        attrs.listStudent = studentList.toTypedArray()
    }


