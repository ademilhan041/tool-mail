package com.ailhan.tool.mail.builder

import com.ailhan.tool.mail.MailSenderService
import com.ailhan.tool.mail.service.DummyMailSenderService
import com.ailhan.tool.mail.service.aws.AwsMailSenderService
import com.ailhan.tool.mail.service.aws.MailAwsConfig
import com.ailhan.tool.mail.service.smtp.MailSmtpConfig
import com.ailhan.tool.mail.service.smtp.SmtpMailSenderService

class MailSenderServiceBuilder {
    private lateinit var _mailType: MailType

    private lateinit var _smtpConfig: MailSmtpConfig
    private lateinit var _awsConfig: MailAwsConfig

    fun smtp(config: MailSmtpConfig): MailSenderServiceBuilder {
        this._mailType = MailType.SMTP
        this._smtpConfig = config
        return this
    }

    fun aws(config: MailAwsConfig): MailSenderServiceBuilder {
        this._mailType = MailType.AWS
        this._awsConfig = config
        return this
    }

    fun dummy(): MailSenderServiceBuilder {
        this._mailType = MailType.DUMMY
        return this
    }

    fun build(): MailSenderService {
        if (!::_mailType.isInitialized) throw IllegalStateException("MAIL_SENDER_MODULE_BUILD_FAILED")

        return when (_mailType) {
            MailType.DUMMY -> DummyMailSenderService()
            MailType.SMTP -> SmtpMailSenderService(_smtpConfig)
            MailType.AWS -> AwsMailSenderService(_awsConfig)
        }
    }

    private enum class MailType { DUMMY, SMTP, AWS }
}
