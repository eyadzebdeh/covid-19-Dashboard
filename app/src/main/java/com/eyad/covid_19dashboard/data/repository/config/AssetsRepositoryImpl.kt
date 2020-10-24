package com.eyad.covid_19dashboard.data.repository.config

import android.content.Context
import com.eyad.covid_19dashboard.domain.model.CountryTrackingInfo
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import io.reactivex.Single
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class AssetsRepositoryImpl(private val context: Context, private val gson: Gson) : AssetsRepository{

    override fun getPolygonsForCountries(countries: Map<String, CountryTrackingInfo>): Single<Map<String, CountryTrackingInfo>> {
        return Single.create {
            val userJsonString = loadJSONFromAsset("polygons.json")
            try{
                val jsonObject = JSONObject(userJsonString)
                val jsonArray = jsonObject.getJSONArray("data")
                for (i in 0 until jsonArray.length()){
                    val array = jsonArray.getJSONArray(i)
                    val country = array.getString(8)
                    val polygons = getPolygons(array.getString(9))
                    countries[country]?.polygons = polygons
                }
            }catch (e: JSONException){
                e.printStackTrace()
            }
            it.onSuccess(countries)
        }
    }

    private fun getPolygons(value: String): List<List<LatLng>>{
        val polygons = ArrayList<ArrayList<LatLng>>()

        var valueString = value.substring(14, value.length - 1)
        var polygonsString = valueString.split("), (")
        for (p in polygonsString){
            var polygonsString = p
            polygonsString = polygonsString.replace("((", "[")
            polygonsString = polygonsString.replace("(", "[")
            polygonsString = polygonsString.replace("))", "]")
            polygonsString = polygonsString.replace(")", "]")
            polygonsString = polygonsString.replace(",", "],[")
            polygonsString = polygonsString.replace("[ ", "[")
            polygonsString = polygonsString.replace(" ", ",")
            polygonsString = "[$polygonsString]"
            try {
                val jsonArray = JSONArray(polygonsString)
                val polygon = ArrayList<LatLng>()
                for (i in 0 until jsonArray.length()){
                    val items = jsonArray.getJSONArray(i)
                    val latlng = LatLng(items.getDouble(1), items.getDouble(0))
                    polygon.add(latlng)
                }
                polygons.add(polygon)
            }catch (e: JSONException){
                e.printStackTrace()
            }
        }

        return polygons
    }

    private fun getPolygon(value: String) : List<LatLng> {
        val polygon = ArrayList<LatLng>()
        val polygonString = value.substring(16, value.length - 3)
        val pairs = polygonString.split(",")
        for (pair in pairs){
            val values: ArrayList<String> = pair.split(" ") as ArrayList<String>
            if (values[0] == ""){
                values.removeAt(0)
            }
            val latLng = LatLng(values[1].toDouble(), values[0].toDouble())
            polygon.add(latLng)
        }
        return polygon
    }

    private fun loadJSONFromAsset(jsonName: String): String? {
        return try {
            val `is` = context.assets.open(jsonName)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }
}