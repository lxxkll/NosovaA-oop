package redux

import data.*

class ChangePresent(val lessonID: Int, val studentID: Int) : RAction

class add_Student(val student: Student) : RAction

class RemoveStudent(val id: Int) : RAction

class ChangeStudent(val id: Int, val newStudent: Student) : RAction

class add_Lesson(val lesson: Lesson) : RAction

class RemoveLesson(val id: Int) : RAction

class ChangeLesson(val id: Int, val newLesson: Lesson) : RAction

class SetVisibilityFilter(val filter: VisibilityFilter) : RAction