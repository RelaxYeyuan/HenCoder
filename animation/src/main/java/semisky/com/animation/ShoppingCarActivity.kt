package semisky.com.animation

import android.content.Context
import android.graphics.PointF
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_shopping_car.*
import java.util.*


class ShoppingCarActivity : AppCompatActivity() {

    private val TAG = "ShoppingCarActivity"

    private var start: PointF = PointF()
    private var end: PointF = PointF()
    private var cross: PointF = PointF()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_car)

        //获取状态栏高度
        var height = 0
        val resourceId = applicationContext.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            height = applicationContext.resources.getDimensionPixelSize(resourceId)
        }

        val data: ArrayList<String>? = ArrayList()
        data?.add("土豆")
        data?.add("土豆")
        data?.add("土豆")
        data?.add("土豆")
        data?.add("土豆")
        data?.add("土豆")
        data?.add("土豆")
        data?.add("土豆")
        val adapter = MyAdapter(this, R.layout.item_shopping_car, data)
        lv_shopping.adapter = adapter
        adapter.setListener(object : MyAdapter.OnClickBtnViewListener {
            override fun getBtnView(button: Button) {
                val position = intArrayOf(0, 1)
                button.getLocationOnScreen(position)

                start.x = position[0].toFloat()
                start.y = position[1].toFloat() - height


                Log.d(TAG, "location x ${start.x}")
                Log.d(TAG, "location y ${start.y}")


                cross.x = end.x
                cross.y = start.y
                startAnimation()
            }
        })
    }

    private fun startAnimation() {
        val bezierView = BezierView(this)
        bezierView.setViewGroup(bezier_relativeLayout, bezierView)
        bezierView.setStartPosition(start, end, cross)
        bezierView.setImageResource(R.drawable.icon_add)
        val layoutParams = RelativeLayout.LayoutParams(100, 100)
        bezier_relativeLayout.addView(bezierView, layoutParams)

        bezierView.startAnim()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        Log.d(TAG, "actionBar ${actionBar}")

        val position = intArrayOf(0, 1)
        iv_shopping_car.getLocationInWindow(position)

        end.x = position[0].toFloat()
        end.y = position[1].toFloat()
    }
}

class MyAdapter(context: Context, private val resource: Int, private val data: ArrayList<String>?) :
        ArrayAdapter<String>(context, resource, data) {

    private val TAG = "ShoppingCarActivity"

    private var onClickBtnViewListener: OnClickBtnViewListener? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resource, null)
            viewHolder = ViewHolder()
            view.tag = viewHolder

            viewHolder.btn = view.findViewById(R.id.btn)
            viewHolder.text = view.findViewById(R.id.text)
            viewHolder.text?.text = data?.get(position)

            viewHolder.btn?.setOnClickListener {
                onClickBtnViewListener?.getBtnView(viewHolder.btn!!)
            }
        } else {
            view = convertView
        }

        return view
    }

    class ViewHolder {
        var btn: Button? = null
        var text: TextView? = null
    }

    fun setListener(listener: OnClickBtnViewListener) {
        this.onClickBtnViewListener = listener
    }

    interface OnClickBtnViewListener {
        fun getBtnView(button: Button)
    }
}