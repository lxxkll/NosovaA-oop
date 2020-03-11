Основной код программы который выводит список студентов.
```Kotlin
fun main() {
    render(document.getElementById("root")!!) {
        h1 {
            +"Список студентов"
        }
        rstudent(studentList)
    }
}

```

В данном коде используется функция rstudent.

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


fun RBuilder.rstudent(student: ArrayList<Student>) =
    child(
        functionalComponent<RStudentProps_spicok> {
            ol {
                student.map {
                    li {
                        +"${it.firstname} ${it.surname}"
                    }
                }
            }
        }
    ){
        attrs.students = student.toTypedArray()
    }

```

Данная часть кода отвечает за вывод списка студентов.


На рисунке 1 представлен список студентов созданый одним функциональным компонентом

<img src = 1.jpg>

