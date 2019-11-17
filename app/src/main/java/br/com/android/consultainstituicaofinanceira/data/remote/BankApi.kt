package br.com.android.consultainstituicaofinanceira.data.remote

import br.com.android.consultainstituicaofinanceira.data.entities.Bank
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface BankApi {
    @GET("list")
    fun getBanksAsync(): Deferred<List<Bank>>
}