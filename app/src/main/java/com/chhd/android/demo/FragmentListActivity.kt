package com.chhd.android.demo

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chhd.android.common.entity.BaseListData
import com.chhd.android.common.entity.BaseResponseData
import com.chhd.android.common.http.HttpObserver
import com.chhd.android.common.http.ResponseTransformer
import com.chhd.android.common.http.RetrofitProvider
import com.chhd.android.common.http.RxHelper
import com.chhd.android.common.mvp.IPageView
import com.chhd.android.common.ui.activity.ToolbarActivity
import com.chhd.android.common.ui.adapter.FragmentAdapter
import io.reactivex.*
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureBuffer
import kotlinx.android.synthetic.main.fragment_list.*
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class FragmentListActivity : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_list)

        val list = ArrayList<Fragment>()
        list.add(ListFragment())
        list.add(ListFragment())
        list.add(ListFragment())
        list.add(ListFragment())
        list.add(ListFragment())
        list.add(ListFragment())

        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        viewPager.adapter = FragmentAdapter(supportFragmentManager, list)
    }

    class ListFragment : com.chhd.android.common.ui.fragment.PullToRefreshFragment<ListAdapter, Entity>() {
        override fun onCreateAdapter(): ListAdapter {
            return ListAdapter(list)
        }

        override fun getContentResId(): Int {
            return R.layout.fragment_list
        }

        override fun isAutoLoad(): Boolean {
            return false
        }

        override fun onLoad(isLoadMore: Boolean) {
            retrofit()
                    .create(Api::class.java)
                    .getList(listData.getPageStart(isLoadMore), 50)
                    .compose(RxHelper.ioMainThread())
                    .compose(ResponseTransformer.transform())
                    .compose(this.bindUntilDestroy())
                    .subscribe(object : HttpObserver<ListData<Entity>>() {
                        @Throws(Exception::class)
                        override fun onSucceed(objectListData: ListData<Entity>?) {
                            objectListData!!.setStart(listData.getPageStart(isLoadMore))
                            onLoadSuccess(objectListData)
                        }

                        override fun showPageView(): IPageView {
                            return this@ListFragment
                        }
                    })

//            Observable
//                    .timer(2000, TimeUnit.MILLISECONDS)
//                    .flatMap { it ->
//                        val list = ArrayList<Entity>()
//                        for (i in 0..50) {
//                            list.add(Entity())
//                        }
//                        Observable.just(list)
//                    }
//                    .compose(RxHelper.ioMainThread())
//                    .compose(this.bindUntilDestroy())
//                    .subscribe(object : HttpObserver<List<Entity>>() {
//                        override fun onSucceed(t: List<Entity>?) {
//                            onLoadSuccess(t)
//                        }
//
//                        override fun showPageView(): IPageView {
//                            return this@ListFragment
//                        }
//                    })
        }

        override fun onInit(view: View?, savedInstanceState: Bundle?) {
            float_button.setOnClickListener {
                refresh()
            }
        }

        override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            super.onItemClick(adapter, view, position)
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        private fun retrofit(): Retrofit {
            return RetrofitProvider.newInstance("http://api.jisuapi.com/")
        }

        interface Api {

            @GET("news/get?channel=头条&appkey=a51fa7d0325d05f2")
            fun getList(@Query("start") start: Int, @Query("num") num: Int): Observable<ResponseData<ListData<Entity>>>
        }

    }

    class ListAdapter(data: List<Entity>?) : BaseQuickAdapter<Entity, BaseViewHolder>(R.layout.item_list_text, data) {

        override fun convert(helper: BaseViewHolder, item: Entity) {
            helper.setText(R.id.tv_name, "" + helper.layoutPosition)
        }
    }

    class ResponseData<T> : BaseResponseData<T> {

        private val status: Int? = null

        private val msg: String? = null

        private val result: T? = null

        override fun getCode(): Int? {
            return status
        }

        override fun getMessage(): String? {
            return msg
        }

        override fun getData(): T? {
            return result
        }

        override fun isSuccess(): Boolean? {
            return status == 0
        }
    }

    class ListData<T> : BaseListData<T>() {

        private val list: List<T>? = null

        private var start: Int = 0

        fun setStart(start: Int) {
            this.start = start
        }

        override fun getPageStart(): Int? {
            return start
        }

        override fun isPageNext(): Boolean? {
            return list?.isEmpty() == false
        }

        override fun getList(): List<T>? {
            return list
        }
    }

    class Entity {

        var title: String? = null
    }
}

