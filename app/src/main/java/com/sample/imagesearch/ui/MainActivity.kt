package com.sample.imagesearch.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.ArrayMap
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.imagesearch.R
import com.sample.imagesearch.adapter.RecyclerViewAdapter
import com.sample.imagesearch.api.ApiInterface
import com.sample.imagesearch.model.ImageDataModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var txtSearch: AppCompatEditText
    private lateinit var rclImagesList: RecyclerView
    private lateinit var apiInterface: ApiInterface
    private lateinit var recyclerAdapter: RecyclerViewAdapter
    private lateinit var progressBar:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiInterface = ApiInterface.create()
        recyclerAdapter = RecyclerViewAdapter(this)
        txtSearch = findViewById(R.id.txtSearch)
        progressBar = findViewById(R.id.progress_bar)
        rclImagesList = findViewById(R.id.recycler_image_list)
        rclImagesList.layoutManager = GridLayoutManager(this, 2)
        rclImagesList.adapter = recyclerAdapter

    }

    fun searchImages(view: View) {
        val keyword = txtSearch.text.toString().trim()
        if(keyword.isEmpty() || keyword.length<3) {
            Toast.makeText(this@MainActivity, "Please enter minimum 3 characters.", Toast.LENGTH_LONG).show()
        } else {
            progressBar.visibility = View.VISIBLE
            var map: ArrayMap<String, String> = ArrayMap()
            map.put("format", "json")
            map.put("numRsults", "20")
            map.put("keywords", keyword)
            val call = apiInterface.getImageResults(map)
            dismissKeyBoard()
            call.enqueue(object : Callback<List<ImageDataModel>> {
                override fun onResponse(call: Call<List<ImageDataModel>>?, response: Response<List<ImageDataModel>>?) {
                    progressBar.visibility = View.GONE
                    if (response?.body() != null) {
                        recyclerAdapter.setImageListItems(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<List<ImageDataModel>>?, t: Throwable?) {
                    progressBar.visibility = View.GONE
                    AlertDialog.Builder(this@MainActivity)
                            .setTitle("Error")
                            .setMessage(t!!.message) // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, DialogInterface.OnClickListener {
                                dialog, which ->
                                // Continue with delete operation

                            }) // A null listener allows the button to dismiss the dialog and take no further action.

                            .show()
                }
            })
        }


    }

    private fun dismissKeyBoard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(txtSearch.getWindowToken(), 0)
    }
}