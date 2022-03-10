package com.scope.application.screens.list.adapter

import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.scope.application.R
import com.scope.application.domain.models.Driver
import com.scope.application.screens.list.adapter.ListDriversAdapter.ItemViewHolder

@RequiresApi(Build.VERSION_CODES.M)
class ListDriversAdapter(val context: Context) :
    RecyclerView.Adapter<ItemViewHolder>() {

    var listOfDrivers: List<Driver> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemClicked: OnItemClickListener? =
        null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val driverHolderView = ItemDriverView(parent.context)
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        driverHolderView.layoutParams = lp
        return ItemViewHolder(driverHolderView, onItemClicked)
    }

    override fun getItemId(position: Int): Long {
        return position.hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return listOfDrivers.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val driver = listOfDrivers[position]
        driver.owner.let { holder.bind(driver) }

        if (position == itemCount - 1) holder.itemView.findViewById<MaterialCardView>(R.id.baseCardProductView)
            .setCardBackgroundColor(context.getColor(R.color.red))

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ItemViewHolder(
        private val driverHolderView: ItemDriverView,
        private val onItemClicked: OnItemClickListener?
    ) : RecyclerView.ViewHolder(driverHolderView) {
        fun bind(driver: Driver) {
            with(driverHolderView) {

                setOnClickListener { onItemClicked?.onDriverCardClicked(driver) }
                this.nameDriver = "${driver.owner.name} ${driver.owner.surname}"
                this.imageUrl = driver.owner.foto
                this.vehicles = driver.vehicles
            }
        }
    }
}
