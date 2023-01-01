package com.ailhan.tool.mail.service.aws

import com.ailhan.tool.mail.service.MailFromConfig
import java.util.*

/**
 * mail.aws.public
 * mail.aws.secret
 * mail.aws.region=[ EU_WEST_1 ]
 * mail.from.username
 * mail.from.password
 * mail.from.name
 */
class MailAwsConfig(props: Properties) : MailFromConfig(props) {
    var public = props.getProperty("mail.aws.public", "")
    var secret = props.getProperty("mail.aws.secret", "")
    var region = props.getProperty("mail.aws.region", "")
}
