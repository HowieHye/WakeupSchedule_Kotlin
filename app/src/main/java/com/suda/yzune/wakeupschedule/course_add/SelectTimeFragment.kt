package com.suda.yzune.wakeupschedule.course_add

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.suda.yzune.wakeupschedule.R
import com.suda.yzune.wakeupschedule.bean.CourseDetailBean
import com.suda.yzune.wakeupschedule.bean.CourseEditBean
import com.suda.yzune.wakeupschedule.bean.TimeBean
import kotlinx.android.synthetic.main.activity_add_course.*
import kotlinx.android.synthetic.main.activity_login_web.*
import kotlinx.android.synthetic.main.fragment_select_time.*

class SelectTimeFragment : DialogFragment() {

    var position = -1
    private val dayList = listOf<String>("周一", "周二", "周三", "周四", "周五", "周六", "周日")
    private val nodeList = arrayListOf<String>()
    private lateinit var viewModel: AddCourseViewModel
    private lateinit var course: CourseEditBean
    var day = 1
    var start = 1
    var end = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        viewModel = ViewModelProviders.of(activity!!).get(AddCourseViewModel::class.java)
        initNodeList(12)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select_time, container, false)
    }

    override fun onResume() {
        super.onResume()
        wp_day.data = dayList
        wp_start.data = nodeList
        wp_end.data = nodeList
        course = viewModel.getList()[position]
        day = course.time.value!!.day
        start = course.time.value!!.startNode
        end = course.time.value!!.endNode
        initEvent()
    }

    private fun initNodeList(max: Int) {
        for (i in 1..max) {
            nodeList.add("第 $i 节")
        }
    }

    private fun initEvent() {
        wp_day.selectedItemPosition = day - 1
        wp_start.selectedItemPosition = start - 1
        wp_end.selectedItemPosition = end - 1

        wp_day.setOnItemSelectedListener { _, _, position ->
            day = position + 1
        }
        wp_start.setOnItemSelectedListener { _, _, position ->
            start = position + 1
            if (end < start) {
                wp_end.animation
                wp_end.selectedItemPosition = start - 1
            }
        }
        wp_end.setOnItemSelectedListener { _, _, position ->
            end = position + 1
            if (end < start) {
                wp_end.selectedItemPosition = start - 1
            }
        }

        btn_cancel.setOnClickListener {
            dismiss()
        }

        btn_save.setOnClickListener {
            val result = TimeBean(day, start, end)
            viewModel.getList()[position].time.value = result
            dismiss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(arg: Int) =
                SelectTimeFragment().apply {
                    position = arg
                }
    }
}
