package component

import hoc.withDisplayName
import kotlinx.html.InputType

import kotlinx.html.js.onClickFunction

import org.w3c.dom.events.Event
import react.*
import react.dom.*



interface Any_add_or_del_Props<O> : RProps {
    var subobjs: Array<O>
    var name : String
    var path : String
    var add: (Event) -> Unit
    var del: (Event) -> Unit
}

fun <O> Any_add_or_dell_Full(
    rChange: RBuilder.() -> ReactElement,
    rComponent: RBuilder.(Array<O>, String, String) -> ReactElement
) =
    functionalComponent<Any_add_or_del_Props<O>>
    {props ->
        div {
            h3 {
                +"Изменение"
            }
            rChange()
            input(type = InputType.submit) {
                attrs.onClickFunction = props.add
            }
            input(type = InputType.reset) {
                attrs.onClickFunction = props.del
            }
            rComponent(props.subobjs,props.name,props.path)
        }
    }

fun <O> RBuilder.Any_add_or_dell_Full(
    rChange: RBuilder.() -> ReactElement,
    rComponent: RBuilder.(Array<O>, String, String) -> ReactElement,
    subobjs: Array<O>,
    name : String,
    path : String,
    add: (Event) -> Unit,
    del: (Event) -> Unit
) = child(
    withDisplayName("Any_add_or_dell_Full",  Any_add_or_dell_Full<O>(rChange, rComponent))
){
    attrs.subobjs = subobjs
    attrs.name = name
    attrs.path = path
    attrs.add = add
    attrs.del = del
}