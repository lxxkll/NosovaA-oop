Создаем новый компонент addLessons в котором создаем две кнопки:
 <ol>
 <li> Создаем кнопку input типа text в которой будем прописывать название. 
 <p>"Вытаскиваем" значение которое мы ввели и помещаем его в глобальную перемееную </p> </li> 
<li> Создаем кнопку "Отправить" которая отвечает за добавление слова в созданную в св-ах переменную</li> 
</ol>

```kotlin
package component


import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.*
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import kotlin.browser.document



interface AppLessonProps : RProps {

    var newsubject: (String) -> Any
}

var add_newsubject: String = ""

val addLessons =
    functionalComponent<AppLessonProps> { props ->


        div {
            h3 {
                +"Введите название предмета"

            }
            input(type = InputType.text, name = "inp_1")
            {
                attrs {
                    onChangeFunction = {
                        val a = it.target as HTMLInputElement
                        add_newsubject = a.value
                            //console.log(add_newsubject)
                    }
                }
            }

            input(type = InputType.submit) {
                attrs.onClickFunction =
                    {
                        props.newsubject (add_newsubject)
                    }
            }
        }
    }

fun RBuilder.applesson(
     newsubject: (String) -> Any
) = child(addLessons){
    attrs.newsubject= newsubject
}
```

 <p> Часть кода которая записывает в глобальную переменную add_newsubject слово которое мы ввели в поле </p>

``` kotlin
onChangeFunction = {
 val a = it.target as HTMLInputElement
add_newsubject = a.value
```

 <p> Часть кода которая отвчеает за кнопку "Отправить", что бы мы могли добавить предмет в список  </p>

```kotlin
 attrs.onClickFunction =
{
    props.newsubject (add_newsubject)
}

```

<p> Далее мы перенесли subject из св-в в состояние и добавили функцию добавления предмета в список.  </p>


``` kotlin
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
```


 <p> На рисунке 1 показана страница при первом запуске

<img src = 1.jpg>

На рисунке 2 показана странница с введеным предметом

<img src = 2.jpg>

На рисунке 3 показана страница с добавленным предметом

<img src = 3_1.jpg>

На рисунке 4 показана страница с отмечеными студентами
<img src = 3_2.jpg>
 </p>

