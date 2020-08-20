package com.anonlatte.trends.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anonlatte.trends.MainActivity
import com.anonlatte.trends.R
import com.anonlatte.trends.databinding.FragmentHomeBinding
import com.anonlatte.trends.db.model.Since
import com.anonlatte.trends.ui.adapters.RepositoriesAdapter
import com.anonlatte.trends.util.APP_SETTINGS
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var adapter: RepositoriesAdapter? = null
    private val sharedPreferences: SharedPreferences by lazy {
        requireContext().getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        restoreSinceType().also {
            changeTitle(it)
            if (it != null) {
                viewModel.sinceType.value = it
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        initializeAdapter()

        binding.viewModel = viewModel

        subscribeUI()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sinceDaily -> {
                with(Since.DAILY) {
                    changeTitle(this)
                    rememberSinceType(this)
                    viewModel.sinceType.value = this.value
                }
            }
            R.id.sinceWeekly -> {
                with(Since.WEEKLY) {
                    changeTitle(this)
                    rememberSinceType(this)
                    viewModel.sinceType.value = this.value
                }
            }
            R.id.sinceMonthly -> {
                with(Since.MONTHLY) {
                    changeTitle(this)
                    rememberSinceType(this)
                    viewModel.sinceType.value = this.value
                }
            }
        }
        viewModel.isLoading.value = true
        return super.onOptionsItemSelected(item)
    }

    private fun changeTitle(sinceType: Since?) {
        changeTitle(sinceType?.value)
    }

    private fun changeTitle(sinceType: String?) {
        if (sinceType != null) {
            (requireActivity() as MainActivity).supportActionBar?.title =
                getString(R.string.app_title, sinceType)
        }
    }

    private fun rememberSinceType(sinceType: Since) {
        sharedPreferences.edit()
            .putString("since-type", sinceType.value).apply()
    }

    private fun restoreSinceType(): String? {
        return sharedPreferences.getString("since-type", null)
    }

    private fun subscribeUI() {
        viewModel.reposMediatorLiveData.observe(viewLifecycleOwner, {
            adapter?.setRepositoriesList(it)
            viewModel.isLoading.value = false
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            binding.swipeRefreshLayout.isRefreshing = it
        })
    }

    private fun initializeAdapter() {
        adapter = RepositoriesAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        val divider = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.repositoriesList.adapter = adapter
        binding.repositoriesList.layoutManager = layoutManager
        binding.repositoriesList.addItemDecoration(divider)
    }
}