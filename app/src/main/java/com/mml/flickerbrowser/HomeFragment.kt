package com.mml.flickerbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mml.flickerbrowser.databinding.FragmentHomeBinding
import kotlinx.coroutines.*


class HomeFragment : Fragment(R.layout.fragment_home),  RecyclerItemClickListener.OnRecyclerClickListener {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private lateinit var binding:FragmentHomeBinding

    private var flickerAdapter: FlickerAdapter ?=null

    private lateinit var result: String

    private lateinit var jsonData: ArrayList<Photo>

    private lateinit var jop1:Job
    private lateinit var job2: Job

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)



        val url = createURI(
            "https://www.flickr.com/services/feeds/photos_public.gne",
            "android, oreo",
            "en-us",
            true
        )

        jop1 = GlobalScope.launch(Dispatchers.IO) {

            result = async { getRawData(url) }.await()

            jsonData = async { getFlickerJsonData(result) }.await()

            withContext(Dispatchers.Main) {
                flickerAdapter = FlickerAdapter(jsonData)

                binding.apply {
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = flickerAdapter

                    recyclerView.addOnItemTouchListener(
                        RecyclerItemClickListener(
                            context,
                            binding.recyclerView,
                            this@HomeFragment
                        )
                    )
                }

            }

        }

        return binding.root
    }

    override fun onDestroy() {
        jop1.cancel()
        job2.cancel()
        super.onDestroy()

    }

    override fun onResume() {
        Log.d(TAG, "onResume: starts")


        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val queryResult = sharedPreferences.getString(BaseActivity.FLICKER_QUERY, "")

        if (queryResult != null && queryResult.isNotEmpty()) {
            val url = createURI(
                "https://api.flickr.com/services/feeds/photos_public.gne",
                queryResult,
                "en-us",
                true
            )

           job2 = GlobalScope.launch(Dispatchers.IO) {
                result = async { getRawData(url) }.await()
                jsonData = async { getFlickerJsonData(result) }.await()
                withContext(Dispatchers.Main){
                    if(flickerAdapter == null) {
                        flickerAdapter = FlickerAdapter(jsonData)
                    }else {
                        flickerAdapter!!.loadNewData(jsonData)
                    }
                }
                Log.d(TAG,result)
            }
        }
        super.onResume()
    }

    private fun createURI(
        baseURL: String, searchCriteria: String, lang: String, matchAll: Boolean
    ): String {

        Log.d(TAG, "createURI: starts")
        return Uri.parse(baseURL)
            .buildUpon()
            .appendQueryParameter("tags", searchCriteria)
            .appendQueryParameter("tagmode", if (matchAll) "ALL" else "ANY")
            .appendQueryParameter("lang", lang)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .build().toString()
    }



    override fun onItemClick(view: View, position: Int) {
        Log.d(TAG, ".onItemClick: starts")
        Toast.makeText(requireContext(), "Normal tap at position $position", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG, ".onItemLongClick: starts")
//        Toast.makeText(this, "Long tap at position $position", Toast.LENGTH_SHORT).show()
        val photo = flickerAdapter?.getPhoto(position)
        if (photo != null) {
            val action = HomeFragmentDirections.actionHomeFragmentToPhotoDetailsFragment(photo)
            findNavController().navigate(action)
        }
    }

}