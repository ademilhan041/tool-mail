package com.ailhan.tool.mail.service

import com.ailhan.tool.mail.Attachment
import com.ailhan.tool.mail.Mail
import com.ailhan.tool.mail.MimeTypeMap
import jakarta.activation.DataHandler
import jakarta.mail.Message
import jakarta.mail.Session
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeBodyPart
import jakarta.mail.internet.MimeMessage
import jakarta.mail.internet.MimeMultipart
import jakarta.mail.util.ByteArrayDataSource
import java.lang.IllegalArgumentException

internal object MailUtil {
    private val CHARSET = Charsets.UTF_8.name()

    fun prepareMessage(mail: Mail, session: Session): MimeMessage {
        val message = MimeMessage(session)
        mail.toList.forEach {
            message.addRecipient(
                Message.RecipientType.TO,
                InternetAddress(it.email, it.name, it.charset)
            )
        }
        mail.ccList.forEach {
            message.addRecipient(
                Message.RecipientType.CC,
                InternetAddress(it.email, it.name, it.charset)
            )
        }
        mail.bccList.forEach {
            message.addRecipient(
                Message.RecipientType.BCC,
                InternetAddress(it.email, it.name, it.charset)
            )
        }

        message.setFrom(InternetAddress(mail.from!!.email, mail.from!!.name, mail.from!!.charset))
        message.setSubject(mail.subject, CHARSET)

        val multipart = MimeMultipart()

        if (mail.body != null) {
            val bodyPart = MimeBodyPart()
            bodyPart.setText(mail.body.content, CHARSET, mail.body.type.name.lowercase())
            multipart.addBodyPart(bodyPart)
        }

        if (mail.attachments.isNotEmpty()) {
            mail.attachments.forEach {
                val mimeType = MimeTypeMap.mimeTypeFromExtension(it.filename.split(".").last())
                    ?: throw IllegalArgumentException("MAIL_EXT_NOT_SUPPORTED")

                if (it.isInline) createInlineAttachment(multipart, it, mimeType) else createAttachment(
                    multipart,
                    it,
                    mimeType
                )
            }
        }

        message.setContent(multipart)

        for (name in mail.headers.keys) message.addHeader(name, mail.headers[name])

        return message
    }

    private fun createInlineAttachment(multipart: MimeMultipart, attachment: Attachment, mimeType: String) {
        val html = "<html>" +
                "<body>" +
                "<img style=\"width:100%; height:100%;\" src=\"cid:mail-image\">" +
                "</body>" +
                "</html>"
        val htmlBody = MimeBodyPart()
        htmlBody.setContent(html, "text/html")
        multipart.addBodyPart(htmlBody)

        val imageBody = MimeBodyPart()
        imageBody.setHeader("Content-ID", "<mail-image>")
        imageBody.dataHandler = DataHandler(ByteArrayDataSource(attachment.content, mimeType))
        imageBody.fileName = attachment.filename
        imageBody.disposition = MimeBodyPart.INLINE
        multipart.addBodyPart(imageBody)
    }

    private fun createAttachment(multipart: MimeMultipart, attachment: Attachment, mimeType: String) {
        val imageBody = MimeBodyPart()
        imageBody.dataHandler = DataHandler(ByteArrayDataSource(attachment.content, mimeType))
        imageBody.fileName = attachment.filename
        multipart.addBodyPart(imageBody)
    }
}
