package com.pcandriod.kusitms_hackathon_c.presentation.ui.main.map

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.pcandriod.kusitms_hackathon_c.BuildConfig
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.data.data.ResultSearchKeyword
import com.pcandriod.kusitms_hackathon_c.data.module.api.ApiModule
import com.pcandriod.kusitms_hackathon_c.data.remote.request.SignInRequest
import com.pcandriod.kusitms_hackathon_c.data.remote.response.MapStoreResponse
import com.pcandriod.kusitms_hackathon_c.data.remote.response.ResponseSignIn
import com.pcandriod.kusitms_hackathon_c.data.remote.service.KakaoAPI
import com.pcandriod.kusitms_hackathon_c.data.remote.service.LoginService
import com.pcandriod.kusitms_hackathon_c.data.remote.service.MapService
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentMapBinding
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapFragment : Fragment(), OnMapReadyCallback {

    lateinit var fragmentMapBinding: FragmentMapBinding
    lateinit var mainActivity: MainActivity

    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap

    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체

    var storeId = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentMapBinding = FragmentMapBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        mapView = fragmentMapBinding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        val sheetBehavior = BottomSheetBehavior.from(fragmentMapBinding.includeStoreInfo.bottomSheetStoreInfo)

        sheetBehavior.isHideable = true
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN


        NaverMapSdk.getInstance(mainActivity).client = NaverMapSdk.NaverCloudPlatformClient("${BuildConfig.MAP_API_KEY}")


        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        fragmentMapBinding.run {

            svPlace.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    // 검색 버튼 누를 때 호출
                    searchKeyword(query.toString())

                    mainActivity.hideKeyboard()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    // 검색창에서 글자가 변경이 일어날 때마다 호출

                    return true
                }
            })

            fragmentMapBinding.includeStoreInfo.bottomSheetStoreInfo.setOnClickListener {
                val fragmentManager = requireActivity().supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                mainActivity.storeId = storeId
                transaction.replace(R.id.fv_main, StoreInfoFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            }
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

        getStoreMapList()

        SystemClock.sleep(1000)

        // 확대 축소
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0

        // 지도 옵션 설정
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)
        naverMap.isIndoorEnabled = true

        // 초기 위치 설정
        val cameraUpdate = CameraUpdate.scrollTo((LatLng(37.565669, 127.019452))).animate(
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

    // 키워드 검색 함수
    private fun searchKeyword(keyword: String) {
        val retrofit = Retrofit.Builder() // Retrofit 구성
            .baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPI::class.java) // 통신 인터페이스를 객체로 생성
        val call = api.getSearchKeyword(MainActivity.API_KEY, keyword) // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(object: Callback<ResultSearchKeyword> {
            override fun onResponse(
                call: Call<ResultSearchKeyword>,
                response: Response<ResultSearchKeyword>
            ) {
                // 통신 성공 (검색 결과는 response.body()에 담겨있음)
                Log.d("큐커톤", "Raw: ${response.raw()}")
                Log.d("큐커톤", "Body: ${response.body()}")
                Log.d("큐커톤", "first Body: ${ response.body()!!.documents[0].place_name}")
            }

            override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                // 통신 실패
                Log.w("큐커톤", "통신 실패: ${t.message}")
            }
        })
    }

    fun getStoreMapList() {

        val sheetBehavior = BottomSheetBehavior.from(fragmentMapBinding.includeStoreInfo.bottomSheetStoreInfo)

        val api = ApiModule.getInstance().create(MapService::class.java)

        api.getStoreMapList().enqueue(object : Callback<List<MapStoreResponse>> {
            override fun onResponse(call: Call<List<MapStoreResponse>>, response: Response<List<MapStoreResponse>>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: List<MapStoreResponse>? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    // Marker
                    val markers = mutableListOf<Marker>()

                    for (i in 0 until result!!.size) {
                        val marker = Marker()
                        var latitude = result?.get(i)!!.latitude.toDouble()
                        var longitude = result?.get(i)!!.longitude.toDouble()
                        marker.position = LatLng(longitude, latitude)
                        marker.icon = OverlayImage.fromResource(R.drawable.ic_marker)
                        markers.add(marker)
                    }

                    for (m in 0 until markers.size) {
                        markers[m].map = naverMap
                        markers[m].setOnClickListener {
                            for (i in 0 until markers.size) {
                                if (markers[i].icon == OverlayImage.fromResource(R.drawable.ic_marker_click)) {
                                    markers[i].icon =
                                        OverlayImage.fromResource(R.drawable.ic_marker)
                                }
                            }
                            markers[m].icon = OverlayImage.fromResource(R.drawable.ic_marker_click)
                            storeId = result?.get(m)!!.id
                            mainActivity.storeName = result?.get(m)!!.name
                            mainActivity.storeReviewCount = result?.get(m)!!.fenceNum
                            fragmentMapBinding.includeStoreInfo.run {
                                tvStoreName.text = result?.get(m)!!.name
                                tvStoreReview.text = "울타리 ${result?.get(m)!!.fenceNum}"
                                tvStoreReviewCount.text = "${result?.get(m)!!.fenceNum}"
                            }
                            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                            true
                        }
                    }

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    var result: List<MapStoreResponse>? = response.body()
                    val errorBody = response.errorBody()?.string() // 에러 응답 데이터를 문자열로 얻음
                    Log.d("##","Error Response: $errorBody")
                    Log.d("##", "onResponse 실패")
                    Log.d("##", "onResponse 실패: " + response.code())
                }
            }

            override fun onFailure(call: Call<List<MapStoreResponse>>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}