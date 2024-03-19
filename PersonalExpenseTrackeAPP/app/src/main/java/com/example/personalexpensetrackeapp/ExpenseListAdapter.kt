package com.example.personalexpensetrackerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.personalexpensetrackeapp.Expense
import com.example.personalexpensetrackeapp.R
import java.text.SimpleDateFormat
import java.util.Locale

class ExpenseListAdapter : ListAdapter<Expense, ExpenseListAdapter.ExpenseViewHolder>(ExpensesComparator()) {

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val expenseDateView: TextView = itemView.findViewById(R.id.expense_date)
        private val expenseAmountView: TextView = itemView.findViewById(R.id.expense_amount)
        private val expenseCategoryView: TextView = itemView.findViewById(R.id.expense_category)

        fun bind(expense: Expense) {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            expenseDateView.text = sdf.format(expense.date)
            expenseAmountView.text = expense.amount.toString()
            expenseCategoryView.text = expense.category
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        return ExpenseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_expense, parent, false))
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ExpensesComparator : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id
        }
    }
}