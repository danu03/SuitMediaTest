package com.danusuhendra.suitmediatest.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danusuhendra.suitmediatest.databinding.ActivityHomeBinding
import com.danusuhendra.suitmediatest.utils.EVENT_REQUEST_CODE
import com.danusuhendra.suitmediatest.utils.GUEST_REQUEST_CODE
import com.danusuhendra.suitmediatest.utils.NAME

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(NAME)

        binding.tvName.text = name

        binding.btnGuest.setOnClickListener {
            val intent = Intent(this, GuestActivity::class.java)
            startActivityForResult(intent, GUEST_REQUEST_CODE)
        }

        binding.btnEvent.setOnClickListener {
            val intent = Intent(this, EventActivity::class.java)
            startActivityForResult(intent, EVENT_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == GUEST_REQUEST_CODE) {
            data?.getStringExtra(NAME).let {
                binding.tvName.text = it
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == EVENT_REQUEST_CODE) {
            data?.getStringExtra(NAME).let {
                binding.tvName.text = it
            }
        }
    }
}