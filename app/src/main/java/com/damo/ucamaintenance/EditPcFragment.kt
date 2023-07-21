package com.damo.ucamaintenance

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.damo.ucamaintenance.databinding.FragmentEditPcBinding
import com.damo.ucamaintenance.entities.PcEntity
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.LinkedBlockingQueue

class EditPcFragment : Fragment() {

    private lateinit var mBinding: FragmentEditPcBinding
    private var mActivity: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentEditPcBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Recuperamos el main activity
        mActivity = activity as? MainActivity
        //Agregamos la flecha de retroceso en Action Bar
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //Cambiamos el titulo a mostrar en el action bar
        mActivity?.supportActionBar?.title = getString(R.string.edit_pc_title_add)

        //llamamos menu en el fragment
        setHasOptionsMenu(true)
/*
        //previsualizar imagen
        mBinding.etPhotoUrl.addTextChangedListener {
            Glide.with(this)
                .load(mBinding.etPhotoUrl.text.toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.imgPhoto)
        }
*/
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mActivity?.onBackPressedDispatcher?.onBackPressed()
                true
            }

            R.id.action_save -> {
                val pc = PcEntity(
                    name = mBinding.etName.text.toString().trim(),
                    desc = mBinding.etDesc.text.toString().trim(),
                    brand = mBinding.etBrand.text.toString().trim(),
                    model = mBinding.etModel.text.toString().trim(),
                    processor = mBinding.etProcessor.text.toString().trim(),
                    ram = mBinding.etRam.text.toString().trim(),
                    storage = mBinding.etStorage.text.toString().trim(),
                )

                val queue = LinkedBlockingQueue<Long?>()
                Thread {
                    pc.id = PcApplication.database.pcDao().addPc(pc)
                    queue.add(pc.id)
                }.start()

                queue.take()?.let {
                    //actualizar adapter
                    mActivity?.addPc(pc)
                    hideKeyboard()
                    Toast.makeText(mActivity, R.string.edit_pc_message_save_success, Toast.LENGTH_SHORT).show()
                    //mostrar menú principal al presionar guardar
                    mActivity?.onBackPressedDispatcher?.onBackPressed()
                }

                true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }

    private fun hideKeyboard() {
        val imm = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    override fun onDestroyView() {
        hideKeyboard()
        super.onDestroyView()
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        setHasOptionsMenu(false)
        super.onDestroy()
    }
}
