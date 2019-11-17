package br.com.android.consultainstituicaofinanceira.module

import br.com.android.consultainstituicaofinanceira.data.remote.BankApi
import br.com.android.consultainstituicaofinanceira.data.repositories.BankRepository
import br.com.android.consultainstituicaofinanceira.data.repositories.IBankRepository
import br.com.android.consultainstituicaofinanceira.ui.banklist.BankListViewModel
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BANK_API_BASE_URL = "https://private-anon-326265aae2-tqiandroid.apiary-mock.com/"

val appModules = module {

    single {
        createWebService<BankApi>(
            okHttpClient = createHttpClient(),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = BANK_API_BASE_URL
        )
    }

    factory<IBankRepository> { BankRepository(bankApi = get()) }

    viewModel { BankListViewModel(bankRepository = get(), dispatcher = Dispatchers.Main) }
}

fun createHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
    client.readTimeout(5 * 60, TimeUnit.SECONDS)
    return client.addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        val request = requestBuilder.method(original.method(), original.body()).build()
        return@addInterceptor it.proceed(request)
    }.build()
}

inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    factory: CallAdapter.Factory, baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(factory)
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}

@GlideModule
class BankSearchGlideModule : AppGlideModule()