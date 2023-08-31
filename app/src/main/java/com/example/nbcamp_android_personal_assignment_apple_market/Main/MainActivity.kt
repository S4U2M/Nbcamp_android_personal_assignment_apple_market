package com.example.nbcamp_android_personal_assignment_apple_market.Main

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbcamp_android_personal_assignment_apple_market.DB.SellerDB
import com.example.nbcamp_android_personal_assignment_apple_market.Detail.DetailActivity
import com.example.nbcamp_android_personal_assignment_apple_market.databinding.MainActivityBinding


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    companion object {
        const val CHANNEL_ID = "my_channel"
        const val SELLER = "seller"
        const val ITEM_POSITION = "position"

        fun newIntnentMain(context: Context) = Intent(context, MainActivity::class.java)
    }

    private val mainAdapter by lazy {
        MainAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()


    }

    //백버튼 감지
    override fun onBackPressed() {

        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("앱 종료")
        builder.setMessage("앱을 종료하시겠습니까?")
        builder.setPositiveButton("예") { _, _ ->
            super.onBackPressed() // 앱 종료
        }
        builder.setNegativeButton("아니오") { dialog, _ ->
            dialog.dismiss() // 다이얼로그 닫기
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun initView() = with(binding) {

        binding.marketList.adapter = mainAdapter
        binding.marketList.layoutManager = LinearLayoutManager(this@MainActivity)


        //아이템 클릭 이벤트

        //디테일 페이지 이동

//        val activityLancher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            if(it.resultCode == RESULT_OK){
//                val position = it.data?.getIntExtra(DetailActivity.ITEM_POSITION_DETAIL,-1) as Int
//                val isLike = it.data?.getBooleanExtra(DetailActivity.ISLIKE,false) as Boolean
//
//                if(isLike == true){
//                    SellerDB.sellerModelDB[position].like += 1
//                }else{
//                    SellerDB.sellerModelDB[position].like -= 1
//                }
//
//                mainAdapter.getItemChanged(SellerDB.sellerModelDB[position].like,position=position)
//            }
//            }

        mainAdapter.itemClick = object : MainAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val seller = SellerDB.sellerModelDB[position]
                val intent = Intent(DetailActivity.newIntentDetail(this@MainActivity)).apply {
                    putExtra(SELLER, seller)
                    putExtra(ITEM_POSITION, position)
                }

                startActivity(intent)
//                activityLancher.launch(intent)
            }
        }

        //아이템 삭제
        mainAdapter.itemLongClick = object : MainAdapter.ItemLongClick {
            override fun onLongClick(view: View, position: Int) {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("리스트 삭제")
                builder.setMessage("정말로 삭제하시겠습니까?")
                builder.setPositiveButton("예") { _, _ ->

                    SellerDB.sellerModelDB.removeAt(position)
                    mainAdapter.notifyItemRemoved(position)

                }
                builder.setNegativeButton("아니오") { dialog, _ ->
                    dialog.dismiss() // 다이얼로그 닫기
                }
                val dialog = builder.create()
                dialog.show()
            }
        }


        //플러팅 버튼
        fabUp.setOnClickListener {
            marketList.smoothScrollToPosition(0)
        }

        val fadeIN = AlphaAnimation(0f, 1f).apply { duration = 100 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 100 }

        marketList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val findTopPosition = layoutManager.findFirstVisibleItemPosition()
                if (findTopPosition == 0) {
                    fabUp.startAnimation(fadeOut)
                    fabUp.visibility = View.GONE
                } else {
                    fabUp.visibility = View.VISIBLE
                    fabUp.startAnimation(fadeIN)
                }
            }
        })

    }


}