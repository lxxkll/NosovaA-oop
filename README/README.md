Основной код программы который выводитпредмет и список студентов.


```Kotlin

fun main() {
    render(document.getElementById("root")!!) {
        h1 {
            +"Список предметов и студентов"
        }
                    rsubject(subjectList)
}
    }

```

В данном коде используется функция rsubject.

```Kotlin

import data.Student
import data.Subject
import data.studentList
import data.subjectList
import org.w3c.dom.events.Event
import react.*
import react.dom.h1
import react.dom.li
import react.dom.ol

interface RSubjectProps : RProps {
    var subject: Array<Subject>
    var listStudent :Array<Student>
    
}



interface RSubjectState : RState {
   var present: Array<Boolean>
}

class RSubject : RComponent<RSubjectProps, RSubjectState>() {
    override fun componentWillMount() {
        state.apply {
            present = Array(props.listStudent.size) { false }
        }
    }
    fun RBuilder.onIndex(): (Int) -> (Event) -> Unit = {
        onClick(it)
    }
    override fun RBuilder.render() { // Формируем предмет и список студентов к нему
               props.subject.map {
                   + it.name
                   ol {
                       rstudentlist(props.listStudent, state.present, onIndex())
                   }
               }
    }

            fun RBuilder.onClick(index: Int): (Event) -> Unit = {
                setState {
                    present[index] = !present[index]
                }
            }
        }



fun RBuilder.rsubject(subject:  ArrayList<Subject> ) =
    child(RSubject::class)
    {
        attrs.subject = subject.toTypedArray()
        attrs.listStudent = studentList.toTypedArray()
    }



```


В части 


``` kotlin
interface RSubjectProps : RProps {
    var subject: Array<Subject>
    var listStudent :Array<Student>
    
}



interface RSubjectState : RState {
   var present: Array<Boolean>
}```
подняли состояние на уровень выше в предмет



В данной части кода мы переделали StudentList в функциональный компонент 


```Kotlin
interface RStudentListProps : RProps {
    var students: Array<Student>

}

fun RBuilder.rstudentlist(students: Array<Student>, present: Array<Boolean> , onClick:  (Int) -> (Event) -> Unit) =
    child(functionalComponent<RStudentListProps> {props ->
        props.students.mapIndexed {index, student ->
            li {
                rstudent(student,present[index],onClick(index))
            }
        }
    }){
        attrs.students = students
    }


```

На рисунке 1 показан список до нажатия

<img src = 1.jpg>

На рисункк 2 показано что все студенты отсутствуют 

<img src = 2.jpg>

На рисунке 3_1 и 3_2 показано что стало с сотоянием после нажатия на студента

<img src = 3_1.jpg>
<img src = 3_2.jpg>

