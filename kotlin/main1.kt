import data.studentList
import data.subjectList
import react.dom.h1
import react.dom.li
import react.dom.ol
import react.dom.render
import kotlin.browser.document


fun main() {
    render(document.getElementById("root")!!) {
        h1 {
            +"Список предметов и студентов"
        }
                    rsubject(subjectList)
}
    }
