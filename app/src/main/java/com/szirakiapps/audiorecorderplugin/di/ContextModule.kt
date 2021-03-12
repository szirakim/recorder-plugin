package com.szirakiapps.audiorecorderplugin.di

import android.content.ContentResolver
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.szirakiapps.audiorecorderplugin.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class ContextModule(private val context: Context) {

    @Provides
    @ContextScope
    fun context(): Context = context

    @Provides
    @ContextScope
    @Inject
    fun contentResolver(context: Context): ContentResolver = context.contentResolver

    @Provides
    fun mainViewModel(): MainViewModel =
        ViewModelProviders.of(context as FragmentActivity).get(MainViewModel::class.java)
}
