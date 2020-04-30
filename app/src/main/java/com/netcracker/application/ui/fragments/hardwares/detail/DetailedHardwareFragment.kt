package com.netcracker.application.ui.fragments.hardwares.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hardwareRepository = HardwareRepositoryProvider
            .providehardwarerepository()

        prosessGetList()
        //prosessButtonClick()
        buildSpinner()

        hardware_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                prosessButtonClick(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


/*        val idField ="Id: "
        val statusField ="Status: "
        val serialField ="Serial: "
        var initStatusId : Int = 1
        var initHardwareId : Int = 1

        compositeDisposable.add(
            hardwareRepository.getHardware(DetailedHardwareFragmentArgs.fromBundle(requireArguments()).detailedHardwareId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    initHardwareId = it.id
                    initStatusId = it.hardwareStatus.id - 1
                    text_view_id.text = idField + initHardwareId.toString()
                    text_view_name.text = it.name
                    text_view_serial.text = serialField + it.serial
                    text_view_status.text = statusField + it.hardwareStatus.toString()
                    hardware_spinner.setSelection(initStatusId)

                }
        )*/

/*        button.setOnClickListener {
            val selectedStatusId = hardware_spinner.selectedItemPosition
            Log.d("STATUS-ID ", hardware_spinner.selectedItemPosition.toString())
            Log.d("StATUS-LOG",  hardware_spinner.selectedItem.toString())

            if (selectedStatusId == initStatusId) {
                Toast.makeText(context, "Please change status of hardware!", Toast.LENGTH_SHORT).show()
            }

            else {
                //TODO implement if(statuscode == OK)
                Log.d("HARDwARE_stATUS_LOG", selectedStatusId.toString())
                compositeDisposable.add(
                    hardwareRepository.changeHardwareStatus(initHardwareId, selectedStatusId - 1)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe ({}, {throwable -> Log.d("excepton-LOG", throwable.message)}
                    )
                )

                //hardwareRepository.changeHardwareStatus(initHardwareId, selectedStatusId)
                Toast.makeText(context, "Status has been changed successfully!", Toast.LENGTH_SHORT).show()
                initStatusId = selectedStatusId
                //TODO implement else (Throw exception)
            }

        }*/

    }

    private fun prosessGetList() {
        compositeDisposable.add(
            hardwareRepository.getHardware(DetailedHardwareFragmentArgs.fromBundle(requireArguments()).detailedHardwareId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    text_view_id.text = it.hardwareStatus.id.toString()
                    text_view_name.text = it.name
                    text_view_serial.text = it.serial
                    text_view_status.text = it.hardwareStatus.name
                    //hardware_spinner.setSelection(initStatusId)
                }
        )
    }

    private fun prosessButtonClick(selectedStatusId: Int) {
        hardwareRepository = HardwareRepositoryProvider
            .providehardwarerepository()

        Log.d("status id: ",  selectedStatusId.toString())
        button.setOnClickListener {
            val selectedStatus = hardware_spinner.selectedItem
            Log.d("status body: ", hardware_spinner.selectedItemPosition.toString())

            if (selectedStatus == text_view_status.text) {
                Toast.makeText(context, "Please change status of hardware!", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("hardware status: ", selectedStatusId.toString())
                compositeDisposable.add(
                    hardwareRepository.changeHardwareStatus(text_view_id.text.toString().toInt(), selectedStatusId + 1)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe ({
                            Toast.makeText(context, "Status has been changed successfully!", Toast.LENGTH_SHORT).show()
                            //initStatusId = selectedStatusId
                            text_view_status.text = selectedStatus.toString()
                        }, {
                                throwable -> Log.d("excepton: ", throwable.message)
                                Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
                        })
                )
            }

        }
    }

    private fun buildSpinner() {

        // Create an ArrayAdapter using the string array and a default spinner layout
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.status_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                hardware_spinner.adapter = adapter
            }
        }

    }



}
