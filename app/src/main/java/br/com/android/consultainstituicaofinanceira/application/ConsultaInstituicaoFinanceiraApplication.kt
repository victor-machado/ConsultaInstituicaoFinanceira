package br.com.android.consultainstituicaofinanceira.application

import android.app.Application
import br.com.android.consultainstituicaofinanceira.module.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ConsultaInstituicaoFinanceiraApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ConsultaInstituicaoFinanceiraApplication)
            modules(appModules)
        }
    }
}