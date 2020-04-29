package com.netcracker.application.ui.fragments.hardwares.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.netcracker.application.R
import com.netcracker.application.data.entity.Hardware
import com.netcracker.application.data.network.HardwareRepository
import com.netcracker.application.data.network.HardwareRepositoryProvider
import com.netcracker.application.listener.ChooseDetailListener
import com.netcracker.application.ui.adapters.HardwareListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class ListHardwaresFragment : Fragment(), ChooseDetailListener {

    @BindView(R.id.recyclerView)
    lateinit var listView: RecyclerView

    private lateinit var viewModel: ListHardwaresViewModel
    private lateinit var hardwareRepository : HardwareRepository
    private lateinit var listAdapter : HardwareListAdapter

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val hardwareList: MutableList<Hardware> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.list_hardwares_fragment, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListHardwaresViewModel::class.java)
        hardwareRepository = HardwareRepositoryProvider
            .providehardwarerepository()
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        listView.layoutManager = llm

        compositeDisposable.add(
            hardwareRepository.getHardwareList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    hardwareList.addAll(it.content)
                    listAdapter = HardwareListAdapter(hardwareList)
                    listAdapter.addListener(this)
                    listView.adapter = listAdapter
                }
        )

    }

    override fun showDetailedHardware(position: Int) {
        findNavController().navigate(ListHardwaresFragmentDirections.actionDetail()
            .setDetailedHardwareId(listAdapter[position].id.toString())
        )
    }

}
