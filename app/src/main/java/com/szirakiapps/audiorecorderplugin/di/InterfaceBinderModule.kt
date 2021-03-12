package com.szirakiapps.audiorecorderplugin.di

import com.szirakiapps.audiorecorderplugin.model.service.RecorderService
import com.szirakiapps.audiorecorderplugin.model.service.RecorderServiceImpl
import dagger.Binds
import dagger.Module

@Module
interface InterfaceBinderModule {

    @Binds
    fun bind(i: RecorderServiceImpl): RecorderService
}