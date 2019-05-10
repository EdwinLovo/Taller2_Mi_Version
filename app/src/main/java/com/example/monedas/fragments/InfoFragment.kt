package com.example.monedas.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.monedas.R
import com.example.monedas.models.Moneda
import kotlinx.android.synthetic.main.fragment_info.view.*

private const val ARG_PARAM1 = "moneda"

class InfoFragment : Fragment() {
    private var param1: Moneda? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false).apply {
            var moneda:Moneda? = arguments?.getParcelable("moneda")
            nombreMonedaInfo.text = moneda?.nombre
            ambitoMonedaInfo.text = moneda?.ambito
            añoMonedaInfo.text = moneda?.año
            disponibilidadMonedaInfo.text = moneda?.disponibilidad

            Glide.with(logoMonedaInfo.context)
                    .load(moneda?.logo)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(logoMonedaInfo)
        }
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Moneda) =
            InfoFragment().apply {
                arguments = Bundle().apply {

                    putParcelable(ARG_PARAM1,param1)
                }
            }
    }
}
