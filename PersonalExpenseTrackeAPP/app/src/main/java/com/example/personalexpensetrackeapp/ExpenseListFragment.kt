package com.example.personalexpensetrackerapp
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.personalexpensetrackerapp.databinding.FragmentExpenseListBinding

class ExpenseListFragment : Fragment() {
//
//    private var _binding: FragmentExpenseListBinding? = null
//    private val binding get() = _binding!!
//    private val expenseListViewModel: ExpenseListViewModel by viewModels {
//        ExpenseListViewModelFactory((activity?.application as ExpenseApplication).database.expenseDao())
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        _binding = FragmentExpenseListBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val adapter = ExpenseListAdapter()
//        binding.recyclerview.layoutManager = LinearLayoutManager(this.context)
//        binding.recyclerview.adapter = adapter
//
//        expenseListViewModel.allExpenses.observe(viewLifecycleOwner, Observer { expenses ->
//            expenses?.let {
//                adapter.submitList(it)
//            }
//        })
//    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}
