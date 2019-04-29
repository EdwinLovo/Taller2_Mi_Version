package com.example.monedas

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monedas.adapters.ListaMonedasAdapter
import com.example.monedas.api.apiService
import com.example.monedas.models.Moneda
import com.example.monedas.models.RespuestaMoneda
import kotlinx.android.synthetic.main.fragment_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment(), InfoFragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var retrofit: Retrofit
    private lateinit var recyclerView: RecyclerView
    private lateinit var listaMonedasAdapter: ListaMonedasAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_main, container, false)

        initRecyclerView(view)
        obtenerDatos()

        return view
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
        fun newInstance(param1: String, param2: String) =
                MainFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    private fun initRecyclerView(view: View){
        recyclerView = view.RecyclerView
        var layoutManager2 = GridLayoutManager(view.context,2)
        listaMonedasAdapter = object : ListaMonedasAdapter(){
            override fun addListener(holder: ViewHolder, moneda: Moneda) {
                holder.contenedorMoneda.setOnClickListener {
                    val infoFragment = InfoFragment.newInstance(moneda)

                    if (resources.configuration.orientation == 1){
                        fragmentManager!!.beginTransaction().replace(R.id.MainFrameLayout, infoFragment).addToBackStack(null).commit()
                    } else if (resources.configuration.orientation == 2){
                        fragmentManager!!.beginTransaction().replace(R.id.SecondFrameLayout, infoFragment).commit()
                    }
                }
            }
        }
        if (resources.configuration.orientation == 2){
            layoutManager2 = GridLayoutManager(view.context,1)
        }
        recyclerView.apply {
            adapter = listaMonedasAdapter
            setHasFixedSize(true)
            layoutManager = layoutManager2
        }
    }

    private fun obtenerDatos(){
        retrofit = Retrofit.Builder()
                .baseUrl("https://taller2-a645b.firebaseio.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(apiService::class.java)
        var monedaRespuestaCall: Call<RespuestaMoneda> = service.obtenerListaMonedas()

        monedaRespuestaCall.enqueue(object : Callback<RespuestaMoneda>{
            override fun onFailure(call: Call<RespuestaMoneda>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<RespuestaMoneda>, response: Response<RespuestaMoneda>) {
                if(response.isSuccessful){
                    var monedaRespuesta = response.body()
                    var listaMoneda:ArrayList<Moneda> = monedaRespuesta?.moneda!!
                    listaMonedasAdapter.adicionarMonedas(listaMoneda)
                }
            }
        })
    }

}
