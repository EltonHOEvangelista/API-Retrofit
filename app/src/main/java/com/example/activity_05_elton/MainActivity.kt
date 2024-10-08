
package com.example.activity_05_elton

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.activity_05_elton.api.DogApi
import com.example.activity_05_elton.api.DogApiService
import com.example.activity_05_elton.databinding.ActivityMainBinding
import com.example.activity_05_elton.model.Breed
import com.example.activity_05_elton.model.BreedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null
    private lateinit var breeds: List<Breed>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        //call function to fetch data from dog api
        fetchBreeds()

        //Load data on Spinner
        binding!!.dogSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedBreed = breeds[position]

                //set UI
                binding!!.txtDescription.setText(selectedBreed.attributes.description)
                binding!!.txtMin.setText(selectedBreed.attributes.life.min.toString())
                binding!!.txtMax.setText(selectedBreed.attributes.life.max.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun fetchBreeds() {

        //Instantiate Dog Api
        val dogApi = DogApi.retrofit.create(DogApiService::class.java)
        val apiCall = dogApi.getBreeds()

        //Asynchronous call to fetch data from Dog's Api
        apiCall.enqueue(object: Callback<BreedResponse> {
            override fun onResponse(
                call: Call<BreedResponse>,
                response: Response<BreedResponse>
            ) {
                if (response.isSuccessful) {
                    // Successfully fetched data
                    breeds = response.body()?.data!!

                    //display breed's data using Spinner
                    displayBreeds()
                } else {
                    Log.e("DogApi", "Failed to fetch data. Error: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<BreedResponse>, t: Throwable) {
                Toast.makeText(baseContext, "Failed to fetch data.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //function to display breed's data using Spinner
    private fun displayBreeds() {

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            breeds.map { it.attributes.name }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.dogSpinner?.adapter = adapter
    }
}