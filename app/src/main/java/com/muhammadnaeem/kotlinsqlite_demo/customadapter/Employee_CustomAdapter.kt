package com.muhammadnaeem.kotlinsqlite_demo.customadapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.muhammadnaeem.kotlinsqlite_demo.R
import com.muhammadnaeem.kotlinsqlite_demo.databinding.ItemEmployeeBinding
import com.muhammadnaeem.kotlinsqlite_demo.interfaceListner.ItemClickListner
import com.muhammadnaeem.kotlinsqlite_demo.model.EmployeeModel


class Employee_CustomAdapter ( var clickListener: ItemClickListner):
    RecyclerView.Adapter<Employee_CustomAdapter.EmployeeViewHolder>() {

    private var mEmployeeModellist: ArrayList<EmployeeModel>? = null

      override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): EmployeeViewHolder {
        val mItemIconBinding: ItemEmployeeBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.item_employee, viewGroup, false
            )
        return EmployeeViewHolder(mItemIconBinding)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(
        mEmployeeViewHolder: EmployeeViewHolder,
        i: Int
    ) {

        val currentitem = mEmployeeModellist!![i]

        mEmployeeViewHolder.mItemIconBinding.item=currentitem
        mEmployeeViewHolder.mItemIconBinding.cvRoot.setOnClickListener(View.OnClickListener {
            clickListener!!.onClick(mEmployeeModellist!![mEmployeeViewHolder.adapterPosition].id,
                mEmployeeViewHolder.adapterPosition)
        })
    }

    override fun getItemCount(): Int {
        return if (mEmployeeModellist != null) {
            mEmployeeModellist!!.size
        } else {
            0
        }
    }

    fun setitemList(local_list: ArrayList<EmployeeModel>?) {
        this.mEmployeeModellist = local_list

        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        mEmployeeModellist?.removeAt(position)
        notifyItemRemoved(position)
    }


    inner class EmployeeViewHolder(var mItemIconBinding: ItemEmployeeBinding) :
        RecyclerView.ViewHolder(mItemIconBinding.root)



}