/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsProperty

// Create PhotoGridAdapter that extends the RecyclerView ListAdapter with DiffCallback
class PhotoGridAdapter(private val onClickListener: OnClickListener) : ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(MarsPropertyDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarsPropertyViewHolder {
        return MarsPropertyViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)

        // Call the onClick function from the onClickListener in a lambda from setOnClickListener
        holder.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }

        holder.bind(marsProperty)
    }

    class MarsPropertyViewHolder(private val binding: GridViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(marsProperty: MarsProperty) {
            binding.property = marsProperty
            binding.executePendingBindings()
        }
    }

    companion object MarsPropertyDiffCallback : DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            // Compare the IDs of the properties. Return true if the property IDs are the same, otherwise return false
            // Checks if both objects represent the same item
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            // Check if the oldItem equals the new item
            // Checks if content is the same
            return oldItem == newItem
        }
    }

    // Create an OnClickListener class with a lambda in its constructor that initializes a marsProperty
    class OnClickListener(val clickListener: (marsProperty: MarsProperty) -> Unit) {
        fun onClick(marsProperty: MarsProperty) = clickListener(marsProperty)
    }
}