package com.pcandriod.kusitms_hackathon_c.presentation.ui.main.map

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import com.pcandriod.kusitms_hackathon_c.BuildConfig
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentMapBinding
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.MainActivity

class MapFragment : Fragment(), OnMapReadyCallback {

    lateinit var fragmentMapBinding: FragmentMapBinding
    lateinit var mainActivity: MainActivity

    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap

    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentMapBinding = FragmentMapBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        mapView = fragmentMapBinding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)


        NaverMapSdk.getInstance(mainActivity).client = NaverMapSdk.NaverCloudPlatformClient("${BuildConfig.MAP_API_KEY}")


        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        fragmentMapBinding.run {

        }

        return fragmentMapBinding.root
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map

        SystemClock.sleep(1000)

        // 확대 축소
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0

        // 지도 옵션 설정
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)
        naverMap.isIndoorEnabled = true

        // 초기 위치 설정
        val cameraUpdate = CameraUpdate.scrollTo((LatLng(37.631651, 127.077487))).animate(
            CameraAnimation.Easing
        )
        naverMap.moveCamera(cameraUpdate)

//        // 현위치 받아오기
//        val uiSettings = naverMap.uiSettings
//        uiSettings.isLocationButtonEnabled = false
//
//        // 로케이션 버튼 재할당
////        locationButton.map = naverMap
//
//        naverMap.locationTrackingMode = LocationTrackingMode.Face

        // 현재 위치 설정
//        locationSource = FusedLocationSource(this, CURRENT_LOCATION_CODE)
//        naverMap.locationSource = locationSource

    }
}