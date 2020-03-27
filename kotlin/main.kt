//import component.addLesson
import component.app
import component.applesson
import data.*
import react.dom.render
import kotlin.browser.document


fun main() {
    render(document.getElementById("root")!!) {
        app(studentList)
    }
}