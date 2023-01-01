package com.ailhan.tool.mail

data class EmailName(val email: String, val name: String = "", val charset: String = Charsets.UTF_8.name()) {
    companion object {
        fun unknown() = EmailName("unknown", "unknown")
    }
}
