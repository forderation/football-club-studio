package com.forderation.footballclubstudio.api

import com.forderation.footballclubstudio.BuildConfig
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.URL

class ApiClient{
   fun doRequest(url:String):Deferred<String> = GlobalScope.async {
       URL(BuildConfig.BASE_API.plus(url)).readText()
   }
}