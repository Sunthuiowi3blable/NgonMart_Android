package me.amitshekhar.mvvm.ui.topheadline

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.amitshekhar.mvvm.data.model.Article
import me.amitshekhar.mvvm.data.model.Product
import me.amitshekhar.mvvm.databinding.ProductItemBinding
import me.amitshekhar.mvvm.utils.AppConstant.URL

class ProductAdapter(
    private val productList: ArrayList<Product>
) : RecyclerView.Adapter<ProductAdapter.DataViewHolder>(){
    class DataViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(product: Product){
                binding.textViewTitle.text = product.tenSanPham
                binding.textViewDescription.text = product.thongTinChung
                Glide.with(binding.imageViewBanner.context)
                    .load(product.thietKe)
                    .into(binding.imageViewBanner)

                val formattedDonGia = String.format("%,d VND", product.donGia)
                binding.textViewSource.text = formattedDonGia

                binding.textViewSource.setOnClickListener {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(it.context, Uri.parse(URL))
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(productList[position])

    fun addData(list: List<Product>) {
        productList.addAll(list)
    }
}