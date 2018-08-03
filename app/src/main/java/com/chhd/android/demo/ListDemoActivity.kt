package com.chhd.android.demo

import android.os.Bundle
import android.widget.TextView
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chhd.android.common.entity.BaseListData
import com.chhd.android.common.entity.BaseResponseData
import com.chhd.android.common.http.RetrofitProvider
import com.chhd.android.common.http.flowable.HttpObserver
import com.chhd.android.common.http.flowable.ResponseTransformer
import com.chhd.android.common.http.flowable.RxHelper
import com.chhd.android.common.mvp.IPageView
import com.chhd.android.common.ui.activity.toolbar.PullToRefreshActivity
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.activity_list_demo.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.http.*
import java.io.File

class ListDemoActivity : PullToRefreshActivity<ListDemoActivity.ListAdapter, ListDemoActivity.Entity>() {

    override fun getAdapter(): ListAdapter {
        return ListAdapter(list)
    }

    override fun getContentResId(): Int {
        return R.layout.activity_list_demo
    }

    override fun onInit(savedInstanceState: Bundle?) {
        float_button.setOnClickListener {
            refresh()
        }

        PermissionUtils.permission(PermissionConstants.STORAGE)
                .request()
    }

    override fun isAutoLoad(): Boolean {
        return false
    }

    override fun onLoad(isLoadMore: Boolean) {
        val file = File("/storage/emulated/0/DCIM/20180802_164640878_0b531c7b5d8e04f48de90ec1f4a6c83a.jpg")
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val part = MultipartBody.Part.createFormData("file", file.getName(), requestFile)
        retrofit()
                .create(Api::class.java)
                .getList(listData.getPageStart(isLoadMore), "10", part)
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
                        return this@ListDemoActivity
                    }
                })
    }

    override fun getToolbarTitle(): CharSequence {
        return "列表"
    }

    private fun retrofit(): Retrofit {
        val map = HashMap<String, String>()
        map.put("auth", "1a2f2pT+xGpECOQiS3aIc+rYVRST+wDqYXBVPTIOtZvdfKWxbOXmJlUqGhby1MACj5FQLfmZ28EvXj9+bIEwp9/r/xEHDmDWtUSPq36JE")
        map.put("app-id", "3")
        map.put("version", "1.9.9")
        map.put("client-id", "6cdb35a734d05e3cd4c06a41a1a677d4")
        return RetrofitProvider.newInstance("http://api.aems.zhimadi.cn/", map)
    }

    interface Api {

        @Multipart
        @POST("sell/index")
        fun getList(@Part("start") start: Int,
                    @Part("limit") num: String,
                    @Part part: MultipartBody.Part
        ): Flowable<ResponseData<ListData<Entity>>>
    }

    class Body {

        var start = 0
        var limit = 10
    }

    class ListAdapter(data: List<Entity>?) : BaseQuickAdapter<Entity, BaseViewHolder>(R.layout.item_list_text, data) {

        override fun convert(helper: BaseViewHolder, item: Entity) {

        }
    }

    class ResponseData<T> : BaseResponseData<T> {

        private val code: Int? = null

        private val msg: String? = null

        private val data: T? = null

        override fun getCode(): Int? {
            return code
        }

        override fun getMessage(): String? {
            return msg
        }

        override fun getData(): T? {
            return data
        }

        override fun isSuccess(): Boolean? {
            return code == 0
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
            return !list!!.isEmpty()
        }

        override fun getList(): List<T>? {
            return list
        }
    }

    class Entity {

        var title: String? = null
    }
}
