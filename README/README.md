По заданию было необходимо создать  фильтры, которые выводят либо всех студентов (уроки), либо только присутствующих, либо только отсутствующих


Для этого был создан компонент filter

```kotlin
package data

enum class VisibilityFilter {
    SHOW_ALL,
    SHOW_PRESENT,
    SHOW_ABSENT
}
```

В компонент State добавили visibilityFilter который по умолочанию показывает всех студентов или все предметы

```kotlin 
class State(
    val lessons: LessonState,
    val students: StudentState,
    val presents: Presents,
    val visibilityFilter: VisibilityFilter = VisibilityFilter.SHOW_ALL
)
```


В  actions.kt было было прописано: 

```kotlin
class SetVisibilityFilter(val filter: VisibilityFilter) : RAction
```

В reducers.kt добавлено дейстивие

```kotlin

fun visibility(state: VisibilityFilter, action: RAction) =
        when (action) {
            is SetVisibilityFilter -> action.filter
             else -> state
}
```

Далее в anyFullContainer прописывем функцию видимости студентов. Аналогично действует видимость предметов.
Для показа всех студентов(предметов) возвращается первоначальная сформированная карта. Если мы хотим проверить отсутсвующих или присутсвющих студентов на каком либо предмете, то формируется новая карта по условиям отсутсвия или присутсвия. Для просмотра предметов на которых присутсвовал студент, формируется карта по условиям присутсвия или отсутсвия на предмете.

```kotlin
private fun visibleStudents(
        students: Map<Int, Student>,
        presents: Map<Int, Boolean>?,
        filter: VisibilityFilter
): Map<Int, Student> = when(filter) {
    VisibilityFilter.SHOW_ALL -> students
    VisibilityFilter.SHOW_ABSENT -> {
        val absentStudents = students.toMutableMap()
        absentStudents.clear()
        if (presents != null) {
            presents.filter { !it.value }.map{
                absentStudents[it.key] = students.getValue(it.key)
            }
        }
        absentStudents
    }
    VisibilityFilter.SHOW_PRESENT -> {
        val presentStudents = students.toMutableMap()
        presentStudents.clear()
        if (presents != null) {
            presents.filter { it.value }.map{
                presentStudents[it.key] = students.getValue(it.key)
            }
        }
        presentStudents
    }
}

private fun visibleLessons(
        lessons: Map<Int, Lesson>,
        presents: Map<Int, Boolean>?,
        filter: VisibilityFilter
): Map<Int, Lesson> = when (filter) {
    VisibilityFilter.SHOW_ALL -> lessons
    VisibilityFilter.SHOW_ABSENT -> {
        val absentLesson = lessons.toMutableMap()
        absentLesson.clear()
        if (presents != null) {
            presents.filter { !it.value }.map{
                absentLesson[it.key] = lessons.getValue(it.key)
            }
        }
        absentLesson
    }
    VisibilityFilter.SHOW_PRESENT -> {
        val presentStudents = lessons.toMutableMap()
        presentStudents.clear()
        if (presents != null) {
            presents.filter { it.value }.map{
                presentStudents[it.key] = lessons.getValue(it.key)
            }
        }
        presentStudents
    }
}
```

В filterContainer инициализируем state


```kotlin
interface FilterLinkProps : RProps {
    var filter: VisibilityFilter
}

private interface LinkStateProps : RProps {
    var active: Boolean
}

private interface LinkDispatchProps : RProps {
    var onClick: () -> Unit
}

val filterLink: RClass<FilterLinkProps> =
        rConnect<State, SetVisibilityFilter, WrapperAction, FilterLinkProps, LinkStateProps, LinkDispatchProps, LinkProps>(
                { state, ownProps ->
                    active = VisibilityFilter.SHOW_ALL == ownProps.filter
                },
                { dispatch, ownProps ->
                    onClick = { dispatch(SetVisibilityFilter(ownProps.filter)) }
                }
        )(Link::class.js.unsafeCast<RClass<LinkProps>>())
```

Далее в  создаем кнопки для фильтра.

```kotlin
fun RBuilder.filter_() =
        div {
            span { +"Фильтр: " }
            filterLink {
                attrs.filter = VisibilityFilter.SHOW_ALL
                +"Все"
            }
            filterLink {
                attrs.filter = VisibilityFilter.SHOW_ABSENT
                +"Отсутствует"
            }
            filterLink {
                attrs.filter = VisibilityFilter.SHOW_PRESENT
                +"Присутствует"
            }
        }
```

В filter визуализируем созданные кнопки

```kotlin
interface LinkProps : RProps {
    var active: Boolean
    var onClick: () -> Unit
}

class Link(props: LinkProps) : RComponent<LinkProps, RState>(props) {
    override fun RBuilder.render() {
        button {
            attrs.onClickFunction = { props.onClick() }
            children()
        }
    }
}
```


 <p> На рисунке 1 показана страница при изменении записи



<img src = 1.jpg>

На рисунке 2 показана странница на которой показан фильтр предметов на которых присутствовал студент или отсутствовал

<img src = 2.jpg>

На рисунке 3 показана страница на которой показан фильтр студентов которые присутсвовали или отсутсвовали на паре

<img src = 3_1.jpg>



