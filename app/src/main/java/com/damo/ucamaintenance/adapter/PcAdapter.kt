package com.damo.ucamaintenance.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.damo.ucamaintenance.MainActivity
import com.damo.ucamaintenance.R
import com.damo.ucamaintenance.databinding.ItemPcBinding
import com.damo.ucamaintenance.entities.PcEntity

class PcAdapter(private var pcs: MutableList<PcEntity>, private var listener: MainActivity) :
    RecyclerView.Adapter<PcAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPcBinding.bind(view)

        fun setListener(pcEntity: PcEntity) {
            with(binding.root) {
                setOnClickListener { listener.onClick(pcEntity) }

                setOnLongClickListener {
                    listener.onDeletePc(pcEntity)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.item_pc, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = pcs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pc = pcs[position]

        with(holder) {
            setListener(pc)
            binding.tvPcName.text = pc.name
            // Bind other PC properties as needed
            // For example:
            // binding.tvPcDesc.text = pc.desc
            // binding.tvPcBrand.text = pc.brand
            // binding.tvPcModel.text = pc.model
            // ...

            Glide.with(mContext)


        }
    }

    fun add(pcEntity: PcEntity) {
        //validating if the PC already exists
        if (!pcs.contains(pcEntity)) {
            pcs.add(pcEntity)
            notifyItemInserted(pcs.size - 1)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPcs(pcs: MutableList<PcEntity>) {
        this.pcs = pcs
        notifyDataSetChanged()
    }

    fun update(pcEntity: PcEntity) {
        val index = pcs.indexOf(pcEntity)
        if (index != -1) {
            pcs[index] = pcEntity
            notifyItemChanged(index)
        }
    }

    fun delete(pcEntity: PcEntity) {
        val index = pcs.indexOf(pcEntity)
        if (index != -1) {
            pcs.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}
