package com.example.demoemployees.ui.departments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.demoemployees.data.Department
import com.example.demoemployees.databinding.ItemDepartmentBinding

class DepartmentAdapter (

): ListAdapter<Department, DepartmentAdapter.DepartmentViewHolder>(DepartmentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {
        val binding =
            ItemDepartmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DepartmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        val department = getItem(position)
        holder.bind(department)
    }

    inner class DepartmentViewHolder(private val binding: ItemDepartmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(department: Department) {
            binding.textViewTitle.text = department.name
            binding.textViewSubtitle1.text = department.city
        }
    }

    class DepartmentDiffCallback : DiffUtil.ItemCallback<Department>() {

        override fun areItemsTheSame(oldItem: Department, newItem: Department): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Department, newItem: Department): Boolean {
            return (oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.city == newItem.city)
            // return oldItem == newItem
        }

    }
}