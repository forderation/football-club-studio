package com.forderation.footballclubstudio

import com.forderation.footballclubstudio.activity.presenter.EventPresenter
import com.forderation.footballclubstudio.activity.view.ListEventView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.event.Event
import com.forderation.footballclubstudio.model.event.GetEvents
import com.forderation.footballclubstudio.utils.TestContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import okhttp3.Response
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.mock


class APITest {

    @Mock
    private lateinit var view: ListEventView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiClient: ApiClient

    @Mock
    private lateinit var responseApi: Deferred<String>

    private lateinit var presenter: EventPresenter
    private lateinit var listEvent: MutableList<Event>
    private lateinit var responseEvent: GetEvents
    private val idLeague = "4481"
    @Before
    fun prepareUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventPresenter(view, gson, apiClient, TestContextProvider())
        listEvent = arrayListOf()
        responseEvent = GetEvents(listEvent)
    }

    @Test
    fun testGetNextMatch() {
        runBlocking {
            Mockito.`when`(apiClient.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(responseApi)
            Mockito.`when`(responseApi.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson("", GetEvents::class.java)
            ).thenReturn(responseEvent)
            presenter.getNextEvenMatch(idLeague)
            Mockito.verify(view).inflateListEvent(responseEvent.events)
        }
    }

    @Test
    fun testGetLatestMatch() {
        runBlocking {
            Mockito.`when`(apiClient.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(responseApi)
            Mockito.`when`(responseApi.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson("", GetEvents::class.java)
            ).thenReturn(responseEvent)
            presenter.getLatestEvenMatch(idLeague)
            Mockito.verify(view).inflateListEvent(responseEvent.events)
        }
    }

    @Test
    fun testGetListFav() {
        presenter.getListEventFav()
        Mockito.verify(view).inflateEventFav()
    }

}