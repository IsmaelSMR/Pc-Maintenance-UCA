package com.damo.ucamaintenance.`interface`

import com.damo.ucamaintenance.entities.PcEntity

interface MainAux {

    fun hideFab(isVisible:Boolean=false)

    //actualizar adapter
    fun addStore(pcEntity: PcEntity)
    fun updateStore(pcEntity: PcEntity)
}