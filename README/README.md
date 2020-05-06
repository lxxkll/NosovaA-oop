Переделали прошлую лаб.работу с использованием redux.

Компоненты отвечающие за создания input для добавления и удаления студентов и общий компонент не изменились.

Для того что бы начать работу мы перенесли все что было в сосnоянии из APP в отдельный компонент State

```kotlin
class State (
    val presents: Array<Array<Boolean>>,
    var lessons: Array<Lesson>,
    var students: Array<Student>
)
```

Далее в actions прописываем:

```kotlin
package redux
class ChangePresent(val lesson: Int, val student: Int) : RAction
class add_Student(val firstname:String, val surname: String): RAction
class del_Student(val del_student: Int) : RAction
class add_Lesson(val lesson: String): RAction
class del_Lesson(val del_lesson: Int): RAction
```

Далее в reducers прописываем то что будет происходить при добавлении или удалении студентов и предметов.

```kotlin
fun changeReducer(state: State, action: RAction) =
    when (action) {
        is ChangePresent -> State(
            state.presents.mapIndexed { indexLesson, lesson ->
                if (indexLesson == action.lesson)
                    lesson.mapIndexed { indexStudent, student ->
                        if (indexStudent == action.student)
                            !student
                        else student
                    }.toTypedArray()
                else
                    lesson
            }.toTypedArray(),
            state.lessons,
            state.students
        )

        is add_Student ->State(
            state.presents.mapIndexed{index,_ ->
                state.presents[index].plus(arrayOf(false))
            }
                .toTypedArray(),
            state.lessons,
            state.students.plus(Student(action.firstname, action.surname))
        )

        is del_Student -> State(
            state.presents.mapIndexed { index, _ ->
                state.presents[index].toMutableList().apply {
                    removeAt(action.del_student)
                }.toTypedArray()
            }.toTypedArray(),
            state.lessons,
            state.students.toMutableList().apply {
                removeAt(action.del_student)
            }.toTypedArray()
        )

        is add_Lesson -> State(
            state.presents.plus(arrayOf(Array(state.students.size) { false })),
            state.lessons.plus(Lesson(action.lesson)),
            state.students
        )

        is del_Lesson -> State(
            state.presents.toMutableList().apply {
                removeAt(action.del_lesson)
            }.toTypedArray(),
            state.lessons.toMutableList().apply {
                removeAt(action.del_lesson)
            }.toTypedArray(),
            state.students
        )
        else -> state
    }
```


В компоненте APP прописываем пути для изменения студентов и предметов, функции добавления или удаления предметов и студентов.


```kotlin

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

```


Часть кода которая отвечае за удаление или добавление студента и предмета.

Для того что бы удалить или добавить предмет мы находим элемент котороый ввели и затем при помощи вызова функции dispatch вносим изменения в хранилище

```kotlin

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
```


 <p> На рисунке 1 показана страница при первом запуске


<img src = 1.jpg>

На рисунке 2 показана странница "Добавить предмет"

<img src = 2.jpg>

На рисунке 3 показана страница с добавленным предметом

<img src = 3_1.jpg>

На рисунке 4 показана страница со студентом который присутствует на новом предмете

<img src = 3_2.jpg>
 </p>

