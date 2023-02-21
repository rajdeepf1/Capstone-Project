package com.example.pizza_singh_capstone_project.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.pizza_singh_capstone_project.R
import com.example.pizza_singh_capstone_project.activities.ProductListActivity
import com.example.pizza_singh_capstone_project.models.CategoriesModel

class HomeGridAdapter (context: Context, list: List<CategoriesModel>) : BaseAdapter() {

    //Passing Values to Local Variables

    var context = context
    var list = list


    //Auto Generated Method
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var myView = convertView
        var holder: ViewHolder

        if (myView == null) {

            val mInflater = (context as Activity).layoutInflater
            myView = mInflater.inflate(R.layout.grid_item, parent, false)
            holder = ViewHolder()

            holder.mImageView = myView!!.findViewById<ImageView>(R.id.imageView) as ImageView
            holder.mTextView = myView!!.findViewById<TextView>(R.id.textView) as TextView
            myView.setTag(holder)
        } else {
            holder = myView.getTag() as ViewHolder
        }

        val position = list.get(position)

        Glide
            .with(context)
            .load(position.categoryImage)
            .centerCrop()
            .into(holder.mImageView!!)

        holder.mTextView!!.setText(position.categoryName)
//        holder.rlGridItem?.setOnClickListener({
//            context.startActivity(Intent(context,ProductListActivity::class.java))
//        })

        return myView

    }

    override fun getItem(p0: Int): Any {
        return list.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list.size
    }
    class ViewHolder {

        var mImageView: ImageView? = null
        var mTextView: TextView? = null
        var rlGridItem: RelativeLayout? = null

    }

}