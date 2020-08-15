package com.muhammadnaeem.kotlinsqlite_demo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.muhammadnaeem.kotlinsqlite_demo.R
import com.muhammadnaeem.kotlinsqlite_demo.customadapter.Employee_CustomAdapter
import com.muhammadnaeem.kotlinsqlite_demo.databinding.FragmentEmployeelistBinding
import com.muhammadnaeem.kotlinsqlite_demo.databinding.FragmentEmployformBinding

import com.muhammadnaeem.kotlinsqlite_demo.interfaceListner.ItemClickListner
import com.muhammadnaeem.kotlinsqlite_demo.model.EmployeeModel
import com.muhammadnaeem.kotlinsqlite_demo.viewmodel.CommonViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EmployeeListFragment : Fragment(), ItemClickListner {

    var viewModel: CommonViewModel?=null
    var mepmloyeelist = ArrayList<EmployeeModel>()
    var mEmployee_CustomAdapter: Employee_CustomAdapter?=null


    var binding: FragmentEmployeelistBinding?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_employeelist, container, false)
        binding!!.lifecycleOwner = viewLifecycleOwner

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        binding!!.fab.setOnClickListener(View.OnClickListener {
            val bundle = bundleOf("id" to 0)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment,bundle)
        })

        viewModel = ViewModelProvider(this).get(CommonViewModel::class.java)
        mEmployee_CustomAdapter=Employee_CustomAdapter()

        // get Employee list and Set Adpater.
        viewModel!!.getAll_employee()!!.observe(requireActivity(), Observer { response->
            mepmloyeelist= response as ArrayList<EmployeeModel>
            mEmployee_CustomAdapter!!.setitemList(mepmloyeelist)
            binding!!.employeeRecyclerView.apply {
                adapter=mEmployee_CustomAdapter
            }
        })

    }

    /// implement RecycelView Item Click and Pass Employee id to Next Screen
    override fun onClick(emp_id: Int?, pos: Int) {
        val bundle = bundleOf("id" to emp_id)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment,bundle)
    }
}