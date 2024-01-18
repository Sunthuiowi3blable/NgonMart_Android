package me.amitshekhar.mvvm.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import me.amitshekhar.mvvm.data.repository.ProductRepository
import me.amitshekhar.mvvm.data.repository.TopHeadlineRepository
import me.amitshekhar.mvvm.di.ActivityContext
import me.amitshekhar.mvvm.ui.base.ViewModelProviderFactory
import me.amitshekhar.mvvm.ui.topheadline.ProductAdapter
import me.amitshekhar.mvvm.ui.topheadline.ProductViewModel
import me.amitshekhar.mvvm.ui.topheadline.TopHeadlineAdapter

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

//    @Provides
//    fun provideTopHeadlineViewModel(topHeadlineRepository: TopHeadlineRepository): TopHeadlineViewModel {
//        return ViewModelProvider(activity,
//            ViewModelProviderFactory(TopHeadlineViewModel::class) {
//                TopHeadlineViewModel(topHeadlineRepository)
//            })[TopHeadlineViewModel::class.java]
//    }

    @Provides
    fun provideProductViewModel(productRepository: ProductRepository): ProductViewModel{
        return ViewModelProvider(activity,
            ViewModelProviderFactory(ProductViewModel::class){
                ProductViewModel(productRepository)
            })[ProductViewModel::class.java]
    }

//    @Provides
//    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun provideProductAdapter() = ProductAdapter(ArrayList())

}