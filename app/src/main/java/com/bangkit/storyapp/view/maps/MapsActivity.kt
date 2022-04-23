package com.bangkit.storyapp.view.maps

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.storyapp.R
import com.bangkit.storyapp.databinding.ActivityMapsBinding
import com.bangkit.storyapp.util.showError
import com.bangkit.storyapp.util.showLoading
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var markers: ArrayList<Marker?> = arrayListOf()

    private val viewModel by viewModels<MapsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.getStoriesWithLocation()

        viewModel.isError.observe(this) {
            showError(it, applicationContext, "Unable to fetch stories")
        }

        viewModel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMapToolbarEnabled = true

        setMapStyle()

        viewModel.stories.observe(this) { stories ->
            stories?.forEach {
                if (it?.lon != null && it.lat != null) {
                    val latLng = LatLng(it.lat.toDouble(), it.lon.toDouble())

                    // TODO : Check if it is okay to only show 1 marker if location is identical
                    markers.add(map.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .title(it.name)
                            // TODO: Change snippet to desc
                            .snippet(it.description)
                    ))
                }
            }

            setMapCamera()
        }
    }

    private fun setMapStyle() {
        try {
            val success =
                map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }

    private fun setMapCamera() {
        val listOfMarker = markers
        val b = LatLngBounds.Builder()
        for (m in listOfMarker) {
            m?.position?.let { b.include(it) }
        }
        val bounds = b.build()
        val paddingFromEdgeAsPX = 100
        val cu = CameraUpdateFactory.newLatLngBounds(bounds,paddingFromEdgeAsPX)
        map.animateCamera(cu)
    }

    companion object {
        private const val TAG = "MapsActivity"
    }
}