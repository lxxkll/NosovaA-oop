package component



import data.Subject
import react.*
import react.dom.*
import react.router.dom.*

interface SubjectListProps : RProps{
    var subject: Array<Subject>
}

val fSubjectList =
    functionalComponent< SubjectListProps> {
        h2 { +"Lessons" }
        ul {
            it.subject.mapIndexed { index, lesson ->
                li {
                    navLink("/lessons/$index") { +lesson.name }
                }
            }
        }
    }

fun RBuilder.subjectList(subject: Array<Subject>) =
    child(fSubjectList) { attrs.subject = subject }