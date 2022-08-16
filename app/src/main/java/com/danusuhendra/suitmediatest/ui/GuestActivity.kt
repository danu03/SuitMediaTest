package com.danusuhendra.suitmediatest.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.danusuhendra.suitmediatest.adapter.GuestAdapter
import com.danusuhendra.suitmediatest.databinding.ActivityGuestBinding
import com.danusuhendra.suitmediatest.model.response.Data
import com.danusuhendra.suitmediatest.ui.viewmodel.GuestViewModel
import com.danusuhendra.suitmediatest.utils.NAME
import com.danusuhendra.suitmediatest.utils.NetworkStatus
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GuestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuestBinding
    private val viewModel: GuestViewModel by viewModels()
    private lateinit var adapter: GuestAdapter
    private lateinit var networkStatus: NetworkStatus

    private lateinit var layoutManager: GridLayoutManager
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        networkStatus = NetworkStatus()

        if (networkStatus.isNetworkConnected(this)) {
            viewModel.getGuest()
        } else {
            viewModel.getGuestFromDb()
        }

        layoutManager = GridLayoutManager(this, 2)
        setUpRecyclerView()
        adapter.setClickCallback(object : GuestAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Data) {
                val intent = intent
                intent.putExtra(NAME, data.firstName + " " + data.lastName)
                setResult(RESULT_OK, intent)
                finish()
            }
        })

        getGuest()


        viewModel.loading.observe(this) {
            isLoading = it
            binding.swipeToRefresh.isRefreshing = it
        }

        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        binding.swipeToRefresh.setOnRefreshListener {
            if (networkStatus.isNetworkConnected(this)) {
                viewModel.getGuest()
            } else {
                viewModel.getGuestFromDb()
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = GuestAdapter()
        binding.apply {
            rvGuest.layoutManager = layoutManager
            rvGuest.setHasFixedSize(true)
            rvGuest.adapter = adapter
        }
    }

    private fun getGuest() {
        viewModel.curatedPhoto.observe(this) {
            isLoading = false
            binding.swipeToRefresh.isRefreshing = false
            adapter.setList(it)
        }
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Guest"
    }

}