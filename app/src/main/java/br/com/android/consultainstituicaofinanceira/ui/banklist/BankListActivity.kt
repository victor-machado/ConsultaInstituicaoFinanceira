package br.com.android.consultainstituicaofinanceira.ui.banklist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.android.consultainstituicaofinanceira.R
import br.com.android.consultainstituicaofinanceira.data.entities.Bank
import br.com.android.consultainstituicaofinanceira.ui.banklist.adapter.BankAdapter
import br.com.android.consultainstituicaofinanceira.ui.form.BANK_EXTRA
import kotlinx.android.synthetic.main.activity_bank_list.*
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.ext.android.viewModel

class BankListActivity : AppCompatActivity() {

    private val viewModel: BankListViewModel by viewModel()
    private lateinit var bankAdapter: BankAdapter
    private lateinit var favoriteBankAdapter: BankAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_list)

        viewModel.loadBanks(Dispatchers.IO)

        val onBankClicked: (bank: Bank) -> Unit = { bank ->
            viewModel.bankClicked(bank)
        }

        favoriteBankAdapter = BankAdapter(onBankClicked)
        bankAdapter = BankAdapter(onBankClicked)

        bankListFavoriteRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@BankListActivity)
            adapter = favoriteBankAdapter
        }

        bankListRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@BankListActivity)
            adapter = bankAdapter
        }

        bankListSearchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(c: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun afterTextChanged(c: Editable?) {
                //
            }

            override fun onTextChanged(c: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchBank(c.toString())
            }
        })

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.favoriteBanksList.observe(this, Observer { newBanksList ->
            val textView = this@BankListActivity.bankListFavoriteTextView
            if(newBanksList.isEmpty()){
                textView.visibility = View.GONE
            }  else {
                textView.visibility = View.VISIBLE
                bankListSearchInputLayout.visibility = View.VISIBLE
            }
            favoriteBankAdapter.updateData(newBanksList)
        })

        viewModel.banksList.observe(this, Observer { newBanksList ->
            val textView = this@BankListActivity.bankListTextView
            if(newBanksList.isEmpty()){
                textView.visibility = View.GONE
            }  else {
                textView.visibility = View.VISIBLE
                bankListSearchInputLayout.visibility = View.VISIBLE
            }
            bankAdapter.updateData(newBanksList)
        })

        viewModel.showLoading.observe(this, Observer { showLoading ->
            bankListProgressBar.visibility = if (showLoading!!) View.VISIBLE else View.GONE
        })

        viewModel.showError.observe(this, Observer { showError ->
            Toast.makeText(this, showError, Toast.LENGTH_LONG).show()
        })

        viewModel.navigateToForm.observe(this, Observer { bank ->
            bank?.let {
                setResult(Activity.RESULT_OK, Intent().putExtra(BANK_EXTRA, bank))
                finish()
            }
        })
    }
}
