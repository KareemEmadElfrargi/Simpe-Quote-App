package com.example.simpequote

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.simpequote.Adapter.QuoteAdapter
import com.example.simpequote.Remote.QuoteResponse
import com.example.simpequote.databinding.ActivityMainBinding
import com.example.simpequote.ui.QuoteInteractionListener
import com.example.simpequote.util.Quote
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity(),QuoteInteractionListener {
    lateinit var binding : ActivityMainBinding
    private val client = OkHttpClient()
    private val quotesList = mutableListOf<Quote>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appCompatButton.setOnClickListener {
            makeRequestUsingOKHTTP()
        }

    }
    private fun makeRequestUsingOKHTTP() {
        Log.i("MAIN_ACTIVITY","Make Request..")
        val url = HttpUrl.Builder()
            .scheme("https")
            .host("api.quotable.io")
            .addPathSegment("random")
            .build()

        val request =okhttp3.Request.Builder().url(url).build()
        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread{
                    binding.animationView.apply {
                        setAnimation(R.raw.error)
                        playAnimation()
                    }
                    binding.appCompatButton.visibility = View.GONE
                    Log.i("WHERE",e.message.toString())
                    Toast.makeText(applicationContext, e.message.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                response.body?.string().let{jsonString ->
                val result = Gson().fromJson(jsonString,QuoteResponse::class.java)
                    val quote = Quote(
                        result.idQuote,
                        result.contentQuote,
                        result.authorQuote,
                        result.tagsQuote,
                        result.authorSlug,
                        result.length,
                        result.dateAdded,
                        result.dateModified
                    )
                    quotesList.add(quote)
                    runOnUiThread {
                        binding.apply {
                            animationView.visibility = View.GONE
                            appCompatButton.text = "Get More Quotes"
                        }
                        val adapter = QuoteAdapter(quotesList.reversed(),this@MainActivity)
                        binding.recycleQuotes.adapter = adapter

                    }
                }
            }

        })

    }
    override fun copyTextToClipboard(copyText: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("quote", copyText)
        clipboardManager.setPrimaryClip(clipData)
    }
}