import kotlin.browser.document
import kotlin.js.Date
fun main() {
    val Data = Date() // текущее время
    val Year = Data.getFullYear()
    val Day = Data.getDate()
    val months = arrayOf(
        "Января", "Февраля", "Матрта",
        "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября",
        "Октября", "Ноября", "Декабря"
    )
    val Month = Data.getMonth()
    val Hours = Data.getHours()
    val Minutes = Data.getMinutes()
    val Seconds = Data.getSeconds()

    document.write("<h2> Текущая дата: "+Day+" "+months[Month]+" "+Year+" года <br>")
    document.write(" <br> Текущее время: "+Hours+":"+Minutes+":"+Seconds+" <h2>")
    var date_new = "January 1,2021 00:00"
    var date_st = Date(date_new)
    var msPerDay = 24*60*60*1000 //Количество миллисекунд в одном дне
    var days = ((date_st.getTime() - Data.getTime())/msPerDay).toInt() //Высчитываем количество дней
    var daysn = days%10
    var word = arrayOf("дней","день","дня","дня","дня","дней","дней","дней","дней","дней")
    var  dayname= word[daysn]
    document.write(" <h2>  До нового года осталось "+days+" "+dayname+"<h2>")
}