package redux


class ChangePresent(val lesson: Int, val student: Int) : RAction
class add_Student(val firstname:String, val surname: String): RAction
class del_Student(val del_student: Int) : RAction
class add_Lesson(val lesson: String): RAction
class del_Lesson(val del_lesson: Int): RAction

