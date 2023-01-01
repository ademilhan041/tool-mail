package com.ailhan.tool.mail.service.aws

import com.ailhan.tool.mail.EmailName
import com.ailhan.tool.mail.Mail
import com.ailhan.tool.mail.MailSenderService
import com.ailhan.tool.mail.service.MailUtil
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder
import com.amazonaws.services.simpleemail.model.RawMessage
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest
import jakarta.mail.Message
import jakarta.mail.Session
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.*

class AwsMailSenderService(private val config: MailAwsConfig) : MailSenderService {
    private val ses: AmazonSimpleEmailService = AmazonSimpleEmailServiceClientBuilder.standard()
        .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(config.public, config.secret)))
        .withRegion(Regions.valueOf(config.region))
        .build()

    override fun send(mail: Mail): Boolean {
        mail.from = prepareFrom()
        val message = MailUtil.prepareMessage(mail, session())
        ses.sendRawEmail(createAwsMailReq(message))
        return true
    }

    private fun prepareFrom() = EmailName(config.from, config.fromName)

    private fun session(): Session {
        return Session.getDefaultInstance(Properties())
    }

    private fun createAwsMailReq(message: Message): SendRawEmailRequest {
        val outputStream = ByteArrayOutputStream()
        message.writeTo(outputStream)
        return SendRawEmailRequest(RawMessage(ByteBuffer.wrap(outputStream.toByteArray())))
    }
}
