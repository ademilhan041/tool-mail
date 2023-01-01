package com.ailhan.tool.mail

data class Body(val content: String, val type: BodyType = BodyType.PLAIN) {
    override fun toString(): String {
        return "Body(content='...', type=$type)"
    }
}
