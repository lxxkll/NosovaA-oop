package redux

import data.*

fun presents_Reducer(state: Presents, action: RAction, id: Int = -1) =
    when (action) {
        is ChangePresent ->
            state.toMutableMap().apply {
                this[action.lessonID]?.let {
                    val old = it[action.studentID] ?: false
                    (it as MutableMap)[action.studentID] = !old
                }
            }
        is add_Lesson ->
            state.plus(id to state.values.first().keys.associateWith { false })
        is add_Student ->
            HashMap<Int, Map<Int, Boolean>>().toMutableMap().apply {
                state.map {
                    put(it.key, it.value.plus(id to false))
                }
            }
        is RemoveLesson -> state.minus(action.id)
        is RemoveStudent ->
            HashMap<Int, Map<Int, Boolean>>().toMutableMap().apply {
                state.map {
                    put(it.key, it.value.minus(action.id))
                }
            }
        else -> state
    }




fun lessons_Reducer(state: LessonState, action: RAction, newId: Int = -1) =
    when (action) {
        is add_Lesson -> state + (newId to action.lesson)
        is RemoveLesson -> state.minus(action.id)
        is ChangeLesson ->
            state.toMutableMap()
                .apply {
                    this[action.id] = action.newLesson
                }
        else -> state
    }




fun students_Reducer(state: StudentState, action: RAction, newId: Int = -1) =
    when (action) {
        is add_Student -> state + (newId to action.student)
        is RemoveStudent -> state.minus(action.id)
        is ChangeStudent ->
            state.toMutableMap()
                .apply {
                    this[action.id] = action.newStudent
                }
        else -> state
    }




fun root_Reducer(state: State, action: RAction) =
    when (action) {
        is add_Lesson -> {
            val id = state.lessons.newId()
            State(
                lessons_Reducer(state.lessons, action, id),
                students_Reducer(state.students, action),
                presents_Reducer(state.presents, action, id),
                    visibility(state.visibilityFilter, action)
            )
        }
        is add_Student -> {
            val id = state.students.newId()
            State(
                lessons_Reducer(state.lessons, action),
                students_Reducer(state.students, action, id),
                presents_Reducer(state.presents, action, id),
                    visibility(state.visibilityFilter, action)
            )
        }
        else ->
            State(
                lessons_Reducer(state.lessons, action),
                students_Reducer(state.students, action),
                presents_Reducer(state.presents, action),
                    visibility(state.visibilityFilter, action)
            )
    }




fun visibility(state: VisibilityFilter, action: RAction) =
        when (action) {
            is SetVisibilityFilter -> action.filter
             else -> state
}
