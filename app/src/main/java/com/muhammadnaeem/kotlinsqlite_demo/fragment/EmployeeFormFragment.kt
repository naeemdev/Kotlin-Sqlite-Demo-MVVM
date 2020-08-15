package com.muhammadnaeem.kotlinsqlite_demo.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.muhammadnaeem.kotlinsqlite_demo.R
import com.muhammadnaeem.kotlinsqlite_demo.customadapter.Roles_CustomAdapter
import com.muhammadnaeem.kotlinsqlite_demo.databinding.FragmentEmployformBinding
import com.muhammadnaeem.kotlinsqlite_demo.databinding.RolesDialogBinding
import com.muhammadnaeem.kotlinsqlite_demo.model.RolesModel
import com.muhammadnaeem.kotlinsqlite_demo.viewmodel.CommonViewModel
import java.util.ArrayList


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EmployeeFormFragment : Fragment() {

    var mselectlist = ArrayList<RolesModel>()
    private val roles: Array<RolesModel>
        get() =
            arrayOf(
                RolesModel("ADMIN"),
                RolesModel("ACCT_PAY"),
                RolesModel("ACCT_RCV"),
                RolesModel("EMP_BENEFITS"),
                RolesModel("GEN_LEDGER"),
                RolesModel("PAYROLL"),
                RolesModel("INVERTORY"),
                RolesModel("PRODUCATION"),
                RolesModel("QURALITY_CTL"),
                RolesModel("SALES"),
                RolesModel("ORDERS"),
                RolesModel("CUSTOMERS"),
                RolesModel("SHIPPING"),
                RolesModel("RETURNS")
            )


    var viewModel: CommonViewModel?=null

    var str_empdes=""

    var binding: FragmentEmployformBinding?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_employform, container, false)
        binding!!.lifecycleOwner = viewLifecycleOwner
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CommonViewModel::class.java)
        viewModel!!.getemployeeById(arguments?.getInt("id")!!.toInt())!!.observe(requireActivity(), Observer {
                respose->
            //Bind  Employee Detail to XML
            binding!!.item=respose

        })

        if (arguments?.getInt("id")==0){
            binding!!.btnUpdate.setText(getString(R.string.Insert))


        }else{
            binding!!.btnUpdate.setText(getString(R.string.update))
            viewModel!!.getAll_employeeroles(arguments?.getInt("id")!!.toInt())!!.observe(requireActivity(), Observer
            { response->
                mselectlist= response as ArrayList<RolesModel>
            })

        }

        binding!!.spDesg.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                str_empdes= parent?.getItemAtPosition(p2).toString()
              }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        ///Cancel Button Click
        binding!!.btnCancel.setOnClickListener(View.OnClickListener {
            requireActivity().onBackPressed()
        })

        ///roles dialog click
        binding!!.btnRoles.setOnClickListener(View.OnClickListener {
            rolesdialog()
        })

    }



    fun rolesdialog() {

        val binding_dilaog: RolesDialogBinding=   DataBindingUtil.inflate(    LayoutInflater.from(activity),
            R.layout.roles_dialog,
            null, false)

        var mRoles_CustomAdapter= Roles_CustomAdapter()

        var dialog = Dialog(requireActivity())
        dialog.setContentView(binding_dilaog.root)
        dialog.setCancelable(false)
        mRoles_CustomAdapter.setitemList(roles,mselectlist!!)
        dialog.show()
        binding_dilaog.rolesRecyclerView.apply {
            adapter=mRoles_CustomAdapter
        }
        binding_dilaog.btnCancel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        binding_dilaog.btnOk.setOnClickListener(View.OnClickListener {

            if ( mRoles_CustomAdapter.checkedroles.size > 0){

                mselectlist= mRoles_CustomAdapter.checkedroles
            }

            dialog.dismiss()
        })


    }

}