package com.eyad.covid_19dashboard.presentation.newsdetails

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.eyad.covid_19dashboard.R
import com.eyad.covid_19dashboard.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_news_details.*
import java.text.SimpleDateFormat
import java.util.*

class NewsDetailsFragment : BaseFragment(){

    override val layoutResId = R.layout.fragment_news_details

    private val args: NewsDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.article.apply {
            Glide.with(requireContext()).load(urlToImage).into(iv_image)
            publishedAt?.let{tv_date.text = SimpleDateFormat("d/MM/yyyy", Locale.ENGLISH).format(it)}
            tv_title.text = title
            source?.let { tv_source.text = it.name }
            tv_description.text = description
            content?.let { tv_content.text = it }
        }

        iv_close.setOnClickListener { findNavController().popBackStack() }

        args.article.content?.apply {
            val index = indexOf("[+")
            if (index >= 0){
                tv_content.makeLinks(
                    Pair(substring(index), View.OnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(args.article.url)
                        startActivity(intent)
                    })
                )
            }
        }
    }
}

fun TextView.makeLinks(
    vararg links: Pair<String, View.OnClickListener>
) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ContextCompat.getColor(context, R.color.design_default_color_primary)
            }
        }

        val startIndexOfLink = this.text.toString().indexOf(link.first)
        if (startIndexOfLink >= 0) {
            spannableString.setSpan(
                clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
    this.movementMethod = LinkMovementMethod.getInstance()
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}
