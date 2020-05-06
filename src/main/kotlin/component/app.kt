package component

import data.*
import hoc.withDisplayName
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*
import redux.*
import kotlin.browser.document


interface AppProps : RProps {
    var store: Store<State, RAction, WrapperAction>
}


interface RouteNumberResult : RProps {
    var number: String
}

fun fApp() =
    functionalComponent<AppProps> { props ->
        header {
            h1 { +"App" }
            nav {
                ul {
                    li { navLink("/lessons") { +"Lessons" } }
                    li { navLink("/students") { +"Students" } }
                    li { navLink("/changeLesson") { +"Изменение предмета" } }
                    li { navLink("/changeStudent") { +"Изменение студента" } }
                }
            }
        }


        switch {
            route("/lessons",
                exact = true,
                render = {
                    anyList(props.store.getState().lessons, "Lessons", "/lessons")
                }
            )
            route("/students",
                exact = true,
                render = {
                    anyList(props.store.getState().students, "Students", "/students")
                }
            )
            route(
                "/changeLesson",
               // exact = true,
                render = change_Lesson(props)
            )
            route(
                "/changeStudent",
               // exact = true,
                render = change_Student(props)
            )
            route("/lessons/:number",
                render = renderLesson(props)
            )
            route("/students/:number",
                render = renderStudent(props)
            )
        }
    }


fun AppProps.add_Student(): (Event) -> Unit ={
    val nameObj = document.getElementById("student") as HTMLInputElement
    val new_name = nameObj.value.split(" ")
    store.dispatch(add_Student(new_name[0],new_name[1]))
}

fun AppProps.del_Student():(Event) -> Unit = {
    val del = document.getElementById("student") as HTMLInputElement
    store.dispatch(del_Student(del.value.toInt()-1))
}

fun AppProps.add_Lesson(): (Event) -> Unit = {
    val add = document.getElementById("subject") as HTMLInputElement
    store.dispatch(add_Lesson(add.value))
}

fun AppProps.del_Subject(): (Event) -> Unit = {
    val del_s = document.getElementById("subject") as HTMLInputElement
    store.dispatch(del_Lesson(del_s.value.toInt() - 1))
}


fun RBuilder.change_Lesson (props: AppProps): () -> ReactElement ={
    Any_add_or_dell_Full(
        RBuilder::addLessons,
        RBuilder::anyList,
        props.store.getState().lessons,
        "Изменение урока",
        "/lessons",
        props.add_Lesson(),
        props.del_Subject()

    )
}

fun RBuilder.change_Student(props: AppProps): () -> ReactElement = {
        Any_add_or_dell_Full(
            RBuilder::addstudent,
            RBuilder::anyList,
            props.store.getState().students,
            "Изменение студента",
            "/students",
            props.add_Student(),
            props.del_Student()
    )
}





fun AppProps.onClickStudent(num: Int): (Int) -> (Event) -> Unit =
    { index ->
        {
            store.dispatch(ChangePresent(index, num))
        }
    }

fun AppProps.onClickLesson(num: Int): (Int) -> (Event) -> Unit =
    { index ->
        {
            store.dispatch(ChangePresent(num, index))
        }
    }

fun RBuilder.renderLesson(props: AppProps) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.match.params.number.toIntOrNull() ?: -1
        val lesson = props.store.getState().lessons.getOrNull(num)
        if (lesson != null)
            anyFull(
                RBuilder::student,
                lesson,
                props.store.getState().students,
                props.store.getState().presents[num],
                props.onClickLesson(num)
            )
        else
            p { +"No such lesson" }
    }

fun RBuilder.renderStudent(props: AppProps) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.match.params.number.toIntOrNull() ?: -1
        val student = props.store.getState().students.getOrNull(num)
        if (student != null)
            anyFull(
                RBuilder::lesson,
                student,
                props.store.getState().lessons,
                props.store.getState().presents.map {
                    it[num]
                }.toTypedArray(),
                props.onClickStudent(num)
            )
        else
            p { +"No such student" }
    }


fun RBuilder.app(

    store: Store<State, RAction, WrapperAction>
) =
    child(
        withDisplayName("App", fApp())
    ) {
        attrs.store = store
    }





