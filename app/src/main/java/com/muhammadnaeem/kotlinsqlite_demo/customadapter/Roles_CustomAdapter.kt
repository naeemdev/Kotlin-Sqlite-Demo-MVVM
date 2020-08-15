package com.muhammadnaeem.kotlinsqlite_demo.customadapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.muhammadnaeem.kotlinsqlite_demo.R
import com.muhammadnaeem.kotlinsqlite_demo.databinding.ItemRolesBinding
import com.muhammadnaeem.kotlinsqlite_demo.model.RolesModel


class Roles_CustomAdapter ():
    RecyclerView.Adapter<Roles_CustomAdapter.RolesViewHolder>() {

    private var mRolesllist: Array<RolesModel>? = null

    var checkedroles = ArrayList<RolesModel>()
    var id:Int=0

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RolesViewHolder {
        val mItemRolesBinding: ItemRolesBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.item_roles, viewGroup, false
            )
        return RolesViewHolder(mItemRolesBinding)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(
        mRolesViewHolder: RolesViewHolder,
        i: Int
    ) {

        val currentitem = mRolesllist!![i]

        if (checkedroles.size>0){
            for(i in  0..(checkedroles.size - 1))
            {
                if (checkedroles.get(i).name==currentitem.name){
                    mRolesViewHolder.mItemIconBinding.checkbox.isChecked=true

                }


            }
        }
        mRolesViewHolder.mItemIconBinding.tvTitle.setText(currentitem.name)
        mRolesViewHolder.mItemIconBinding.checkbox.setOnClickListener(View.OnClickListener {
            val mRolesModel = mRolesllist!![i]
            if ( mRolesViewHolder.mItemIconBinding.checkbox.isChecked){
                mRolesViewHolder.mItemIconBinding.checkbox.isChecked=true

                checkedroles.add(mRolesModel)
            }else{
                mRolesViewHolder.mItemIconBinding.checkbox.isChecked=false
                checkedroles.remove(mRolesModel)
            }
        })


    }

    override fun getItemCount(): Int {
        return if (mRolesllist != null) {
            mRolesllist!!.size
        } else {
            0
        }
    }

    fun setitemList(local_list: Array<RolesModel>?,local_selectedlist: ArrayList<RolesModel>) {
        this.mRolesllist = local_list
        //  mSelectedRolesModellist=local_selectedlist
        //  checkedTeachers.clear()
        checkedroles=local_selectedlist
        notifyDataSetChanged()
    }

    fun setid(id:Int) {

        this.id=id
        notifyDataSetChanged()
    }


    inner class RolesViewHolder(var mItemIconBinding: ItemRolesBinding) :
        RecyclerView.ViewHolder(mItemIconBinding.root)


}