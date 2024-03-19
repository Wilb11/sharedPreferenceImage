package com.example.personalexpensetrackerapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.personalexpensetrackeapp.Expense
import com.example.personalexpensetrackeapp.ExpenseDao
//import androidx.lifecycle.asLiveData

class ExpenseListViewModel(private val expenseDao: ExpenseDao) : ViewModel() {
    val allExpenses: LiveData<List<Expense>> = expenseDao.getAllExpenses()
}

class ExpenseListViewModelFactory(private val expenseDao: ExpenseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseListViewModel(expenseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
