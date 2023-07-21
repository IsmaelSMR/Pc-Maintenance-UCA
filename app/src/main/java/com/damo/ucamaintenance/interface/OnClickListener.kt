package com.damo.ucamaintenance.`interface`

import com.damo.ucamaintenance.entities.PcEntity

interface OnClickListener {

    fun onClick(pcEntity: PcEntity)
    fun onFavoriteStore(pcEntity: PcEntity)
    fun onDeleteStore(pcEntity: PcEntity)
}