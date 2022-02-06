package com.example.aqiproximityworks

import android.content.Context
import android.net.ConnectivityManager
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONArray
import java.lang.reflect.Type


/**
 * Created by Mukund Gururani, mkndmail@gmail.com on 2/5/2022
 */
class MainViewModel : ViewModel() {

    private val SOCKET_ADDRESS = "ws://city-ws.herokuapp.com/"
    private val _aqiOfCities = MutableLiveData<List<AQIForecastModel>>()
    val aqiOfCities: LiveData<List<AQIForecastModel>>
        get() = _aqiOfCities

    private
    val _setNoInternet = MutableLiveData<Boolean>()
    val setNoInternet: LiveData<Boolean>
        get() = _setNoInternet


    fun startSocket(client: OkHttpClient) {
        val request = Request.Builder().url(SOCKET_ADDRESS).build()

        client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket?, text: String?) {
                super.onMessage(webSocket, text)
                text?.let {
                    try {
                        val listType: Type = object : TypeToken<List<AQIForecastModel?>?>() {}.type
                        _aqiOfCities.postValue( Gson().fromJson(it, listType))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun isInternetAvailable(context: Context): Boolean {
        var haveConnectedWifi = false
        var haveConnectedMobile = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = cm!!.allNetworkInfo
        for (netWorkInfo in netInfo) {
            if (netWorkInfo.typeName.equals(
                    "WIFI",
                    ignoreCase = true
                )
            ) if (netWorkInfo.isConnected) haveConnectedWifi = true
            if (netWorkInfo.typeName.equals(
                    "MOBILE",
                    ignoreCase = true
                )
            ) if (netWorkInfo.isConnected) haveConnectedMobile = true
        }
        return haveConnectedWifi || haveConnectedMobile
    }

    fun setNoInternet(noInternet: Boolean) {
        _setNoInternet.value = noInternet
    }

}