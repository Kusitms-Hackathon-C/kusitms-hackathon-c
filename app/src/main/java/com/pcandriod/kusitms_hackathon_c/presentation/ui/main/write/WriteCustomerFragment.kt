package com.pcandriod.kusitms_hackathon_c.presentation.ui.main.write

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.data.data.PostItem
import com.pcandriod.kusitms_hackathon_c.data.module.api.ApiModule
import com.pcandriod.kusitms_hackathon_c.data.remote.request.SignInRequest
import com.pcandriod.kusitms_hackathon_c.data.remote.request.WriteCustomerRequest
import com.pcandriod.kusitms_hackathon_c.data.remote.response.ResponsePost
import com.pcandriod.kusitms_hackathon_c.data.remote.response.ResponseSignIn
import com.pcandriod.kusitms_hackathon_c.data.remote.service.HomeService
import com.pcandriod.kusitms_hackathon_c.data.remote.service.WriteService
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentWriteCustomerBinding
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentWriteOwnerBinding
import com.pcandriod.kusitms_hackathon_c.presentation.adapter.PostAdapter
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.MainActivity
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteCustomerFragment(var itemList: ArrayList<PostItem>) : Fragment() {

    lateinit var fragmentWriteCustomerBinding: FragmentWriteCustomerBinding
    lateinit var mainActivity: MainActivity
    lateinit var albumLauncher: ActivityResultLauncher<Intent>

    // 업로드할 이미지의 Uri
    var uploadUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentWriteCustomerBinding = FragmentWriteCustomerBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        // 앨범 설정
        albumLauncher = albumSetting(fragmentWriteCustomerBinding.ivRegisterImage)

        fragmentWriteCustomerBinding.run {

            ivRegisterImage.visibility = View.GONE

            ibtnClose.setOnClickListener {

            }

            btnComplete.setOnClickListener {
                // 게시글 데이터 전송
                postCustomer()
            }

            ibtnRegisterImage.setOnClickListener {
                // 앨범에서 사진을 선택할 수 있는 Activity를 실행한다.
                val newIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                // 실행할 액티비티의 마임타입 설정(이미지로 설정해준다)
                newIntent.setType("image/*")
                // 선택할 파일의 타입을 지정(안드로이드  OS가 이미지에 대한 사전 작업을 할 수 있도록)
                val mimeType = arrayOf("image/*")
                newIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)

                // 액티비티를 실행한다.
                albumLauncher.launch(newIntent)

                val handler = Handler()
                handler.postDelayed({
                    ivRegisterImage.visibility = View.VISIBLE
                    ibtnRegisterImage.visibility = View.GONE
                }, 2000) // 2000ms (2초) 후에 RecyclerView 생성
            }

        }


        return fragmentWriteCustomerBinding.root
    }

    // 앨범 설정
    fun albumSetting(previewImageView: ImageView): ActivityResultLauncher<Intent> {
        val albumContract = ActivityResultContracts.StartActivityForResult()
        val albumLauncher = registerForActivityResult(albumContract) {
            if (it?.resultCode == AppCompatActivity.RESULT_OK) {
                // 선택한 이미지에 접근할 수 있는 Uri 객체를 추출한다.
                if (it.data?.data != null) {
                    uploadUri = it.data?.data

                    // 안드로이드 10 (Q) 이상이라면...
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        // 이미지를 생성할 수 있는 디코더를 생성한다.
                        val source =
                            ImageDecoder.createSource(mainActivity.contentResolver, uploadUri!!)
                        // Bitmap객체를 생성한다.
                        val bitmap = ImageDecoder.decodeBitmap(source)

                        previewImageView.setImageBitmap(bitmap)
                    } else {
                        // 컨텐츠 프로바이더를 통해 이미지 데이터 정보를 가져온다.
                        val cursor =
                            mainActivity.contentResolver.query(uploadUri!!, null, null, null, null)
                        if (cursor != null) {
                            cursor.moveToNext()

                            // 이미지의 경로를 가져온다.
                            val idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                            val source = cursor.getString(idx)

                            // 이미지를 생성하여 보여준다.
                            val bitmap = BitmapFactory.decodeFile(source)
                            previewImageView.setImageBitmap(bitmap)
                        }
                    }
                }
            }
        }

        return albumLauncher
    }


    private fun postCustomer() {
        val api = ApiModule.getInstance().create(WriteService::class.java)
        if (
            fragmentWriteCustomerBinding.etvContent.text.toString() != ""
            &&
            fragmentWriteCustomerBinding.etvTitle.text.toString() != ""
        ) {
            api.postCustomer(
                WriteCustomerRequest(
                    "",
                    "",
                    fragmentWriteCustomerBinding.etvTitle.toString(),
                    fragmentWriteCustomerBinding.etvContent.toString()
                )
            ).enqueue(object : Callback<ResponsePost> {
                override fun onResponse(
                    call: Call<ResponsePost>,
                    response: Response<ResponsePost>
                ) {
                    Log.d("HomeFragment", "API 성공 ${response.body()}")
                    val postItem = PostItem(
                        fragmentWriteCustomerBinding.etvTitle.toString(),
                        fragmentWriteCustomerBinding.etvContent.toString()
                    )
                    itemList.add(postItem)
                    val fragmentManager = requireActivity().supportFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.fv_main, HomeFragment(itemList))
                    transaction.addToBackStack(null)
                    transaction.commit()
                }

                override fun onFailure(call: Call<ResponsePost>, t: Throwable) {
                    Log.e("HomeFragment", "API 실패 ${t}")
                }

            })
        } else {
            Toast.makeText(context, "제목과 내용을 모두 입력하세요 !", Toast.LENGTH_SHORT).show()
        }

    }

}