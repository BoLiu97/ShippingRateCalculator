package com.example.shippingratecalculator

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Integer.parseInt

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        itemList?.clear()
        showItems()
        //To change body of created functions use File | Settings | File Templates.
    }

    class Item {
        var name = ""
        var weight = 0
    }
    var itemList: ArrayList<Item>? = null
    //val itemPrices = arrayOf(5.0, 4.0, 3.0)
    //val itemPrices = Array(3) { i -> 5.0 - i }
    val itemPrices = Array(3) { 5.0 - it }
    var couponApplied = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemList = ArrayList()
        add_button.setOnClickListener {
            addItem(item_name.text.toString(), item_weight.text.toString())
            Log.d("test", "add button clicked")
            showItems()
            item_name.text.clear()
            item_weight.text.clear()

        }
        coupon_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            couponApplied = isChecked

            showItems()
        }
        clear_button.setOnClickListener(this)
    }
        fun addItem(name: String, weight: String) {
        val item = Item()
        item.name = name
        item.weight = if (weight == "") 0 else parseInt(weight)
        itemList?.add(item)
    }
    fun showItems() {
        var sumCost = 0.0
        var sumWeight = 0
        itemList?.run {
            for (i in 0 until this.size) {
                sumCost += getItemPrice(this[i].weight)
            }

            sumWeight = this.sumBy { i -> i.weight }
        }
        var information = "Total weight $sumWeight     Total cost $sumCost\n"
        itemList?.forEach {
            information += "item:${it.name}, weight: ${it.weight}lb, rate: ${getItemPrice(it.weight)}\n"
        }
        info_textview.text = information
    }

    fun getItemPrice(weight: Int): Double {
        val discount = if (couponApplied) 0.9 else 1.0
        var pr = 0.0
        when (weight) {
            in 0..10 -> pr = weight * itemPrices[0]
            in 11..100 -> pr = weight * itemPrices[1]
            else -> pr = weight * itemPrices[2]
        }
        return (pr * discount * 100).toInt() / 100.0
    }

}
