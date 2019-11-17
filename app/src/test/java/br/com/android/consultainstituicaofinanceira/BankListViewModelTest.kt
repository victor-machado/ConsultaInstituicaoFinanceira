package br.com.android.consultainstituicaofinanceira

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.android.consultainstituicaofinanceira.data.entities.Bank
import br.com.android.consultainstituicaofinanceira.data.repositories.BankRepository
import br.com.android.consultainstituicaofinanceira.ui.banklist.BankListViewModel
import br.com.android.consultainstituicaofinanceira.utils.UseCaseResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.*
import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BankListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var bankRepository: BankRepository
    lateinit var bankListViewModel: BankListViewModel
    private val dispatcher = Dispatchers.Unconfined

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        bankListViewModel = BankListViewModel(bankRepository, Dispatchers.Unconfined)
    }

    @Test
    fun getBankListSuccess() {
        runBlocking {
            val bankName = "Bradesco"
            val bankCode = "237"
            val favorite = true
            val emptyString = ""

            val successResponse = UseCaseResult.Success(listOf(
                Bank(
                    bankName,
                    bankCode,
                    favorite,
                    emptyString,
                    emptyString
                )
            ))

            coEvery { bankRepository.getBanksList() } returns successResponse

            bankListViewModel.selectedBanksList.observeForever {}
            bankListViewModel.loadBanks(dispatcher)

            val bankList = bankListViewModel.selectedBanksList.value

            assertNotNull(bankList)
            assertEquals(bankName, bankList?.get(0)?.name)
            assertEquals(bankCode, bankList?.get(0)?.code)
            assertTrue(bankList?.get(0)?.favorite ?: false)
        }

    }

    @Test
    fun getBankListError() {
        runBlocking {
            val msg = "teste"
            var ex = Exception(msg)

            val errorResponse = UseCaseResult.Error(ex)
            coEvery { bankRepository.getBanksList() } returns errorResponse

            bankListViewModel.selectedBanksList.observeForever {}
            bankListViewModel.loadBanks(dispatcher)

            val bankList = bankListViewModel.selectedBanksList.value
            val showError = bankListViewModel.showError.value

            assertNull(bankList)
            assertNotNull(showError)
            assertEquals(msg, showError)
        }

    }
}
