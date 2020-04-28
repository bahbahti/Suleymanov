package com.netcracker.application.ui.fragments.hardwares.list

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife

import com.netcracker.application.R
import com.netcracker.application.data.entity.Hardware
import com.netcracker.application.data.network.*
import com.netcracker.application.ui.adapters.HardwareAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.list_hardwares_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListHardwaresFragment : Fragment() {

    companion object {
        fun newInstance() =
            ListHardwaresFragment()
    }

    @BindView(R.id.recyclerView)
    lateinit var listView: RecyclerView

    private lateinit var viewModel: ListHardwaresViewModel
    private lateinit var hardwareRepository : HardwareRepository

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
                    listView.adapter = HardwareAdapter(hardwareList)
                    d(tag,hardwareList.toString())
                }
        )

    }

}
