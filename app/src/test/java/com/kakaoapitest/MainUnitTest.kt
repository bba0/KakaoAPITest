package com.kakaoapitest


import com.kakaoapitest.data.model.SearchQuery
import com.kakaoapitest.data.source.document.DocumentRepository
import com.kakaoapitest.data.source.document.RemoteDocumentDataSource
import com.kakaoapitest.data.source.searchquery.SearchQueryDataSource
import com.kakaoapitest.data.source.searchquery.SearchQueryRepository
import com.kakaoapitest.network.Api
import com.kakaoapitest.ui.main.MainContract
import com.kakaoapitest.ui.main.MainPresenter
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.InOrder
import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations


class MainUnitTest {

    @Mock
    lateinit var mMainView: MainContract.View
    @Mock
    lateinit var mSearchQueryDataSource: SearchQueryDataSource

    private lateinit var mMainPresenter: MainPresenter
    private lateinit var mDocumentRepository: DocumentRepository
    private lateinit var mSearchQueryRepository: SearchQueryRepository
    private lateinit var mMainInOrder: InOrder
    private lateinit var mSearchQueryDataSourceInorder: InOrder

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUpRxSchedulers() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
        }
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mDocumentRepository = DocumentRepository.getInstance(RemoteDocumentDataSource.getInstance(Api))
        mSearchQueryRepository = SearchQueryRepository.getInstance(mSearchQueryDataSource)
        mMainPresenter = MainPresenter(mMainView, mSearchQueryRepository, mDocumentRepository)

        //기본 찾기 테스트
        mMainPresenter.search("테스트", false)
        mMainInOrder = inOrder(mMainView)
        mSearchQueryDataSourceInorder = inOrder(mSearchQueryDataSource)
        Thread.sleep(1000)
        mMainInOrder.verify(mMainView, times(1)).setDocument(ArgumentMatchers.anyList())
    }


    @Test
    fun moreLoad() {
        mMainPresenter.moreLoad()
        Thread.sleep(1000)
        mMainInOrder.verify(mMainView, times(1)).addDocument(ArgumentMatchers.anyList())
    }

    @Test
    fun changeApiType() {
        mMainPresenter.changeApiType(MainPresenter.ApiType.All.name)
        Thread.sleep(1000)
        mMainInOrder.verify(mMainView, times(0)).setDocument(ArgumentMatchers.anyList())

        mMainPresenter.changeApiType(MainPresenter.ApiType.Cafe.name)
        Thread.sleep(1000)
        mMainInOrder.verify(mMainView, times(1)).setDocument(ArgumentMatchers.anyList())

        mMainPresenter.changeApiType(MainPresenter.ApiType.Blog.name)
        Thread.sleep(1000)
        mMainInOrder.verify(mMainView, times(1)).setDocument(ArgumentMatchers.anyList())
    }

    @Test
    fun changeSortType() {
        mMainPresenter.changeSortType(MainPresenter.SortType.DateTime.ordinal)
        Thread.sleep(1000)
        mMainInOrder.verify(mMainView, times(1)).setDocument(ArgumentMatchers.anyList())

        mMainPresenter.changeSortType(MainPresenter.SortType.Title.ordinal)
        Thread.sleep(1000)
        mMainInOrder.verify(mMainView, times(1)).setDocument(ArgumentMatchers.anyList())

        mMainPresenter.changeSortType(MainPresenter.SortType.Title.ordinal)
        Thread.sleep(1000)
        mMainInOrder.verify(mMainView, times(0)).setDocument(ArgumentMatchers.anyList())
    }

//    @Test
//    fun saveQuery() {
//        //kotlin not null 이슈로 테스트 방법 필요
//        mSearchQueryDataSourceInorder.verify(mSearchQueryDataSource, times(1)).addSearchQuery(ArgumentMatchers.any(SearchQuery::class.java))
//    }


}