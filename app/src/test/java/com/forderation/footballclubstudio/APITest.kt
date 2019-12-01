package com.forderation.footballclubstudio

import com.forderation.footballclubstudio.activity.presenter.EventPresenter
import com.forderation.footballclubstudio.activity.view.ListEventView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.event.Event
import com.forderation.footballclubstudio.model.event.GetEvents
import com.forderation.footballclubstudio.model.league.GetLeagues
import com.forderation.footballclubstudio.utils.TestContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class APITest {

    @Mock
    private lateinit var listEventView: ListEventView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiClient: ApiClient

    @Mock
    private lateinit var endpoint: Endpoints

    @Mock
    private lateinit var responseApi: Deferred<String>

    @Mock
    private lateinit var eventPresenter: EventPresenter

    private lateinit var listEvent: MutableList<Event>
    private lateinit var responseEvent: GetEvents
    private val idLeague = "4481"
    private val nameClub = "Arsenal"
    private val idEvent = "602249"
    @Before
    fun prepareUp() {
        MockitoAnnotations.initMocks(this)
        eventPresenter = EventPresenter(listEventView, gson, apiClient, TestContextProvider())
        listEvent = arrayListOf()
        responseEvent = GetEvents(listEvent)
    }

    @Test
    fun testGetSearchEvent(){
        runBlocking {
            Mockito.`when`(apiClient.doRequestAsync(endpoint.getSearchEvent(nameClub)))
                .thenReturn(responseApi)
            Mockito.`when`(responseApi.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson("",GetEvents::class.java)
            ).thenReturn(responseEvent)
        }
    }

    @Test
    fun testGetDetailEvent(){
        runBlocking {
            Mockito.`when`(apiClient.doRequestAsync(endpoint.getDetailEvent(idEvent)))
                .thenReturn(responseApi)
            Mockito.`when`(responseApi.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson("",GetEvents::class.java)
            ).thenReturn(responseEvent)
        }
    }

    @Test
    fun testGetNextMatch() {
        val resString = ""
        runBlocking {
            Mockito.`when`(apiClient.doRequestAsync(endpoint.getNextEvent(idLeague)))
                .thenReturn(responseApi)
            Mockito.`when`(responseApi.await()).thenReturn(resString)
            Mockito.`when`(
                gson.fromJson(resString, GetEvents::class.java)
            ).thenReturn(responseEvent)
        }
    }

    @Test
    fun testGetDetailLeague(){
        val responseLeague = GetLeagues(arrayListOf())
        val resString = ""
        runBlocking{
            Mockito.`when`(apiClient.doRequestAsync(endpoint.getDetailLeague(idLeague)))
                .thenReturn(responseApi)
            Mockito.`when`(responseApi.await()).thenReturn(resString)
            Mockito.`when`(gson.fromJson(resString,GetLeagues::class.java))
                .thenReturn(responseLeague)
        }
    }

    @Test
    fun testGetLatestMatch() {
        runBlocking {
            Mockito.`when`(apiClient.doRequestAsync(endpoint.getLatestEvent(idLeague)))
                .thenReturn(responseApi)
            Mockito.`when`(responseApi.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", GetEvents::class.java))
                .thenReturn(responseEvent)
        }
    }

    @Test
    fun testGetListFav() {
        eventPresenter.getListEventFav()
        Mockito.verify(listEventView).inflateEventFav()
    }

}