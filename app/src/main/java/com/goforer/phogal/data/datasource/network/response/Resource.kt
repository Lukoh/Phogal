package com.goforer.phogal.data.datasource.network.response

class Resource constructor(var status: Status = Status.LOADING) {
    internal var message: String? = null

    internal var data: Any? = null

    internal var errorCode: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val resource = other as Resource?

        if (status !== resource?.status) {
            return false
        }

        val messageEqualed = if (message != null)
            message == resource.message
        else
            resource.message == null

        return if (messageEqualed)
            true
        else {
            val resourceEqualed = if (data != null)
                data == resource.data
            else
                resource.data == null

            resourceEqualed
        }
    }

    override fun hashCode(): Int {
        var result = status.hashCode()

        result = 31 * result + if (message != null) message!!.hashCode() else 0
        result = 31 * result + if (data != null) data?.hashCode()!! else 0

        return result
    }

    override fun toString(): String =
        "Resource{" +
                "status=" + status +
                ", message='" + message + '\''.toString() +
                ", data=" + data +
                '}'.toString()

    internal fun success(data: Any?): Resource {
        status = Status.SUCCESS
        this.errorCode = 0
        this.data = data
        message = null

        return this
    }

    internal fun error(msg: String?, errorCode: Int): Resource {
        status = Status.ERROR
        this.errorCode = errorCode
        this.data = null
        message = msg

        return this
    }

    internal fun loading(data: Any?): Resource {
        status = Status.LOADING
        this.errorCode = 0
        this.data = data
        message = null

        return this
    }
}