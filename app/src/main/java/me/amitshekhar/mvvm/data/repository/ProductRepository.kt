package me.amitshekhar.mvvm.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.amitshekhar.mvvm.data.api.NetworkService
import me.amitshekhar.mvvm.data.model.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private val networkService: NetworkService) {

    fun getProducts(): Flow<List<Product>>{
        return flow {
            val productsResponse = networkService.getProducts()
            emit(productsResponse)
        }
    }

}