Создали компонент для изменения студентов, где находится input типа text для ввода Имени и Фамилии студента.

```kotlin
package component


import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.id
import react.*
import react.dom.*





val addstudent =
    functionalComponent<RProps > {
        div {

            p{
                h3 {
                    +"Введите имя и фамилию студента"
                }
            input(type = InputType.text)
            {
                attrs.id = "student"
                attrs.placeholder = "При удалении ввод цифры"
            }
            }

        }

    }


fun RBuilder.addstudent(
) = child(
    withDisplayName("studentsAdd", addstudent)
) {}

```

Создали компонент для изменения предмета, где находится input типа text для ввода названия предмета.

```kotlin
package component


import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*



val addLessons =
    functionalComponent<RProps> {
        div {
            h3 {
                +"Введите название предмета"
            }
            input(type = InputType.text)
            {
                attrs.id = "subject"
               attrs.placeholder = "При удалении ввод цифры"

            }
        }
    }

fun RBuilder.addLessons(
) = child(
    withDisplayName("lessonsAdd", addLessons)
) {}

```

Создали общий компонент в котором находятся input для удаления и добавления чего либо

```kotlin
package component

import hoc.withDisplayName
import kotlinx.html.InputType

import kotlinx.html.js.onClickFunction

import org.w3c.dom.events.Event
import react.*
import react.dom.*


interface Any_add_or_del_Props<O> : RProps {
    var subobjs: Array<O>
    var name : String
    var path : String
    var add: (Event) -> Unit
    var del: (Event) -> Unit
}

fun <O> Any_add_or_dell_Full(
    rChange: RBuilder.() -> ReactElement,
    rComponent: RBuilder.(Array<O>, String, String) -> ReactElement
) =
    functionalComponent<Any_add_or_del_Props<O>>
    {props ->
        div {
            h3 {
                +"Изменение"
            }
            rChange()
            input(type = InputType.submit) {
                attrs.onClickFunction = props.add
            }
            input(type = InputType.reset) {
                attrs.onClickFunction = props.del
            }
            rComponent(props.subobjs,props.name,props.path)
        }
    }

fun <O> RBuilder.Any_add_or_dell_Full(
    rChange: RBuilder.() -> ReactElement,
    rComponent: RBuilder.(Array<O>, String, String) -> ReactElement,
    subobjs: Array<O>,
    name : String,
    path : String,
    add: (Event) -> Unit,
    del: (Event) -> Unit
) = child(
    withDisplayName("Any_add_or_dell_Full",  Any_add_or_dell_Full<O>(rChange, rComponent))
){
    attrs.subobjs = subobjs
    attrs.name = name
    attrs.path = path
    attrs.add = add
    attrs.del = del
}
```



Изменили компонент app что бы он работал с новыми компонентами.Для корректной работы перенесли студентов из свойств в состояние. Добавили функции удаления и добавления студента или предмета.

```kotlin
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
```


Часть кода которая отвечает за удаление предмета и добавление нового предмета


```kotlin
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
```

Часть кода которая отвечает за удаление студента и добавление нового студента.

```kotlin
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
```


Часть кода в функции addStudent отвечает за разделение одной строки на две части, для того что бы корректно записать в массив по отдельности имя и фамилию студента.

```kotlin
  val tmp = name.value.split(" ")
        val fname = tmp[0]
        val sname = tmp[1]
```

Работа функций добавления:  мы записывем в переменную new_subject или name найденый элемент (т.е то что мы ввели в поле input типа text) и затем извлекаем value и помещаем в переменную tmp (извлекается наш введеный текст). Затем мы перезаписывем исходный массив.
В функции добавления студетов используется метод split() который разбивает наш элемент, на массив строк (так получается имя и фамилия отдельно)

Работа функции удаления: в пременную del записываем элемент, затем мы делаем список студетов или предметов изменяемым и применяем функцию removeAt для того что бы удалить элемент ( del.value.toInt() отвечает за то что бы сделать элемент числом) и записываем  новый массив без удаленного элемента, такие же действия для массива состояния(присутствия или отсутсвия). Затем в set state перезаписываем  исходный массив, нашим новым массивом.



Часть кода которая отвечает за создание страницы и ее отрисовку

```kotlin
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
```

 <p> На рисунке 1 показана страница "Изменить студента". На которой мы добавили нового студента.


<img src = 1.jpg>

На рисунке 2 показана страница "Изменить студента". На которой мы удалили нового студента.

<img src = 2.jpg>

На рисунке 3 показана страница "Изменить предмет". На которой мы добавили новый предмет.

<img src = 3_1.jpg>

На рисунке 4 показана страница "Изменить предмет". На которой мы удалили новый предмет.

<img src = 4.jpg>

На рисунке 5 показана старница нового студета с предметами, в том числе и новый предмет

<img src = 3_2.jpg>
 </p>

