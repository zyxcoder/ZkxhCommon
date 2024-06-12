package com.gxy.common.common.activitylist

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.gxy.common.databinding.FragmentBaseCommonListBinding
import com.gxy.common.base.BaseViewBindFragment
import com.gxy.common.common.loadsir.getLoadSir
import com.gxy.common.common.loadsir.setLoadContentStatus
import com.kingja.loadsir.core.LoadService
import com.zyxcoder.mvvmroot.base.adapter.BaseViewBindingAdapter

/**
 * @author zhangyuxiang
 * @date 2024/3/21
 * 一个滑动列表的fragment
 * 由于MVVMBuild框架底层限制，第一个泛型必须是BaseViewModel，第二个必须是ViewBinding，虽然有点冗余，但前两个泛型必须写死，对于VB传入时就传FragmentBaseCommonListBinding即可
 */
abstract class BaseCommonListFragment<VM : BaseCommonListFragmentViewModel<ItemDataEntity>, VB : FragmentBaseCommonListBinding, ItemVB : ViewBinding, ItemDataEntity>(
    private val title: String? = null
) : BaseViewBindFragment<VM, VB>() {

    /**
     * 返回列表页面需要刷新的intent
     */
    val intentActivityResultRefreshLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                startSearch()
            }
        }

    private lateinit var adapter: BaseViewBindingAdapter<ItemDataEntity, ItemVB>
    private lateinit var mLoadService: LoadService<Any>
    private var searchKey: String? = null


    /**
     * 当前Fragment在ViewPager中是第几个
     */
    var currentFragmentInViewPagerIndex: Int = 0

    /**
     * 获取请求参数，动态参数，由于是动态，设置和获取时请注意顺序
     */
    protected abstract fun provideRequestParams(): Array<out Any>

    /**
     * 列表adapter
     */
    protected abstract fun provideAdapter(): BaseViewBindingAdapter<ItemDataEntity, ItemVB>


    /**
     * 获取标题
     */
    fun getTitle() = title

    /**
     * 设置搜索key
     */
    open fun setSearchKey(inputSearchKey: String?) {
        searchKey = inputSearchKey
    }

    /**
     * 开始搜索
     */
    open fun startSearch() {
        //如果之前已经加载过才需要重新刷新
        if (isLazyLoaded) {
            mViewModel.getList(
                isFirst = true,
                isRefresh = false,
                start = 0,
                searchKey = searchKey,
                *provideRequestParams()
            )
        }
    }

    override fun lazyLoadData() {
        mViewModel.getList(
            isFirst = true,
            isRefresh = false,
            start = 0,
            searchKey = searchKey,
            *provideRequestParams()
        )
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewBind.apply {
            mLoadService = getLoadSir().register(refreshLayout) {
                mViewModel.getList(
                    isFirst = true,
                    isRefresh = false,
                    start = 0,
                    searchKey = searchKey,
                    *provideRequestParams()
                )
            }
            adapter = provideAdapter().apply {
                rvInfo.adapter = this
            }
            refreshLayout.apply {
                setOnRefreshListener {
                    mViewModel.getList(
                        isFirst = false,
                        isRefresh = true,
                        start = 0,
                        searchKey = searchKey,
                        *provideRequestParams()
                    )
                }
                setOnLoadMoreListener {
                    mViewModel.getList(
                        isFirst = false,
                        isRefresh = false,
                        start = rvInfo.adapter?.itemCount ?: 0,
                        searchKey = searchKey,
                        *provideRequestParams()
                    )
                }
            }
        }
    }

    override fun createObserver() {
        mViewModel.apply {
            loadContentStatus.observe(this@BaseCommonListFragment) {
                mLoadService.setLoadContentStatus(it)
            }
            isRefreshing.observe(this@BaseCommonListFragment) {
                if (!it) {
                    mViewBind.refreshLayout.finishRefresh()
                }
            }
            isLoading.observe(this@BaseCommonListFragment) {
                if (!it) {
                    mViewBind.refreshLayout.finishLoadMore()
                }
            }
            firstDatas.observe(this@BaseCommonListFragment) {
                adapter.setNewInstance(it)
            }
            moreDatas.observe(this@BaseCommonListFragment) {
                adapter.addData(it)
            }
            dataHasMore.observe(this@BaseCommonListFragment) {
                mViewBind.refreshLayout.setNoMoreData(!it)
            }
//            listCount.observe(this@BaseCommonListFragment) {
//                (activity as? BaseCommonListActivity<*, *, *, *>)?.updateCurrentTabView(
//                    currentFragmentInViewPagerIndex,
//                    it
//                )
//            }
        }
    }
}