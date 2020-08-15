package com.muhammadnaeem.kotlinsqlite_demo.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.muhammadnaeem.kotlinsqlite_demo.R
import com.muhammadnaeem.kotlinsqlite_demo.customadapter.Roles_CustomAdapter
import com.muhammadnaeem.kotlinsqlite_demo.databinding.FragmentEmployformBinding
import com.muhammadnaeem.kotlinsqlite_demo.databinding.RolesDialogBinding
import com.muhammadnaeem.kotlinsqlite_demo.model.EmployeeModel
import com.muhammadnaeem.kotlinsqlite_demo.model.RolesModel
import com.muhammadnaeem.kotlinsqlite_demo.viewmodel.CommonViewModel
import java.util.*


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
var index: Int=0;
    var binding: FragmentEmployformBinding?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_employform, container, false)
        binding!!.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this).get(CommonViewModel::class.java)
        viewModel!!.getemployeeById(arguments?.getInt("id")!!.toInt())!!.observe(requireActivity(), Observer {
                respose->
            //Bind  Employee Detail to XML
            binding!!.item=respose
            if (respose!=null) {
                 index = resources.getStringArray(R.array.empdesg).indexOf(respose.desg)
                binding!!.spDesg.setSelection(index)
            }

        })
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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


        binding!!.btnUpdate.setOnClickListener(View.OnClickListener {
            if (binding!!.edtFirstname.text.toString().isNullOrEmpty()){
                showtoast(getString(R.string.firstname))
            }
            else if (binding!!.edtFirstname.text.toString().length<3){
                showtoast(getString(R.string.error_firstname3))
            }
            else if (binding!!.edtLastname.text.toString().isNullOrEmpty()){
                showtoast(getString(R.string.error_enterlastname))
            }else if (binding!!.edtLastname.text.toString().length<3){
                showtoast(getString(R.string.error_last3))
            }
            else  if (binding!!.edtEmail.text.toString().isNullOrEmpty()){
                showtoast(getString(R.string.error_email))
            } else if (!Patterns.EMAIL_ADDRESS.matcher(binding!!.edtEmail.text.toString())
                    .matches()
            ) {
                showtoast(getString(R.string.error_emailvalid))

            }
            else  if (binding!!.edtPassword.text.toString().isNullOrEmpty()){
                showtoast(getString(R.string.error_pwd))
            }else  if (binding!!.edtPassword.text.toString().length<3){
                showtoast(getString(R.string.error_pwd3))
            }
            else  if (!binding!!.edtPassword.text.toString().equals(binding!!.edtConfirmpassword.text.toString())){
                showtoast(getString(R.string.error_confimpwd))
            }
            else  if (str_empdes.equals("--None Selected--")){
                showtoast(getString(R.string.error_selectdesg))
            }
            else if (mselectlist.size>0){
                var employeecode=binding!!.edtFirstname.text.toString().substring(0,1)+binding!!.edtLastname.text.toString()
                var mEmployeeModel= EmployeeModel()
                mEmployeeModel.firstname=binding!!.edtFirstname.text.toString()
                mEmployeeModel.lastnmae=binding!!.edtLastname.text.toString()
                mEmployeeModel.email=binding!!.edtEmail.text.toString()
                mEmployeeModel.password=binding!!.edtPassword.text.toString()
                mEmployeeModel.desg=str_empdes
                mEmployeeModel.emp_code=employeecode
                if (arguments?.getInt("id")==0){
                viewModel!!.insertemployee(mEmployeeModel,mselectlist)!!.observe(requireActivity(), Observer { respinse->
                    showtoast("Insert Succfully")
                    requireActivity().onBackPressed()
                })
                }
            }else{
                showtoast(getString(R.string.error_selectrole))
            }

        })

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

    fun  showtoast( str_msg:String){
        Toast.makeText(requireActivity(),str_msg, Toast.LENGTH_LONG).show()
    }
}