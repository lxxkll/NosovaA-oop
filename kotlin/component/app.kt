package component


import data.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.button
import react.dom.h1

interface AppProps : RProps {
    var students: Array<Student>

}

interface AppState : RState {
    var subject: Array<Subject>
    var presents: Array<Array<Boolean>>
    var newsubject: String

}

class App : RComponent<AppProps, AppState>() {
    override fun componentWillMount() {
        state.subject = subjectList
        state.presents = Array(state.subject.size) {
            Array(props.students.size) { false }
        }

    }
    fun new_subject (): (String) -> Any = { new_subject ->
        setState {
            subject+= Subject(new_subject)
            presents+= arrayOf(Array(props.students.size) { false })
        }
    }




    override fun RBuilder.render() {
        h1 { +"App" }
        applesson(new_subject())
        lessonListFull(
            state.subject,
            props.students,
            state.presents,
            onClickLessonFull
        )
        studentListFull(
            state.subject,
            props.students,
            transform(state.presents),
            onClickStudentFull
        )
    }

    fun transform(source: Array<Array<Boolean>>) =
        Array(source[0].size){row->
            Array(source.size){col ->
                source[col][row]
            }
        }

    fun onClick(indexLesson: Int, indexStudent: Int) =
        { _: Event ->
            setState {
                presents[indexLesson][indexStudent] =
                    !presents[indexLesson][indexStudent]
            }
        }

    val onClickLessonFull =
        { indexLesson: Int ->
            { indexStudent: Int ->
                onClick(indexLesson, indexStudent)
            }
        }

    val onClickStudentFull =
        { indexStudent: Int ->
            { indexLesson: Int ->
                onClick(indexLesson, indexStudent)
            }
        }

}

fun RBuilder.app(
    students: Array<Student>
) = child(App::class) {

    attrs.students = students
}