package com.example.simpequote.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.simpequote.R
import com.example.simpequote.databinding.ItemQuoteBinding
import com.example.simpequote.ui.QuoteInteractionListener
import com.example.simpequote.util.Quote
import okhttp3.Callback


class QuoteAdapter(private val quotesList:List<Quote>, private val listener: QuoteInteractionListener):RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quote,parent,false)
        return QuoteViewHolder(view)
    }
    override fun getItemCount() = quotesList.size

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val currentQuote = quotesList[position]
        holder.binding.apply{
            quoteTitle.text = currentQuote.tagsQuote[0]
            quoteText.text = currentQuote.contentQuote
            authorText.text = currentQuote.authorQuote

            if (currentQuote.tagsQuote[0] =="Famous Quotes"){
                card.setBackgroundResource(R.drawable.bc_card_famous_qoute)
                copyButton.apply {
                    setBackgroundResource(R.drawable.bc_button_clip_famous)
                    setTextColor(ContextCompat.getColor(copyButton.context, R.color.white))
                }

            }else{
                card.setBackgroundResource(R.drawable.bc_button)
                copyButton.apply {
                    setBackgroundResource(R.drawable.bc_button_clip)
                    setTextColor(ContextCompat.getColor(copyButton.context, R.color.black))
                }            }

            copyButton.setOnClickListener{
                listener.copyTextToClipboard(currentQuote.contentQuote)
            }
        }
    }
    class QuoteViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemQuoteBinding.bind(view)
    }


}



