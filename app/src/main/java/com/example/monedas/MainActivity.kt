package com.example.monedas

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), MainFragment.OnFragmentInteractionListener, InfoFragment.OnFragmentInteractionListener {

    private lateinit var mainFragment:MainFragment

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainFragment = MainFragment()
        supportFragmentManager.beginTransaction().add(R.id.MainFrameLayout, mainFragment).commit()
    }
}
