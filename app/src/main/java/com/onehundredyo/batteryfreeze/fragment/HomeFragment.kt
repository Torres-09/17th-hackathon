package com.onehundredyo.batteryfreeze.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.onehundredyo.batteryfreeze.R
import android.widget.TextView
import com.onehundredyo.batteryfreeze.App
import com.onehundredyo.batteryfreeze.MainActivity
import com.onehundredyo.batteryfreeze.databinding.FragmentHomeBinding
import java.time.LocalDate


class HomeFragment : Fragment() {
    private var remainPercentage: Long? = 0L
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity != null && activity is MainActivity) {
            remainPercentage = (activity as MainActivity?)?.getTotalDailyCarbon()
        }
    }

    fun compareDate(): Boolean {
        var currentDate: String = LocalDate.now().toString()
        val savedDate: String = App.prefs.getSavedDate("savedDate", "")
        if (currentDate != savedDate) {
            App.prefs.setSavedDate("savedDate", currentDate)
            return false
        } else
            return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        Log.d("homefragment", "yeah${remainPercentage}")
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val glacierImage: ImageView = view.findViewById(R.id.glacierImage)
        val polarBearImage: ImageView = view.findViewById(R.id.polarBearImage)
        val remainText: TextView = view.findViewById(R.id.remainText)
        val animation = AnimationUtils.loadAnimation(activity, R.anim.moving)
        var dailyCarbonDouble = (remainPercentage)?.toDouble()?.div(1000)
        var dailyCarbonInt = (remainPercentage)?.toDouble()?.div(1000)?.toInt()
        // 배출된 이산화탄소 양
        when (dailyCarbonInt) {
            in 76..100 -> {
            }
            in 51..75 -> {
//                glacierImage.setImageResource(R.drawable.glacier0)
//                polarBearImage.setImageResource(R.drawable.polar_bear2)
//                glacierImage.startAnimation(animation)
//                polarBearImage.startAnimation(animation)
                glacierImage.startAnimation(animation)
                polarBearImage.startAnimation(animation)
            }
            in 26..50 -> {
                glacierImage.setImageResource(R.drawable.glacier0)
                polarBearImage.setImageResource(R.drawable.polar_bear1)
                glacierImage.startAnimation(animation)
                polarBearImage.startAnimation(animation)
            }
            in 0..25 -> {
                glacierImage.setImageResource(R.drawable.glacier0)
                polarBearImage.setImageResource(R.drawable.polar_bear1)
                glacierImage.startAnimation(animation)
                polarBearImage.startAnimation(animation)
            }
            else -> {
                glacierImage.setImageResource(R.drawable.glacier0)
                polarBearImage.setImageResource(R.drawable.polar_bear1)
                glacierImage.startAnimation(animation)
                polarBearImage.startAnimation(animation)
            }
        }
        remainText.setText("오늘의 탄소배출량 ${dailyCarbonDouble}kg")


        //오늘의 문구
        val todayGoalText: TextView = view.findViewById(R.id.todayGoalText)
        if (compareDate()) {
            todayGoalText.setText(App.prefs.getSavedText("savedDate", "error"))
        } else {
            val range = (0..6)
            val randomNumber = range.random()
            val textArray: Array<String> = resources.getStringArray(R.array.dailyMission)
            val newText: String = textArray[randomNumber]
            todayGoalText.setText(newText)
            App.prefs.setSavedText("savedDate", newText)
        }
    }
}