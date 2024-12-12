package com.example.customlistview

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.NinePatch
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class SecondActivity : AppCompatActivity() {
    private val GALLERY_REQUEST = 302
    private lateinit var menuButton:ImageButton
    var bitmap:Bitmap? = null
    val shops:MutableList<Shop> = mutableListOf()

    private lateinit var listViewLV:ListView
    private lateinit var productNameET:EditText
    private lateinit var priceET:EditText
    private lateinit var editImageIV:ImageView
    private lateinit var addBTN:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        init()
        menuButton = findViewById(R.id.menuButton)
        menuButton.setOnClickListener{
            shopMenu(it)
        }


        editImageIV.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent,GALLERY_REQUEST)
        }

        addBTN.setOnClickListener {
            createShop()

            val lIstAdapter = LIstAdapter(this@SecondActivity, shops)
            listViewLV.adapter = lIstAdapter
            lIstAdapter.notifyDataSetChanged()
            clearEditFields()
        }
    }
    private fun shopMenu(view: View){
        val popupMenu = PopupMenu(this,view)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item:MenuItem ->
            when(item.itemId){
                R.id.exit ->{
                    finish()
                    true
                }
                else ->false
            }
        }
        popupMenu.show()
    }

    private fun clearEditFields() {
        productNameET.text.clear()
        priceET.text.clear()
        editImageIV.setImageResource(R.drawable.baseline_add_photo_alternate_24)
    }

    private fun createShop() {
        val shopName = productNameET.text.toString()
        val shopPrice = priceET.text.toString()
        val shopImage = bitmap
        val shop = Shop(shopName, shopPrice, shopImage)
        shops.add(shop)
    }

    private fun init() {
        listViewLV = findViewById(R.id.listViewLV)
        productNameET = findViewById(R.id.productNameET)
        priceET = findViewById(R.id.priceET)
        editImageIV = findViewById(R.id.editImageIV)
        addBTN = findViewById(R.id.addBTN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        editImageIV = findViewById(R.id.editImageIV)
        when(requestCode){
            GALLERY_REQUEST -> if(resultCode === RESULT_OK){
                val selectedImage:Uri? = data?.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                }catch (e:IOException){
                    e.printStackTrace()
                }
                editImageIV.setImageBitmap(bitmap)
            }
        }
    }
}