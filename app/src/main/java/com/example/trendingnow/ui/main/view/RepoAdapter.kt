package com.example.trendingnow.ui.main.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.compose.ui.text.toUpperCase
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.trendingnow.R
import com.example.trendingnow.data.source.model.Items
import kotlinx.android.synthetic.main.repo_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class RepoAdapter(
    private val mTrendingRepoList: ArrayList<Items>
) : RecyclerView.Adapter<RepoAdapter.DataViewHolder>() , Filterable{

    var mTempFilteredList = ArrayList<Items>()
    init {
        mTempFilteredList = mTrendingRepoList
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Items) {
            itemView.tx_repo_name.text = item.full_name
            itemView.tc_owner_name.text = item.owner.login
            itemView.tx_watches.text= "Watchers :".plus(item.watchers.toString())
            itemView.tx_forks.text= "Forks :".plus(item.forks.toString())
            itemView.tx_visibility.text= item.visibility.uppercase(Locale.getDefault())
            itemView.tx_open_issues.text= "Open Issues :".plus(item.open_issues.toString())
            if(item.selection_flag==1){
                itemView.tx_repo_name.setTextColor(ContextCompat.getColor(itemView.ll_main.context, R.color.white))
                itemView.tc_owner_name.setTextColor(ContextCompat.getColor(itemView.ll_main.context, R.color.white))
                itemView.tx_watches.setTextColor(ContextCompat.getColor(itemView.ll_main.context, R.color.white))
                itemView.tx_forks.setTextColor(ContextCompat.getColor(itemView.ll_main.context, R.color.white))
                itemView.tx_open_issues.setTextColor(ContextCompat.getColor(itemView.ll_main.context, R.color.white))
                itemView.tx_visibility.setTextColor(ContextCompat.getColor(itemView.ll_main.context, R.color.white))
                itemView.ll_main.setBackgroundColor(ContextCompat.getColor(itemView.ll_main.context, R.color.sky_blue))
            }else{
                itemView.tx_repo_name.setTextColor(ContextCompat.getColor(itemView.ll_main.context, R.color.sky_blue))
                itemView.tc_owner_name.setTextColor(ContextCompat.getColor(itemView.ll_main.context, R.color.sky_blue))
                itemView.tx_watches.setTextColor(ContextCompat.getColor(itemView.ll_main.context, R.color.sky_blue))
                itemView.tx_forks.setTextColor(ContextCompat.getColor(itemView.ll_main.context, R.color.sky_blue))
                itemView.tx_visibility.setTextColor(ContextCompat.getColor(itemView.ll_main.context, R.color.sky_blue))
                itemView.tx_open_issues.setTextColor(ContextCompat.getColor(itemView.ll_main.context, R.color.sky_blue))
                itemView.ll_main.setBackgroundColor(ContextCompat.getColor(itemView.ll_main.context, R.color.white))

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.repo_item, parent,
                false
            )
        )

    override fun getItemCount(): Int = mTempFilteredList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            if(mTempFilteredList[position].selection_flag==1){
                mTempFilteredList[position].selection_flag=0
            }else
                mTempFilteredList[position].selection_flag=1

            this.notifyItemChanged(position)
        }
        holder.bind(mTempFilteredList[position])

    }

    fun addData(list: List<Items>) {
        mTrendingRepoList.addAll(list)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                mTempFilteredList = if (charSearch.isEmpty()) {
                    mTrendingRepoList
                } else {
                    val resultList = ArrayList<Items>()
                    for (row in mTrendingRepoList) {
                        if (row.name.lowercase(Locale.getDefault()).contains(constraint.toString().lowercase(Locale.getDefault())
                            )
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = mTempFilteredList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mTempFilteredList = results?.values as ArrayList<Items>
                notifyDataSetChanged()
            }
        }
    }

}