package com.example.nbcamp_android_personal_assignment_apple_market.Detail

import android.content.Context
import android.content.Intent
import android.icu.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nbcamp_android_personal_assignment_apple_market.DB.SellerModel
import com.example.nbcamp_android_personal_assignment_apple_market.Main.MainActivity
import com.example.nbcamp_android_personal_assignment_apple_market.R
import com.example.nbcamp_android_personal_assignment_apple_market.databinding.DetailActivityBinding
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    companion object {
        const val ITEM_POSITION_DETAIL = "positiondetail"
        const val ISLIKE = "islike"

        fun newIntentDetail(context: Context) = Intent(context, DetailActivity::class.java)
    }

    private lateinit var binding: DetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() = with(binding) {

        val getSeller = intent.getParcelableExtra<SellerModel>(MainActivity.SELLER)
        val getPosition = intent.getIntExtra(MainActivity.ITEM_POSITION, -1)

        if (getSeller == null) return


        //텍스트 제어

        detailImg.setImageResource(getSeller.img)
        detailProfileName.text = getSeller.name
        detailProfileAdress.text = getSeller.homeAdress
        detailDescriptionTitle.text = getSeller.title
        detailDescriptionText.text = getSeller.detail
        detailBottomBarPrice.text = "${
            NumberFormat.getNumberInstance(Locale.getDefault()).format(getSeller.price)
        }원"

        //like 변환

        var isLike = false

        detailBottomBarLike.setOnClickListener {

            isLike = !isLike

            if (isLike) {
                detailBottomBarLike.setImageResource(R.drawable.ic_like_clicked)
            } else {
                detailBottomBarLike.setImageResource(R.drawable.ic_like)
            }

        }

        //버튼 제어

        detailBackBtn.bringToFront()

//        fun data() {
//            val intent = MainActivity.newIntnentMain(this@DetailActivity).apply {
//                putExtra(ISLIKE, isLike)
//                putExtra(ITEM_POSITION_DETAIL, getPosition)
//                setResult(RESULT_OK, intent)
//                finish()
//            }
//        }

        detailBackBtn.setOnClickListener {
//            data()
            finish()
        }
//        fun onBackPressed() {
//            data()
//        }

    }
}