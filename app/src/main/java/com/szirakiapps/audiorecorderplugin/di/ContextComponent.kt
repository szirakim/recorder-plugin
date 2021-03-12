package com.szirakiapps.audiorecorderplugin.di

import android.content.Context
import com.szirakiapps.audiorecorderplugin.MainActivity
import com.szirakiapps.audiorecorderplugin.viewmodel.MainViewModel
import dagger.Component

@Component(
    modules = [
        ContextModule::class,
        InterfaceBinderModule::class,
    ]
)
@ContextScope
interface ContextComponent {

    fun inject(activity: MainActivity)

    fun inject(viewModel: MainViewModel)

    companion object {
        fun build(context: Context): ContextComponent =
            DaggerContextComponent
                .builder()
                .contextModule(ContextModule(context))
                .build()
    }
}
