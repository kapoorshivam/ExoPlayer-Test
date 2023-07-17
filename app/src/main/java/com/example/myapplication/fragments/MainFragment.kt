package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.test.TestActivity

class MainFragment: Fragment(R.layout.fragment_main) {
    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListener()
    }

    private fun setupClickListener() {
        binding.btAudioPlayer.setOnClickListener {
            val fragmentManager = (activity as FragmentActivity).supportFragmentManager
            fragmentManager.commit {
                replace(R.id.fragment_container_view, AudioFragment())
                addToBackStack(null)
            }
        }

        binding.btVideoPlayer.setOnClickListener {
            val fragmentManager = (activity as FragmentActivity).supportFragmentManager
            fragmentManager.commit {
                replace(R.id.fragment_container_view, VideoFragment())
                addToBackStack(null)
            }
        }

        binding.testButton.setOnClickListener {
            val intent = Intent(activity, TestActivity::class.java)
            activity?.startActivity(intent)
        }
    }
}