package com.ailhan.tool.mail

class Mail(
    val toList: List<EmailName> = mutableListOf(),
    val ccList: List<EmailName> = mutableListOf(),
    val bccList: List<EmailName> = mutableListOf(),
    val subject: String = "",
    val body: Body? = null,
    val attachments: MutableList<Attachment> = mutableListOf(),
    val headers: Map<String, String> = mutableMapOf()
) {
    var from: EmailName? = null

    fun with(
        toList: List<EmailName> = this.toList,
        ccList: List<EmailName> = this.ccList,
        bccList: List<EmailName> = this.bccList,
        subject: String = this.subject,
        body: Body? = this.body,
        attachments: MutableList<Attachment> = this.attachments,
        headers: Map<String, String> = this.headers
    ): Mail {
        return Mail(toList, ccList, bccList, subject, body, attachments, headers)
    }

    fun withFrom(from: EmailName): Mail {
        val newMail = with()
        newMail.from = from
        return newMail
    }

    override fun toString(): String {
        return "Mail(" +
                "toList=$toList, " +
                "ccList=$ccList, " +
                "bccList=$bccList, " +
                "subject='$subject', " +
                "body=$body, " +
                "attachments=${attachments.size}, " +
                "headers=$headers, " +
                "from=$from" +
                ")"
    }
}
