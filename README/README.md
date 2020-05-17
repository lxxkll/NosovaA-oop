Добавили ранее созданый компонент AddLesson на отдельную страницу приложения , прописав маршрут к нему



```kotlin
package component


import data.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*


interface AppProps : RProps {

    var students: Array<Student>
}

interface AppState : RState {
    var subject: Array<Subject>
    var presents: Array<Array<Boolean>>
}

interface RouteNumberResult : RProps {
    var number: String
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
        header {
            h1 { +"App" }
            nav {
                ul {
                    li { navLink("/lessons") { +"Список предметов" } }
                    li { navLink("/students") { +"Список студентов" } }
                    li { navLink("/addLesson") { +"Добавить предмет" } }
                }
            }
        }

        switch {
            route("/lessons",
                exact = true,
                render = {
                    subjectList(state.subject)
                }
            )
            route("/students",
                exact = true,
                render = {
                    studentList(props.students)
                } )
            route("/addLesson",
                exact = true,
                render = {
                    addlesson(new_subject())
                }
            )
            route("/lessons/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val subject = state.subject.getOrNull(num)
                    if (subject != null)
                        lessonFull(
                            subject,
                            props.students,
                            state.presents[num]
                        ) { onClick(num, it) }
                    else
                        p { +"Нет такого предмета" }
                }
            )
            route("/students/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val student = props.students.getOrNull(num)
                    if (student != null)
                        studentFull(
                            state.subject,
                            student,
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

fun RBuilder.app(
    students: Array<Student>
) = child(App::class) {
    attrs.students = students
}
```


Часть кода которая отвечает за переход между страницами 

```kotlin
li { navLink("/addLesson") { +"Добавить предмет" } }
```

Часть кода отвечающая за отрисовку компонента "Добавить предмет"

```kotlin
 route("/addLesson",
  exact = true,
 render = {
  addlesson(new_subject())
   }
 )
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

