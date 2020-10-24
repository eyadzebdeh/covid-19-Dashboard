package com.eyad.covid_19dashboard.presentation.map

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.eyad.covid_19dashboard.R
import com.eyad.covid_19dashboard.domain.model.CountryTrackingInfo
import com.eyad.covid_19dashboard.presentation.base.BaseFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions
import kotlinx.android.synthetic.main.fragment_covid_map.*
import java.net.UnknownHostException
import java.util.*

class CovidMapFragment : BaseFragment(), OnMapReadyCallback{

    override val layoutResId = R.layout.fragment_covid_map
    override val dependencyInjectionEnabled = true

    private var map: GoogleMap? = null

    private lateinit var viewModel: CovidMapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CovidMapViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_date.setOnClickListener { showDatePicker() }

        val map = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        map.getMapAsync(this)

        handleObservers()
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        viewModel.date.value?.let {
            calendar.time = it
        }
        DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                calendar.set(year, month, day)
                viewModel.date.value = calendar.time
                getTrackingInfo()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun handleObservers() {
        viewModel.progressLiveData?.observe(viewLifecycleOwner, Observer {
            handleLoading(it)
        })

        viewModel.failureLiveData?.observe(viewLifecycleOwner, Observer {
            if (it is UnknownHostException){
                showToast(getString(R.string.lbl_no_internet_connection))
            }else{
                showToast(getString(R.string.lbl_fetch_info_error))
            }
        })

        viewModel.emptyLiveData.observe(viewLifecycleOwner, Observer {
            showToast(getString(R.string.lbl_no_info_available))
        })

        viewModel.trackingInfoLiveData.observe(viewLifecycleOwner, Observer {
            handleTrackingInfoLiveData(it)
        })
    }

    private fun handleTrackingInfoLiveData(map: Map<String, CountryTrackingInfo>) {
        this.map?.clear()
        for (value in map.values){
            val polygons = value.polygons
            polygons?.let{
                for (polygon in polygons){
                    addPolygon(value.name, polygon, value.dangerLevel)
                }
            }
        }
    }

    private fun addPolygon(
        id: String,
        polygon: List<LatLng>,
        dangerLevel: Int
    ) {
        val color = getPolygonColor(dangerLevel)
        val p = map?.addPolygon(PolygonOptions()
            .clickable(true)
            .addAll(polygon)
            .strokeWidth(0.5f)
            .fillColor(color))
        p?.tag = id
    }

    private fun getPolygonColor(dangerLevel: Int): Int {
        val resId = when (dangerLevel) {
            1 -> R.color.colorLowestDanger
            2 -> R.color.colorLowDanger
            3 -> R.color.colorHighDanger
            else -> R.color.colorHighestDanger
        }
        return ContextCompat.getColor(requireContext(), resId)
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        map?.setOnPolygonClickListener {
            handlePolygonClick(it)
        }
        viewModel.trackingInfoLiveData.value?.let{
            handleTrackingInfoLiveData(it)
        } ?: getTrackingInfo()
    }

    private fun handlePolygonClick(polygon: Polygon) {
        viewModel.getCountryTrackingInfo(polygon.tag as String)?.let{
            findNavController().navigate(CovidMapFragmentDirections.actionMapFragmentToCountryInfoFragment(it, viewModel.date.value ?: Date()))
        }
    }

    private fun getTrackingInfo(){
        viewModel.getTrackingInfo()
    }

}