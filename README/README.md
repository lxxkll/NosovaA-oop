```Kotlin
fun main() {
    document.getElementById("root")!!
        .append {

            h2 {
                attributes += "id" to "header"
                +"Сортировка"
                onClickFunction = onCLickFunction()
            }
            h1 {
                +"Список студентов"
            }

            ol {
                //attributes += "style" to "color:gray"
                attributes += "id" to "listStudents"
                studentList.map {
                    li {
                        attributes += "id" to it.firstname
                        +"${it.firstname} ${it.surname}"
                        onClickFunction = onClick2(it)
                    }
                }
            }
        }
}
```


Данная функция отвечает за создание заголовков и передает список студентов. 
 
 ``` Kotlin
 private fun LI.onClick2(Student: Student): (Event) -> Unit {
    return {
        val student = document.getElementById(Student.firstname)!!
        if (Student.pris){
            student.setAttribute("style","color:pink")
            Student.pris = false
        }
        else
        {
            student.setAttribute("style","color:gray")
            Student.pris = true
        }
    }
}
```
К каждому студенту применяется функция onClickFunction которая окрашивает студента в определеный цвет: если студент присутствует то розовый, а если отсутсвует то серый.

``` Kotlin
private fun H2.onCLickFunction(): (Event) -> Unit {
    return {

        val listStudents = document.getElementById("listStudents")!!
        listStudents.clear()
        listStudents.append {
            if (ascending)

                studentList.sortBy { it.firstname }
            else
                studentList.sortByDescending { it.firstname }
            ascending = !ascending
            studentList.map {
                li {
                    attributes += "id" to it.firstname
                    +"${it.firstname} ${it.surname}"
                    onClickFunction = onClick2(it)
                }
            }
        }
    }
}
```
Функция отвечает за сортировку студентов при нажатии на слово сортировка.

Так же в функции main после закрытия скобок в ol мы добавили следующий фрагмент кода

``` Kotlin
p {

    +"Blue"
    input (option = arrayListOf("blue"))
    br
    +"Red"
    input (option = arrayListOf("red"))
    br
    +"Yellow"
    input (option = arrayListOf("yellow"))
}
```
Данный фрагмент кода добавляет на html страницу радиокнопки которые окрашивают весь текст.

Для того что бы написать короткий код без повторяющихся элементов мы измении существующую функцию. 

```Kotlin
private fun colorchange(value: String): (Event) -> Unit {
    return {
        val div = document.getElementById("root")!!
        div.setAttribute("style", "color:${value}")
    }
}
```

На рисунке 1 представлен текст до нажатия на любую радиокнопку

<img src = 1.jpg>

На рисунке 2 представлен текст после нажатия на синий цвет

<img src = 4.jpg>

На рисунке 3 представлен текст после нажатия на красный цвет

<img src = 3.jpg>

На рисунке 4 представлен текст после нажатия на желтый цвет

<img src = 2.jpg>