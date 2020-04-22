package component


import data.*
import hoc.withDisplayName
import org.w3c.dom.HTMLInputElement
import kotlin.reflect.KClass
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*
import kotlin.browser.document


interface AppState : RState {
    var subject: Array<Subject>
    var presents: Array<Array<Boolean>>
    var students: Array<Student>
}

interface RouteNumberResult : RProps {
    var number: String
}

class App : RComponent<RProps, AppState>() {
    override fun componentWillMount() {
        state.students = studentList
        state.subject = subjectList
        state.presents = Array(state.subject.size) {
            Array(state.students.size) { false }
        }
    }

    fun addSubject () = {  _: Event ->
        val new_subject = document.getElementById("subject") as HTMLInputElement
        var tmp = new_subject.value
        setState {
            subject+= Subject(tmp)
            presents+= arrayOf(Array(state.students.size) { false })
        }
    }

    fun delSubject () = { _: Event ->

        val del = document.getElementById("subject") as HTMLInputElement
        val change = state.subject.toMutableList().apply {
            removeAt(del.value.toInt()-1) }
        val changePresents = state.presents.toMutableList().apply {
            removeAt(del.value.toInt() - 1)
            }
        setState{
            subject = change.toTypedArray()
            presents= changePresents.toTypedArray()
        }
    }


    fun addStudent () = {  _: Event ->
        val name = document.getElementById("student") as HTMLInputElement
        val tmp = name.value.split(" ")
        val fname = tmp[0]
        val sname = tmp[1]
        setState {
            students += Student(fname, sname)
            presents += arrayOf(Array(state.students.size){false})
        }
    }

    fun delStudent () = { _: Event ->
        val del = document.getElementById("student") as HTMLInputElement
        val change = state.students.toMutableList().apply {
           removeAt(del.value.toInt()-1)
              }
        val changePresentsf = state.presents.toMutableList().apply {
           removeAt(del.value.toInt()-1)
            }
        setState{
            students = change.toTypedArray()
            presents= changePresentsf.toTypedArray()
        }
    }


    override fun RBuilder.render() {
        header {
            h1 { +"App" }
            nav {
                ul {
                    li { navLink("/lessons") { +"Список предметов" } }
                    li { navLink("/students") { +"Список студентов" } }
                    li { navLink("/changeLesson") { +"Изменение предмета" } }
                    li { navLink("/changeStudent") { +"Изменение студента" } }
                }
            }
        }

        switch {
            route("/lessons",
                exact = true,
                render = {
                     anyList(state.subject,"Список предметов","/lessons")
                }
            )
            route("/students",
                exact = true,
                render = {
                  anyList(state.students,"Список студентов","/students")
                } )
            route("/changeLesson",
                exact = true,
                render = {
                    Any_add_or_dell_Full(
                        RBuilder::addLessons,
                        RBuilder::anyList,
                        state.subject,
                        "Изменение урока",
                        "/lessons",
                        addSubject(),
                        delSubject()
                        )
                    }
            )
            route("/changeStudent",
                exact = true,
                render = {
                    Any_add_or_dell_Full(
                    RBuilder::addstudent,
                    RBuilder::anyList,
                    state.students,
                    "Изменение студента",
                    "/students",
                    addStudent(),
                    delStudent()
                    )
                } )
             route("/lessons/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val lesson = state.subject.getOrNull(num)
                    if (lesson != null)
                        anyFull(
                            RBuilder::student,
                            lesson,
                            state.students,
                            state.presents[num]
                        ) { onClick(num, it) }
                    else
                        p { +"Нет такого предмета" }
                }
            )
            route("/students/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val student = state.students.getOrNull(num)
                    if (student != null)
                        anyFull(
                            RBuilder::lesson,
                            student,
                            state.subject,
                            state.presents.map {
                                it[num]
                            }.toTypedArray()
                        ) { onClick(it, num) }
                                   else
                        p { +"Нет такого студента" }
                }
            )
        }
    }

    fun onClick(indexLesson: Int, indexStudent: Int) =
        { _: Event ->
            setState {
                presents[indexLesson][indexStudent] =
                    !presents[indexLesson][indexStudent]
            }
        }
}

fun RBuilder.app() = child(withDisplayName("AppHoc", App::class)) {}

fun r(){
    val b = mutableListOf(1.2, 4.5, 3.2, 1.8, 3.2)
    b.removeAt(0)
    b.remove(3.2)
}