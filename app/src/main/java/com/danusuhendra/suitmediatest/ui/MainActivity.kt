package com.danusuhendra.suitmediatest.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.danusuhendra.suitmediatest.databinding.ActivityMainBinding
import com.danusuhendra.suitmediatest.utils.CAMERA_REQUEST_CODE
import com.danusuhendra.suitmediatest.utils.GALLERY_REQUEST_CODE
import com.danusuhendra.suitmediatest.utils.NAME
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.circleImage.setOnClickListener {
            Dexter.withContext(this)
                .withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                        p0?.let {
                            if (p0.areAllPermissionsGranted()) {
                                val pictureDialog = AlertDialog.Builder(this@MainActivity)
                                pictureDialog.setTitle("Select Action")
                                val pictureDialogItem = arrayOf(
                                    "Select photo from Gallery",
                                    "Capture photo from Camera"
                                )
                                pictureDialog.setItems(pictureDialogItem) { _, which ->

                                    when (which) {
                                        0 -> gallery()
                                        1 -> camera()
                                    }
                                }
                                pictureDialog.show()
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        p1?.continuePermissionRequest()
                    }
                }).check()
        }

        binding.btnCheck.setOnClickListener {
            val textPalindrome = binding.edtPalindrome.text.toString()
            if (textPalindrome.isNotEmpty()) {
                if (isPalindromeString(textPalindrome)) {
                    alertDialog("$textPalindrome adalah Palindrome")
                } else {
                    alertDialog("$textPalindrome bukan Palindrome")
                }
            } else {
                alertDialog("Field text palindrom tidak boleh kosong!")
            }

        }

        binding.btnNext.setOnClickListener {
            val name = binding.edtName.text.toString()
            val textPalindrome = binding.edtPalindrome.text.toString()
            if (name.isNotEmpty() && textPalindrome.isNotEmpty()) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra(NAME, name)
                startActivity(intent)
                finish()
            } else {
                alertDialog("Field Name dan Text Palindrome tidak boleh kosong!")
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                CAMERA_REQUEST_CODE -> {

                    val bitmap = data?.extras?.get("data") as Bitmap

                    //we are using coroutine image loader (coil)
                    Glide.with(this)
                        .load(bitmap)
                        .centerCrop()
                        .into(binding.circleImage)
                }

                GALLERY_REQUEST_CODE -> {

                    Glide.with(this)
                        .load(data?.data)
                        .centerCrop()
                        .into(binding.circleImage)
                }
            }

        }

    }

    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun alertDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setNegativeButton("OK") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun isPalindromeString(inputStr: String): Boolean {
        //reverse the string
        val reverseStr = inputStr.reversed()

        //compare reversed string with input string
        return inputStr.equals(reverseStr, ignoreCase = true)
    }
}