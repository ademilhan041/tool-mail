package com.ailhan.tool.mail.service.smtp

import com.ailhan.tool.mail.service.MailFromConfig
import java.util.*

/**
 * mail.smtp.host
 * mail.smtp.port
 * mail.smtp.tlsport
 * mail.from.username
 * mail.from.password
 * mail.from.name
 */
class MailSmtpConfig(props: Properties) : MailFromConfig(props) {
    var host = props.getProperty("mail.smtp.host", "")
    var port = props.getProperty("mail.smtp.port", "")
    var tlsPort = props.getProperty("mail.smtp.tlsport", "")
}
