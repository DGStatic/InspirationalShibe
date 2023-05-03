package com.example.inspirationalshibe

import android.icu.util.ULocale
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

private const val TAG = "ShibeFragment"


class InspirationalShibeFragment : Fragment() {

    private lateinit var shibeRecyclerView: RecyclerView
    private lateinit var inspirationalViewModel: InspirationalShibeViewModel
    private lateinit var deepLiveData: LiveData<List<TranslatedText>>
    private var adapter: ShibeAdapter? = ShibeAdapter(emptyList(), emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inspirationalViewModel = ViewModelProviders.of(this).get(InspirationalShibeViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_inspirational_shibe, container, false)

        shibeRecyclerView = view.findViewById(R.id.shibe_recycler_view)
        shibeRecyclerView.layoutManager = LinearLayoutManager(context)
        shibeRecyclerView.adapter = adapter
        return view
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var shibeList: List<String> = emptyList()
        var quoteList: List<Quote> = emptyList()
        inspirationalViewModel.shibeLiveData.observe (
            viewLifecycleOwner,
            Observer { shibes ->
                shibeList = shibes
                Log.d(TAG, shibeList.size.toString())
                updateUI(shibeList, quoteList)
            }
                )
        inspirationalViewModel.quoteLiveData.observe (
            viewLifecycleOwner,
            Observer { quotes ->
                quoteList = quotes
                val locale = requireContext().resources.configuration.locale.country
                var language: String = ""
                language = locale
                if (language != "US") {
                    for (i in quoteList.indices) {
                        deepLiveData = DeepFetcher().fetchTranslated(language, quoteList[i].quote)
                        deepLiveData.observe (
                            viewLifecycleOwner,
                            Observer { translated ->
                                Log.d(TAG, translated[0].text)
                                quoteList[i].quote = translated[0].text
                                updateUI(shibeList, quoteList)
                            }
                                )
                    }
                }

                Log.d(TAG, quoteList.size.toString())
            }
                )



    }

    private fun updateUI(shibes: List<String>, quotes: List<Quote>) {
        adapter = ShibeAdapter(shibes, quotes)
        shibeRecyclerView.adapter = adapter
    }

    private class ShibeHolder(view: View)
        : RecyclerView.ViewHolder(view) {
        private lateinit var shibeURL: String
        private lateinit var quote: Quote
        private val shibeImageView: ImageView = itemView.findViewById(R.id.img_shibe)
        private val quoteTextView: TextView = itemView.findViewById(R.id.txt_quote)
        fun bind(shibeURL: String, quote: Quote) {
            this.shibeURL = shibeURL
            this.quote = quote
            Picasso.get().load(shibeURL).resize(1000, 1200).centerCrop().into(shibeImageView)
            quoteTextView.text = quote.quote
        }
    }

    private inner class ShibeAdapter(private val shibes: List<String>, private val quotes: List<Quote>)
        : RecyclerView.Adapter<ShibeHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int): ShibeHolder {

            val view = layoutInflater.inflate(R.layout.inspirational_list_item, parent, false)
            return ShibeHolder(view)
        }

        override fun getItemCount(): Int = shibes.size

        override fun onBindViewHolder(holder: ShibeHolder, position: Int) {
            val shibe = shibes[position]
            val quote = quotes[position]
            holder.bind(shibe, quote)
        }
    }

    companion object {
        fun newInstance() = InspirationalShibeFragment()
    }


}