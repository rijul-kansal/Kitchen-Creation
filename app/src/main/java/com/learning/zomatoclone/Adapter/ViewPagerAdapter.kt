package com.learning.zomatoclone.Adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.airbnb.lottie.LottieAnimationView
import com.learning.zomatoclone.R


class ViewPagerAdapter(var context: Context) : PagerAdapter() {
    var images = intArrayOf(
        R.raw.food_app,
        R.raw.cooking_food,
        R.raw.cooking
    )
    var headings = intArrayOf(
        R.string.heading1,
        R.string.heading2,
        R.string.heading3
    )
    var descriptions = intArrayOf(
        R.string.description1,
        R.string.description2,
        R.string.description3
    )

    override fun getCount(): Int {
        return headings.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.slider_layout, container, false)
        val slideTitleImage = view.findViewById<View>(R.id.animation_view) as LottieAnimationView
        val slideHeading = view.findViewById<View>(R.id.texttitle) as TextView
        val slideDescription = view.findViewById<View>(R.id.textdeccription) as TextView

        // Set Lottie animation
        slideTitleImage.setAnimation(images[position])

        // Set heading and description
        slideHeading.setText(headings[position])
        slideDescription.setText(descriptions[position])

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}
