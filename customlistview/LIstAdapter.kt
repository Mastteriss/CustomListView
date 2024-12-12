package com.example.customlistview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class LIstAdapter(context: Context, productList:MutableList<Shop>):
        ArrayAdapter<Shop>(context,R.layout.list_item,productList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val shop = getItem(position)
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }
        val imageViewIV = view?.findViewById<ImageView>(R.id.imageViewIV)
        val productNameTV = view?.findViewById<TextView>(R.id.productNameTV)
        val priceTV = view?.findViewById<TextView>(R.id.priceTV)

        imageViewIV?.setImageBitmap(shop?.image)
        productNameTV?.text = shop?.product
        priceTV?.text = shop?.price
        return view!!
    }
}