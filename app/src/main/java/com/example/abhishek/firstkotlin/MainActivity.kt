package com.example.abhishek.firstkotlin

import android.app.PendingIntent.getActivity
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import org.json.JSONException
import android.app.ProgressDialog
import android.content.Intent
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException


class MainActivity : AppCompatActivity() {

//    var fossOptionsOnline = arrayListOf<String>()
//    var fossLanguages = HashMap<String,ArrayList<String>>()


//    inner class Loaders : AsyncTask<Void, Void, String>() {
//
//        override fun doInBackground(vararg p0: Void?): String? {
//            try{
//                var url = URL("http://aqms1.000webhostapp.com/data.php")
//                var httpURLConnection = url.openConnection()
//                var inputStream = httpURLConnection.getInputStream()
//                var bufferReader = BufferedReader(InputStreamReader(inputStream, "iso-8859-1"))
//                var line : String = ""
//                var result = ""
//                var lines = bufferReader.readLines()
//                while (line in lines){
//                    println("this line : "+line)
//                    result += line
//                }
//                return result
//            }catch (e : Exception){
//                e.printStackTrace()
//            }
//            return null
//        }
//
//        override fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
//            println("result : " + result)
//            if (result != null){
//            var jsonObject = JSONObject(result)
//            var version = jsonObject.get("version")
//            var jsonArray = jsonObject.getJSONArray("data")
//            var i: Int = 0
//            while (i < jsonArray.length()) {
//
//                var jo = jsonArray.getJSONObject(i)
//                var foss = jo.get("foss")
//
//                fossOptionsOnline.add(foss as String)
//
//                var languages = jo.getJSONArray("lang")
//                var currentFossLanguage = arrayListOf<String>()
//
//                var j: Int = 0
//
//                while (j < languages.length()) {
//                    currentFossLanguage.add(languages.getInt(j) as String)
//                    j++
//                }
//                fossLanguages.put(foss as String, currentFossLanguage)
//
//                Toast.makeText(applicationContext, (foss + fossLanguages.get(foss).toString()), Toast.LENGTH_SHORT).show()
//
//                i++
//
//            }
//        }
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Loaders().execute()


        var languageOptions = arrayListOf("English", "Hindi", "Gujarati", "Marathi", "Tamil")
        var fossOptions = arrayListOf("Java", "C++", "Python", "MySQL")

        var fossAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fossOptions)
        fossSpinner.adapter = fossAdapter
        var languageAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, languageOptions)
        languageSpinner.adapter = languageAdapter

        var selectedFoss = "None"
        var selectedLanguage = "None"

        fossSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
//                selected_output.text = "None selected"
                selectedFoss = "None"
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

//                selected_output.text = fossOptions.get(p2)
                selectedFoss = fossOptions.get(p2)

                languageOptions.clear()

                if (fossOptions.get(p2) == "Java") {
                    languageOptions.add("English")
                    languageOptions.add("Hindi")
                    languageOptions.add("Gujarati")
                } else if (fossOptions.get(p2).equals("C++", true)) {
                    languageOptions.add("English")
                    languageOptions.add("Hindi")
                    languageOptions.add("Marathi")
                    languageOptions.add("Tamil")
                } else if (fossOptions.get(p2).equals("python", true)) {
                    languageOptions.add("Hindi")
                    languageOptions.add("Marathi")
                    languageOptions.add("Tamil")
                    languageOptions.add("Telugu")
                } else if (fossOptions.get(p2).equals("mysql", true)) {
                    languageOptions.add("Hindi")
                    languageOptions.add("English")
                    languageOptions.add("Tamil")
                    languageOptions.add("Nepali")
                }

                languageOptions.sort()
                languageAdapter.notifyDataSetChanged()
                languageSpinner.setSelection(0)

                selected_output.text = selectedFoss + " : " + selectedLanguage
            }

        }
        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                selectedLanguage = "None"
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedLanguage = languageOptions.get(p2)
                selected_output.text = selectedFoss + " : " + selectedLanguage
            }

        }

        searchButton.setOnClickListener {
            var searchIntent = Intent(this, SearchActivity::class.java).apply {
                putExtra("foss", selectedFoss.toLowerCase())
                putExtra("language", selectedLanguage.toLowerCase())
            }
            startActivity(searchIntent)
        }
    }
}
