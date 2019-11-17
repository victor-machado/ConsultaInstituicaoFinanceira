package br.com.android.consultainstituicaofinanceira.ui.banklist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.android.consultainstituicaofinanceira.R
import br.com.android.consultainstituicaofinanceira.data.entities.Bank
import br.com.android.consultainstituicaofinanceira.extension.loadImage
import kotlinx.android.synthetic.main.adapter_item_bank.view.*
import kotlin.properties.Delegates

class BankAdapter(private val onBankClicked: (bank: Bank) -> Unit) :
    RecyclerView.Adapter<BankAdapter.BankViewHolder>() {

    private var banksList: List<Bank> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.adapter_item_bank, parent, false)
        val holder = BankViewHolder(view)

        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                onBankClicked.invoke(banksList[holder.adapterPosition])
            }
        }
        return holder
    }

    override fun getItemCount(): Int = banksList.size

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val bank: Bank = banksList[position]
            holder.bind(bank)
        }
    }

    fun updateData(newBanksList: List<Bank>) {
        banksList = newBanksList
    }

    class BankViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(bank: Bank){
            itemView.bankItemName.text = "${bank.code} - ${bank.name}"
            bank.image?.let {
                if(it.isNotBlank()) bindImageView(it)
            }?: bank.imageName?.let {
                if(it.isNotBlank()) bindImageView(it)
            }
        }

        private fun bindImageView(imagePath: String) {
            itemView.bankItemImageView.loadImage(imagePath)
        }
    }
}