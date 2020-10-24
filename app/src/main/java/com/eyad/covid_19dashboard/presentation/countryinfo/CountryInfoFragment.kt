package com.eyad.covid_19dashboard.presentation.countryinfo

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eyad.covid_19dashboard.R
import com.eyad.covid_19dashboard.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_country_info.*
import java.text.SimpleDateFormat
import java.util.*

class CountryInfoFragment: BaseFragment() {

    override val layoutResId = R.layout.fragment_country_info

    private val args: CountryInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        toolbar.title = args.country.name
        tv_date.text = SimpleDateFormat("d/M/yyyy", Locale.ENGLISH).format(args.date)
        tv_today_open_cases.text = args.country.todayNewCases.toString()
        tv_danger_level.text = when (args.country.dangerLevel) {
            1 -> "Very Low"
            2 -> "Low"
            3 -> "High"
            else -> "Very High"
        }
        tv_total_cases.text = args.country.totalCases.toString()
        tv_total_deaths.text = args.country.totalDeaths.toString()
        btn_news_feed.setOnClickListener {
            findNavController().navigate(CountryInfoFragmentDirections.actionCountryInfoFragmentToNewsFragment(args.country))
        }
    }

}