package com.example.android.weatherapp.services

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesProvider(var context: Context?) {
    companion object {
        private lateinit var pref: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        private const val PREF_NAME = "SHARED_PREFERENCE"

        private const val IS_FIRST_TIME_LAUNCH = "IS_FIRST_TIME_LAUNCH"

        private const val IS_THE_INTERNET_ENABLED = "IS_THE_INTERNET_ENABLED"
        private const val IS_THE_LOCATION_ENABLED = "IS_THE_LOCATION_ENABLED"

        private const val IS_AlARM_SWITCHED_ON = "IS_AlARM_SWITCHED_ON"

        private const val LANGUAGE_BTN_CHECKED_ID = "LANGUAGE_BTN_CHECKED_ID"
        private const val UNITS_BTN_CHECKED_ID = "UNITS_BTN_CHECKED_ID"

        private const val LAT_SHARED_PREF = "LAT_SHARED_PREF"
        private const val LONG_SHARED_PREF = "LONG_SHARED_PREF"

        private const val LAT_SHARED_PREF_FAV = "LAT_SHARED_PREF_FAV"
        private const val LONG_SHARED_PREF_FAV = "LONG_SHARED_PREF_FAV"

        private const val UNITS_SHARED_PREF = "UNITS_SHARED_PREF"
        private const val METRIC_UNITS_SHARED_PREF = "METRIC_UNITS_SHARED_PREF"

        private const val LANGUAGE_SHARED_PREF = "LANGUAGE_SHARED_PREF"

    }

    init {
        pref = context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)!!
        editor = pref.edit()
    }

    fun setFirstTimeLaunch(isFirstTime: Boolean){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
        editor.commit()
    }

    val isFirstTimeLaunch: Boolean
        get()= pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)


    fun setIsTheInternetEnabled(isInternetEnabled: Boolean){
        editor.putBoolean(IS_THE_INTERNET_ENABLED, isInternetEnabled)
        editor.commit()
    }

    val isTheInternetEnabled: Boolean
        get() = pref.getBoolean(IS_THE_INTERNET_ENABLED, false)

    fun setIsTheLocationEnabled(isLocationEnabled: Boolean){
        editor.putBoolean(IS_THE_LOCATION_ENABLED, isLocationEnabled)
        editor.commit()
    }

    val setIsTheLocationEnabled: Boolean
        get()= pref.getBoolean(IS_THE_LOCATION_ENABLED, false)

    fun setUnit(unit:String){
        editor.putString(UNITS_SHARED_PREF,unit)
        editor.apply()
    }

    val getUnit : String?
        get()= pref.getString(UNITS_SHARED_PREF,"metric")

    fun setLanguage(Language:String){
        editor.putString(LANGUAGE_SHARED_PREF,Language)
        editor.apply()
    }

    val getLanguage : String?
        get()= pref.getString(LANGUAGE_SHARED_PREF,"en")


    fun alarmSwitchedOn(converted: Boolean){
        editor.putBoolean(IS_AlARM_SWITCHED_ON, converted)
        editor.commit()
    }

    val isAlarmSwitchedOn: Boolean
        get()= pref.getBoolean(IS_AlARM_SWITCHED_ON, false)

    fun setLatLong(latitude: String?, longitude: String?) {
        editor.putString(LAT_SHARED_PREF, latitude)
        editor.putString(LONG_SHARED_PREF, longitude)
        editor.commit()
    }

    val latLong: Array<String?>
        get() {
            val location = arrayOfNulls<String>(2)
            val lat = pref.getString(LAT_SHARED_PREF, null)
            val lng = pref.getString(LONG_SHARED_PREF, null)
            location[0] = lat
            location[1] = lng
            return location
        }

    fun setLatLongFav(latitude: String?, longitude: String?) {
        editor.putString(LAT_SHARED_PREF_FAV, latitude)
        editor.putString(LONG_SHARED_PREF_FAV, longitude)
        editor.commit()
    }

    val latLongFav: Array<String?>
        get() {
            val location = arrayOfNulls<String>(2)
            val lat = pref.getString(LAT_SHARED_PREF_FAV, null)
            val lng = pref.getString(LONG_SHARED_PREF_FAV, null)
            location[0] = lat
            location[1] = lng
            return location
        }

    fun setLanguageBtnId(id:Int){
        editor.putInt(LANGUAGE_BTN_CHECKED_ID,id)
        editor.apply()
    }

    val getLanguageBtnId : Int
        get()= pref.getInt(LANGUAGE_BTN_CHECKED_ID,1)

    fun setUnitsBtnId(id:Int){
        editor.putInt(UNITS_BTN_CHECKED_ID,id)
        editor.apply()
    }

    val getUnitsBtnId : Int
        get()= pref.getInt(UNITS_BTN_CHECKED_ID,1)
}