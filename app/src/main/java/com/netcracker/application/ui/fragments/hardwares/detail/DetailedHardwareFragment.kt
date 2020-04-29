package com.netcracker.application.ui.fragments.hardwares.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.netcracker.application.R
import com.netcracker.application.data.network.HardwareRepository
import com.netcracker.application.data.network.HardwareRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.detailed_hardware_fragment.*

class DetailedHardwareFragment : Fragment() {

    private lateinit var hardwareRepository : HardwareRepository
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detailed_hardware_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hardwareRepository = HardwareRepositoryProvider
            .providehardwarerepository()
        val idField ="Id: "
        val statusField ="Status: "
        val serialField ="Serial: "

        compositeDisposable.add(
            hardwareRepository.getHardware(DetailedHardwareFragmentArgs.fromBundle(requireArguments()).detailedHardwareId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    text_view_name.text = it.name
                    text_view_id.text = idField + it.id.toString()
                    text_view_serial.text = serialField + it.serial
                    text_view_status.text = statusField + it.hardwareStatus.name
                }
        )

    }


}
