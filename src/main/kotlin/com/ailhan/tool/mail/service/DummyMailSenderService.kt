package com.ailhan.tool.mail.service

import com.ailhan.tool.mail.Mail
import com.ailhan.tool.mail.MailSenderService

class DummyMailSenderService : MailSenderService {
    override fun send(mail: Mail) = true
}
