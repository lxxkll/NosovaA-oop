package container

import component.*
import data.*
import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.*
import redux.*
import react.redux.rConnect

interface AnyFullDispatchProps : RProps {
    var onClick: (Int) -> (Event) -> Unit
}

interface AnyFullStateProps<O, S> : RProps {
    var subobjs: Map<Int, S>
    var presents: Map<Int, Boolean>?
}

interface AnyFullOwnProps<O> : RProps {
    var obj: Pair<Int, O>
}

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


val lessonFullContainer =
    rConnect<
            State,
            RAction,
            WrapperAction,
            AnyFullOwnProps<Lesson>,
            AnyFullStateProps<Lesson, Student>,
            AnyFullDispatchProps,
            AnyFullProps<Lesson, Student>>(
        { state, ownProps ->
            subobjs = visibleStudents(state.students, state.presents[ownProps.obj.first], state.visibilityFilter)
            presents = state.presents[ownProps.obj.first]
        },
        { dispatch, ownProps ->
            onClick =
                { index ->
                    {
                        dispatch(ChangePresent(ownProps.obj.first, index))
                    }
                }
        }
    )(
        withDisplayName(
            "LessonFull",
            fAnyFull<Lesson, Student>(RBuilder::student)
        )
            .unsafeCast<RClass<AnyFullProps<Lesson, Student>>>()
    )

val studentFullContainer =
    rConnect<
            State,
            RAction,
            WrapperAction,
            AnyFullOwnProps<Student>,
            AnyFullStateProps<Student, Lesson>,
            AnyFullDispatchProps,
            AnyFullProps<Student, Lesson>>(
        { state, ownProps ->
            subobjs = visibleLessons(state.lessons, state.presentsStudent(ownProps.obj.first), state.visibilityFilter)
            presents = state.presentsStudent(ownProps.obj.first)
        },
        { dispatch, ownProps ->
            onClick = { index ->
                {
                    dispatch(ChangePresent(index, ownProps.obj.first))
                }
            }
        }
    )(
        withDisplayName(
            "StudentFull",
            fAnyFull<Student, Lesson>(RBuilder::lesson)
        )
            .unsafeCast<RClass<AnyFullProps<Student, Lesson>>>()
    )