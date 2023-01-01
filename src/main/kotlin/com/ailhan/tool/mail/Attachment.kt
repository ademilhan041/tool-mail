package com.ailhan.tool.mail

data class Attachment(val filename: String, val content: ByteArray, val isInline: Boolean = false)
