package me.amitshekhar.mvvm.ui.topheadline

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import me.amitshekhar.mvvm.MVVMApplication
import me.amitshekhar.mvvm.data.model.Product
import me.amitshekhar.mvvm.databinding.ActivityTopHeadlineBinding
import me.amitshekhar.mvvm.di.component.DaggerActivityComponent
import me.amitshekhar.mvvm.di.module.ActivityModule
import me.amitshekhar.mvvm.ui.base.UiState
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity() {
    @Inject
    lateinit var productViewModel: ProductViewModel

//    @Inject
//    lateinit var topHeadlineViewModel: TopHeadlineViewModel

//    @Inject
//    lateinit var adapter: TopHeadlineAdapter

    @Inject
    lateinit var adapter2: ProductAdapter

    private lateinit var binding: ActivityTopHeadlineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter2
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                productViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            Log.e("namnamnam", it.data.toString() )
                            binding.progressBar.visibility = View.GONE
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                        is UiState.Loading -> {

                            Log.e("namnamnam  123", "it.data.toString()" )
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }
                        is UiState.Error -> {

                            Log.e("namnamnam  56654", it.message )
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@TopHeadlineActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

//    private fun setupObserver() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                topHeadlineViewModel.uiState.collect {
//                    when (it) {
//                        is UiState.Success -> {
//                            binding.progressBar.visibility = View.GONE
//                            renderList(it.data)
//                            binding.recyclerView.visibility = View.VISIBLE
//                        }
//                        is UiState.Loading -> {
//                            binding.progressBar.visibility = View.VISIBLE
//                            binding.recyclerView.visibility = View.GONE
//                        }
//                        is UiState.Error -> {
//                            //Handle Error
//                            binding.progressBar.visibility = View.GONE
//                            Toast.makeText(this@TopHeadlineActivity, it.message, Toast.LENGTH_LONG)
//                                .show()
//                        }
//                    }
//                }
//            }
//        }
//    }

    private fun renderList(productList: List<Product>) {
        adapter2.addData(productList)
        adapter2.notifyDataSetChanged()
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as MVVMApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }

}
