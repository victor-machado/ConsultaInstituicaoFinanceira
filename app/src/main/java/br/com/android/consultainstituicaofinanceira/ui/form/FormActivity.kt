package br.com.android.consultainstituicaofinanceira.ui.form

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.android.consultainstituicaofinanceira.R
import br.com.android.consultainstituicaofinanceira.data.entities.Bank
import br.com.android.consultainstituicaofinanceira.extension.loadImage
import br.com.android.consultainstituicaofinanceira.ui.banklist.BankListActivity
import kotlinx.android.synthetic.main.activity_form.*

const val REQUEST_CODE_BANK_LIST = 1
const val BANK_EXTRA = "bank"
const val BANK_SAVED_BUNDLE = "savedBank"

class FormActivity : AppCompatActivity() {

    private lateinit var bank: Bank

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        formFinancialInstitutionTextView.setOnClickListener {
            startActivityForResult(
                Intent(this, BankListActivity::class.java),
                REQUEST_CODE_BANK_LIST
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_BANK_LIST) {
            if (resultCode == Activity.RESULT_OK) {
                bank = data?.getSerializableExtra(BANK_EXTRA) as Bank
                setBankData(bank)
            }
        }
    }

    private fun setBankData(bank: Bank) {
        formFinancialInstitutionTextView.text = "${bank.code} - ${bank.name}"
        formFinancialInstitutionImageView.loadImage(resources.getDrawable(R.drawable.bank))
        bank.image?.let {
            if (it.isNotBlank()) formFinancialInstitutionImageView.loadImage(it)
        } ?: bank.imageName?.let {
            if (it.isNotBlank()) formFinancialInstitutionImageView.loadImage(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::bank.isInitialized) outState.putSerializable(BANK_SAVED_BUNDLE, bank)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getSerializable(BANK_SAVED_BUNDLE)?.let {
            bank = it as Bank
            setBankData(bank)
        }

    }

}