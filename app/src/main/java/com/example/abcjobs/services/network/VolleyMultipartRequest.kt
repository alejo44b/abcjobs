import android.content.Context
import android.net.Uri
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

open class VolleyMultipartRequest(
    method: Int,
    url: String,
    private val mListener: Response.Listener<NetworkResponse>,
    errorListener: Response.ErrorListener,
    private val context: Context,
    private val fileUri: Uri
) : Request<NetworkResponse>(method, url, errorListener) {

    private val mHeaders: MutableMap<String, String> = HashMap()

    override fun getHeaders(): Map<String, String> {
        return mHeaders
    }

    fun setHeader(name: String, value: String) {
        mHeaders[name] = value
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<NetworkResponse> {
        return try {
            Response.success(
                response,
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: Exception) {
            Response.error(ParseError(e))
        }
    }

    override fun deliverResponse(response: NetworkResponse) {
        mListener.onResponse(response)
    }

    override fun getBodyContentType(): String {
        return "application/pdf"
    }

    override fun getBody(): ByteArray {
        val bos = ByteArrayOutputStream()
        val inputStream = context.contentResolver.openInputStream(fileUri)
        val buffer = ByteArray(1024)
        var bytesRead: Int
        try {
            while (inputStream?.read(buffer).also { bytesRead = it ?: 0 } != -1) {
                bos.write(buffer, 0, bytesRead)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bos.toByteArray()
    }
}