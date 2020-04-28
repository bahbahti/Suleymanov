package com.netcracker.application.ui.fragments.hardwares.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.netcracker.application.R

class DetailedHardwareFragment : Fragment() {

    companion object {
        fun newInstance() =
            DetailedHardwareFragment()
    }

    private lateinit var viewModel: DetailedHardwareViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detailed_hardware_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailedHardwareViewModel::class.java)
        // TODO: Use the ViewModel
/*        val apiHardwareService = ApiHardwareService()

        GlobalScope.launch(Dispatchers.Main) {
            val hardwareResponse = apiHardwareService.gethardware("1").await()
            textView.text = hardwareResponse.hardwareStatus.toString()
        }*/
    }

}
