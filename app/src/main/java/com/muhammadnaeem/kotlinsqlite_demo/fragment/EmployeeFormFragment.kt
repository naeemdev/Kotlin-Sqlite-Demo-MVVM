package com.muhammadnaeem.kotlinsqlite_demo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.muhammadnaeem.kotlinsqlite_demo.R
import com.muhammadnaeem.kotlinsqlite_demo.databinding.FragmentEmployformBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EmployeeFormFragment : Fragment() {

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


    }
}