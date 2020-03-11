Основной код программы который выводит список студентов.
```Kotlin
fun main() {
    render(document.getElementById("root")!!) {
        h1 {
            +"Список студентов"
        }
        studentList(studentList)
    }
}

```

В данном коде используется функция studentList.

```Kotlin
import data.Student
import react.*
import react.dom.li
import react.dom.ol

interface RStudentProps : RProps {
    var student: Student
}

interface RStudentProps_spicok : RProps {
    var students: Array<Student>
}

class RStudentList :  RComponent<RStudentProps_spicok, RState>() {

    override fun RBuilder.render() {
        ol {
            props.students.map {
                li {
                    rstudent(it)
                }
            }
        }

    }
}

fun RBuilder.rstudent(student: Student) =
    child(
        functionalComponent<RStudentProps> {
            +"${it.student.firstname} ${it.student.surname}"
        }
    ){
        attrs.student = student
    }



fun RBuilder.studentList(students: ArrayList<Student>) =
child(RStudentList::class) {
    attrs.students = students.toTypedArray()
}
```

Данная часть кода отвечает за вывод списка студентов.


На рисунке 1 представлен список студентов созданый одним компонентом в котором находятся три функциональных компонента

<img src = 2.jpg>

