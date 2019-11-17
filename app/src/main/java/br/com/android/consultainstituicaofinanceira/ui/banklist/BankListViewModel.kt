package br.com.android.consultainstituicaofinanceira.ui.banklist

import androidx.lifecycle.MutableLiveData
import br.com.android.consultainstituicaofinanceira.data.entities.Bank
import br.com.android.consultainstituicaofinanceira.data.repositories.IBankRepository
import br.com.android.consultainstituicaofinanceira.ui.base.BaseViewModel
import br.com.android.consultainstituicaofinanceira.ui.base.SingleLiveEvent
import br.com.android.consultainstituicaofinanceira.utils.UseCaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BankListViewModel(private val bankRepository: IBankRepository, dispatcher: CoroutineDispatcher) : BaseViewModel(dispatcher) {

    private val allBanksList = MutableLiveData<List<Bank>>()
    val selectedBanksList = MutableLiveData<List<Bank>>()
    val favoriteBanksList = MutableLiveData<List<Bank>>()
    val banksList = MutableLiveData<List<Bank>>()
    val showLoading = MutableLiveData<Boolean>()
    val showError = SingleLiveEvent<String>()
    val navigateToForm = SingleLiveEvent<Bank>()

    fun loadBanks(dispatcher: CoroutineDispatcher) {
        showLoading.value = true

        launch {

            val result = withContext(dispatcher) { bankRepository.getBanksList() }

            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> {
                    allBanksList.value = result.data
                    selectedBanksList.value = allBanksList.value

                    updateLists()
                }
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    private fun updateLists() {
        favoriteBanksList.value = selectedBanksList.value?.filter { it.favorite }
        banksList.value = selectedBanksList.value?.filter { !it.favorite }
    }

    fun bankClicked(bank: Bank) {
        navigateToForm.value = bank
    }

    fun searchBank(searchString: String) {
        selectedBanksList.value = allBanksList.value?.filter {
            it.name.contains(searchString) or it.name.toLowerCase().contains(searchString) or it.code.contains(searchString) or it.code.toLowerCase().contains(searchString)
        }
        updateLists()
    }
}