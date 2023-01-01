package com.ailhan.tool.mail.service

import java.util.*

open class MailFromConfig(props: Properties) {
    var from = props.getProperty("mail.from.username", "")
    var password = props.getProperty("mail.from.password", "")
    var fromName = props.getProperty("mail.from.name", "")
}
