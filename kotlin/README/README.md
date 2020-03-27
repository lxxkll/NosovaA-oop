Создаем новый компонент addLessons в котором создаем две кнопки:
 <ol>
 <li> Создаем кнопку input типа text в которой будем прописывать название. 
 <p>Название хранится в состоянии,т.к input обычно само управляет своим состоянием, а для работы с React что бы обновить состояние мы вызываем setState, что бы записать слово которое мы ввели  </p> </li> 
<li> Создаем кнопку "Отправит" которая отвечает за добавление слова в созданную в св-ах переменную</li> 
</ol>

```kotlin
interface AppLessonProps : RProps {
    var add_newsubject: (String) -> Any
}

interface AppLessonState : RState {
    var newsubject: String
}


class AppLessons : RComponent<AppLessonProps, AppLessonState>() {
    override fun RBuilder.render() {
        div {
            h3 {
                +"Введите название предмета"
            }

            input(type = InputType.text, name = "key") // кнопка отвечающая за ввод слова
            {
                attrs {
                    value = state.newsubject
                    onChangeFunction = ADD()
                }
            }
            input(type = InputType.submit){ // кнопка отвечающая за запись слова
                attrs.onClickFunction = {
                    props.add_newsubject(state.newsubject)
                }
            }

        }
    }

    private fun ADD(): (Event) -> Unit { // функция которая считывает слово и обновляет состояние
        return {
            val slovo = it.target // если нажали в поле для ввода  то считываем слово
                    as HTMLInputElement
            setState {
                newsubject = slovo.value
            }

        }
    }
}

fun RBuilder.applesson( add_newsubject:  (String) -> Any
) = child(AppLessons::class){
    attrs.add_newsubject=add_newsubject
}
```

Далее мы перенесли subject из св-в в состояние и добавили функцию добавления предмета в список.

``` kotlin
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

// функции которые отвечают за передачу предмет из addLesson в app

    fun new_subject (): (String) -> Any = { new_subject -> 
        add_new_subject(new_subject)
    }

    fun add_new_subject (new_subject: String) // добавляем в массив новый предмет 
    {
        setState {
            subject+= Subject(new_subject)
            presents+= arrayOf(Array(props.students.size) { false })
        }

    }



    override fun RBuilder.render() {
        h1 { +"App" }
        applesson(new_subject()) // добавляем компонент

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


На рисунке 1 показана страница при первом запуске

<img src = 1.jpg>

На рисунке 2 показана странница с введеным предметом

<img src = 2.jpg>

На рисунке 3 показана страница с добавленным предметом

<img src = 3_1.jpg>

На рисунке 4 показана страница с отмечеными студентами
<img src = 3_2.jpg>

