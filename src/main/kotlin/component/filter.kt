package component

import container.filterLink
import data.VisibilityFilter
import react.RBuilder
import react.dom.div
import react.dom.span

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