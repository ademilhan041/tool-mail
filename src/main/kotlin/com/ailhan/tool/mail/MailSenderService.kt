package com.ailhan.tool.mail

interface MailSenderService {
    fun send(mail: Mail): Boolean
}
