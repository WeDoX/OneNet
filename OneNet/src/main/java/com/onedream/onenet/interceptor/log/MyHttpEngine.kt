package com.onedream.onenet.interceptor.log

import okhttp3.Headers
import okhttp3.Response
import java.net.HttpURLConnection.*

/**
 *
 * @author jdallen
 * @since 2020/12/28
 */
object MyHttpEngine {
    private const val HTTP_CONTINUE = 100

    /**
     * Returns true if the response must have a (possibly 0-length) body. See RFC 2616 section 4.3.
     */
    fun hasBody(response: Response): Boolean {
        // HEAD requests never yield a body regardless of the response headers.
        if (response.request().method() == "HEAD") {
            return false
        }

        val responseCode = response.code()
        if ((responseCode < HTTP_CONTINUE || responseCode >= HTTP_OK)
            && responseCode != HTTP_NO_CONTENT
            && responseCode != HTTP_NOT_MODIFIED
        ) {
            return true
        }

        // If the Content-Length or Transfer-Encoding headers disagree with the
        // response code, the response is malformed. For best compatibility, we
        // honor the headers.
        /*  if (OkHeaders.contentLength(response) != -1
                || "chunked".equalsIgnoreCase(response.header("Transfer-Encoding"))) {
            return true;
        }*/
        return contentLength(response) != -1L || "chunked".equals(
                response.header("Transfer-Encoding"),
                ignoreCase = true
            )

    }

    private fun contentLength(response: Response): Long {
        return contentLength(response.headers())
    }

    private fun contentLength(headers: Headers): Long {
        return stringToLong(headers.get("Content-Length"))
    }

    private fun stringToLong(s: String?): Long {
        if (s == null) {
            return -1
        }
        return try {
            java.lang.Long.parseLong(s)
        } catch (e: NumberFormatException) {
            -1
        }

    }

}