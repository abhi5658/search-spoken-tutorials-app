package com.example.abhishek.firstkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_search.*
import org.json.JSONArray
import org.json.JSONObject

class SearchActivity : AppCompatActivity() {

    var basicVideosHashMap = HashMap<Int, String>()
    var intermediateVideosHashMap = HashMap<Int, String>()
    var advancedVideosHashMap = HashMap<Int, String>()

    var finalResultsArrayList = ArrayList<VideoItem>()
    private lateinit var videoAdapter: VideoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

//        Toast.makeText(applicationContext,"hey",Toast.LENGTH_SHORT).show()

        videos_recycler_view.layoutManager = LinearLayoutManager(this)
        videoAdapter = VideoAdapter(applicationContext, finalResultsArrayList)
        videos_recycler_view.adapter = videoAdapter


        var foss = intent.getStringExtra("foss")
        var language = intent.getStringExtra("language")

        selectedFoss.text = "FOSS : " + foss
        selectedLanguage.text = "Language : " + language

//        var resultsArrayList = emptyList<>()


        val queue = Volley.newRequestQueue(this)
        val url = "https://aqms1.000webhostapp.com/get_json_data.php"

        val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
            println("Response is : ${response}")
            categorizeAndGroupVideos(response)
            Toast.makeText(applicationContext, response, Toast.LENGTH_LONG).show()
        }, Response.ErrorListener { Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_SHORT).show() })

        Toast.makeText(applicationContext, "Contacting Server...", Toast.LENGTH_SHORT).show()
        queue.add(stringRequest)

//        Toast.makeText(applicationContext, "this here...", Toast.LENGTH_SHORT).show()
    }

    private fun categorizeAndGroupVideos(jsonResponse: String) {
        var videosDataArray = JSONArray(jsonResponse)
        var i = 0
        var size = videosDataArray.length()
        var singleVideoJsonObject: JSONObject
        while (i < size) {
            singleVideoJsonObject = videosDataArray.getJSONObject(i)
            var tutorialLevel = singleVideoJsonObject.getString("tutorial_level")
            if (tutorialLevel.equals("Basic", true)) {
                basicVideosHashMap.put(
                        singleVideoJsonObject.getString("tutorial_order").toInt(),
                        singleVideoJsonObject.getString("video_id"))
            } else if (tutorialLevel.equals("Intermediate", true)) {
                intermediateVideosHashMap.put(
                        singleVideoJsonObject.getString("tutorial_order").toInt(),
                        singleVideoJsonObject.getString("video_id"))
            } else if (tutorialLevel.equals("Advanced", true)) {
                advancedVideosHashMap.put(
                        singleVideoJsonObject.getString("tutorial_order").toInt(),
                        singleVideoJsonObject.getString("video_id"))
            }
            i++
        }


        var search_sorted_data = ""

        println("BAsic videos : ")
        var oneVideoItem: VideoItem


        for (m in basicVideosHashMap.toSortedMap()) {
            println("${m.key}  : " + m.value)
            oneVideoItem = VideoItem(m.value, "Basic", m.key as Int)
            search_sorted_data += "Basic : \t" + m.key + "\t : \t${m.value}\n"
            finalResultsArrayList.add(oneVideoItem)
        }

        search_sorted_data += "\n"

        println("Intermediate videos : ")
        for (m in intermediateVideosHashMap.toSortedMap()) {
            println("${m.key}  : " + m.value)
            oneVideoItem = VideoItem(m.value, "Intermediate", m.key as Int)
            search_sorted_data += "Intermediate : \t" + m.key + "\t : \t${m.value}\n"
            finalResultsArrayList.add(oneVideoItem)
        }

        search_sorted_data += "\n"

        println("Advanced videos : ")
        for (m in advancedVideosHashMap.toSortedMap()) {
            println("${m.key}  : " + m.value)
            oneVideoItem = VideoItem(m.value, "Advanced", m.key as Int)
            search_sorted_data += "Advanced : \t" + m.key + "\t : \t${m.value}\n"
            finalResultsArrayList.add(oneVideoItem)
        }

//        sorted_data.text = search_sorted_data

        for (v in finalResultsArrayList) {
            println("${v.videoId} : \t${v.videoLevelForOrder} : \t${v.videoOrderInLevel}")
        }

        videoAdapter.notifyDataSetChanged()
    }
}
