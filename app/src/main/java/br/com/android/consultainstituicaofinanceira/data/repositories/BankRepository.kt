package br.com.android.consultainstituicaofinanceira.data.repositories

import br.com.android.consultainstituicaofinanceira.data.entities.Bank
import br.com.android.consultainstituicaofinanceira.data.remote.BankApi
import br.com.android.consultainstituicaofinanceira.utils.UseCaseResult
import java.lang.Exception

interface IBankRepository {
    suspend fun getBanksList(): UseCaseResult<List<Bank>>
}

class BankRepository(private val bankApi: BankApi): IBankRepository{
    override suspend fun getBanksList(): UseCaseResult<List<Bank>> {
        return try{
            val result = bankApi.getBanksAsync().await()
            UseCaseResult.Success(result)
        } catch (ex: Exception){
            UseCaseResult.Error(ex)
        }
    }

}