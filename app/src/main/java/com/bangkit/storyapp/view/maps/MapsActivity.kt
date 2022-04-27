package com.bangkit.storyapp.view.maps

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.storyapp.R
import com.bangkit.storyapp.data.Result
import com.bangkit.storyapp.databinding.ActivityMapsBinding
import com.bangkit.storyapp.factory.ViewModelFactory
import com.bangkit.storyapp.model.story.ListStoryItem
import com.bangkit.storyapp.util.showError
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var viewModel: MapsViewModel

    private var markers: ArrayList<Marker?> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory(this))[MapsViewModel::class.java]

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMapToolbarEnabled = true

        setMapStyle()

        viewModel.getStoriesWithLocation().observe(this) { result ->
            if(result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val stories = result.data
                        setStoriesToMap(stories)
                        setMapCamera()
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        showError(true, this, "Unable to fech stories")
                    }
                }
            }
        }
    }

    private fun setStoriesToMap(stories: List<ListStoryItem>?) {
        stories?.forEach {
            if (it.lon != null && it.lat != null) {
                val latLng = LatLng(it.lat.toDouble(), it.lon.toDouble())
                Log.d("MARKERS", it.toString())

                markers.add(map.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(it.name)
                        .snippet(it.description)
                ))
            }
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