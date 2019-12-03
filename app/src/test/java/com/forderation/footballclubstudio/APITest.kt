package com.forderation.footballclubstudio

import androidx.lifecycle.MutableLiveData
import com.forderation.footballclubstudio.activity.presenter.DetailEventPresenter
import com.forderation.footballclubstudio.activity.presenter.EventPresenter
import com.forderation.footballclubstudio.activity.presenter.LeaguesPresenter
import com.forderation.footballclubstudio.activity.view.DetailEventView
import com.forderation.footballclubstudio.activity.view.LeaguesView
import com.forderation.footballclubstudio.activity.view.ListEventView
import com.forderation.footballclubstudio.api.ApiClient
import com.forderation.footballclubstudio.api.Endpoints
import com.forderation.footballclubstudio.model.event.Event
import com.forderation.footballclubstudio.model.event.GetEvents
import com.forderation.footballclubstudio.model.event.SearchEvent
import com.forderation.footballclubstudio.model.league.GetLeagues
import com.forderation.footballclubstudio.model.league.League
import com.forderation.footballclubstudio.utils.EventViewModel
import com.forderation.footballclubstudio.utils.TestContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class APITest {

    @Mock
    private lateinit var listEventView: ListEventView

    @Mock
    private lateinit var leagueView: LeaguesView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiClient: ApiClient

    @Mock
    private lateinit var onResponse: MutableLiveData<String>

    @Mock
    private lateinit var listEvent: MutableLiveData<List<Event>>

    @Mock
    private lateinit var onLoading: MutableLiveData<Boolean>

    @Mock
    private lateinit var responseApi: Deferred<String>

    @Mock
    private lateinit var eventPresenter: EventPresenter

    @Mock
    private lateinit var leaguesPresenter: LeaguesPresenter

    @Mock
    private lateinit var detailEventPresenter: DetailEventPresenter

    @Mock
    private lateinit var eventViewModel: EventViewModel

    @Mock
    private lateinit var detailEventView: DetailEventView

    @Before
    fun prepareUp() {
        MockitoAnnotations.initMocks(this)
        eventPresenter = EventPresenter(listEventView, gson, apiClient, TestContextProvider())
        leaguesPresenter = LeaguesPresenter(leagueView, gson, apiClient, TestContextProvider())
        detailEventPresenter = DetailEventPresenter(detailEventView, gson, apiClient, TestContextProvider())
        eventViewModel = EventViewModel(gson,apiClient,TestContextProvider(),listEvent,onResponse,onLoading)
    }

    @Test
    fun testGetSearchEvent(){
        val events: MutableList<Event> = mutableListOf()
        val response = SearchEvent(events)
        val eventName = "Arsenal"
        runBlocking {
            Mockito.`when`(apiClient.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(responseApi)
            Mockito.`when`(responseApi.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson("",SearchEvent::class.java)
            ).thenReturn(response)
            Mockito.`when`(onResponse.value).thenReturn("")
            Mockito.`when`(onLoading.value).thenReturn(true)
            Mockito.`when`(listEvent.value).thenReturn(response.events)
            eventViewModel.getData(eventName)
            Mockito.verify(onResponse).value = "Search success"
        }
    }

    @Test
    fun testGetDetailEvent(){
        val events: MutableList<Event> = mutableListOf()
        val response = GetEvents(events)
        val idEvent = "441613"
        runBlocking {
            Mockito.`when`(apiClient.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(responseApi)
            Mockito.`when`(responseApi.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson("",GetEvents::class.java)
            ).thenReturn(response)
            detailEventPresenter.getDetailEvent(idEvent)
            Mockito.verify(detailEventView).showDetailEvent(response.events!!)
        }
    }

    @Test
    fun testGetNextMatch() {
        val events: MutableList<Event> = mutableListOf()
        val response = GetEvents(events)
        val idLeague = "4481"
        runBlocking {
            Mockito.`when`(apiClient.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(responseApi)
            Mockito.`when`(responseApi.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson("", GetEvents::class.java)
            ).thenReturn(response)
            eventPresenter.getNextEvenMatch(idLeague)
            Mockito.verify(listEventView).inflateListEvent(response.events!!)
        }
    }

    @Test
    fun testGetDetailLeague(){
        val leagues: MutableList<League> = mutableListOf()
        val response = GetLeagues(leagues)
        val idLeague = "4481"
        runBlocking{
            Mockito.`when`(apiClient.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(responseApi)
            Mockito.`when`(responseApi.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("",GetLeagues::class.java))
                .thenReturn(response)
            leaguesPresenter.showLeague(idLeague)
            Mockito.verify(leagueView).addLeague(response.leagues!!)
        }
    }

    @Test
    fun testGetLatestMatch() {
        val events: MutableList<Event> = mutableListOf()
        val response = GetEvents(events)
        val idLeague = "4481"
        runBlocking {
            Mockito.`when`(apiClient.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(responseApi)
            Mockito.`when`(responseApi.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", GetEvents::class.java))
                .thenReturn(response)
            eventPresenter.getLatestEvenMatch(idLeague)
            Mockito.verify(listEventView).inflateListEvent(response.events!!)
        }
    }

    @Test
    fun testGetListFav() {
        eventPresenter.getListEventFav()
        Mockito.verify(listEventView).inflateEventFav()
    }

}