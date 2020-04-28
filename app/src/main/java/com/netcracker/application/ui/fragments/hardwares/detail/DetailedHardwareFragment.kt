package com.netcracker.application.ui.fragments.hardwares.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife

import com.netcracker.application.R
import com.netcracker.application.data.network.HardwareRepository
import com.netcracker.application.data.network.HardwareRepositoryProvider
import kotlinx.android.synthetic.main.detailed_hardware_fragment.*

class DetailedHardwareFragment : Fragment() {

    private lateinit var viewModel: DetailedHardwareViewModel
    private lateinit var hardwareRepository : HardwareRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailedHardwareViewModel::class.java)
        hardwareRepository = HardwareRepositoryProvider
            .providehardwarerepository()

        textView_name.text = hardwareRepository.getHardware("20").toString()

    }


}
