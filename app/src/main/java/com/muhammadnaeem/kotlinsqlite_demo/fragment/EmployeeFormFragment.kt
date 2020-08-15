package com.muhammadnaeem.kotlinsqlite_demo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.muhammadnaeem.kotlinsqlite_demo.R
import com.muhammadnaeem.kotlinsqlite_demo.databinding.FragmentEmployformBinding
import com.muhammadnaeem.kotlinsqlite_demo.model.RolesModel
import com.muhammadnaeem.kotlinsqlite_demo.viewmodel.CommonViewModel


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EmployeeFormFragment : Fragment() {

    var mselectlist = java.util.ArrayList<RolesModel>()
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

    }
}