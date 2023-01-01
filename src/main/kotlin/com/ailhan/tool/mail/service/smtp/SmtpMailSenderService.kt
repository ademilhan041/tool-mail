package com.ailhan.tool.mail.service.smtp

import com.ailhan.tool.mail.EmailName
import com.ailhan.tool.mail.Mail
import com.ailhan.tool.mail.MailSenderService
import com.ailhan.tool.mail.service.MailUtil
import jakarta.mail.Authenticator
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import java.util.*

class SmtpMailSenderService(private val config: MailSmtpConfig) : MailSenderService {
    override fun send(mail: Mail): Boolean {
        mail.from = prepareFrom()
        val message = MailUtil.prepareMessage(mail, session())
        Transport.send(message)
        return true
    }

    private fun prepareFrom() = EmailName(config.from, config.fromName)

    private fun session(): Session {
        val props = Properties()
        props["mail.smtp.host"] = config.host
        props["mail.smtp.port"] = config.port
        props["mail.smtp.auth"] = "true"
        if (config.tlsPort.isNotBlank()) {
            props["mail.smtp.starttls.enable"] = "true"
            props["mail.smtp.socketFactory.port"] = config.tlsPort
            props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        }

        return Session.getInstance(props,
            object : Authenticator() {
                override fun getPasswordAuthentication() = PasswordAuthentication(config.from, config.password)
            })
    }
}
