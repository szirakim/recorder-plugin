package com.szirakiapps.audiorecorderplugin.model.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.DisplayMetrics
import java.util.*

object RuntimeLocaleUtil {

    fun setLocale(context: Context, lang: String) {

        if (!SupportedLocales.list.contains(lang)) {
            return
        }

        val myLocale = Locale(lang)
        val res: Resources = context.resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration

        conf.setLocale(myLocale)
        res.updateConfiguration(conf, dm)
    }

    fun defaultLocale(context: Context) {
        setLocale(context, Locale.getDefault().language)
    }
}