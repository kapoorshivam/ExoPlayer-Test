package com.example.myapplication.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R


class TestActivity : AppCompatActivity() {

    private var viewPager: ViewPager2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity)

        viewPager = findViewById(R.id.viewPager2)

        val mediaList = ArrayList<String>()
        mediaList.add("https://prd-vt.loconav.com/hls/869409060579029_back.m3u8")
        mediaList.add("https://vt-hls.stg13.loconav.dev/hls/864281043977629_back.m3u8")
        mediaList.add("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8")
        mediaList.add("https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8")
        mediaList.add("https://fcc3ddae59ed.us-west-2.playback.live-video.net/api/video/v1/us-west-2.893648527354.channel.YtnrVcQbttF0.m3u8")
        mediaList.add("https://multiplatform-f.akamaihd.net/i/multi/will/bunny/big_buck_bunny_,640x360_400,640x360_700,640x360_1000,950x540_1500,.f4v.csmil/master.m3u8")
        mediaList.add("https://cph-p2p-msl.akamaized.net/hls/live/2000341/test/master.m3u8")
        mediaList.add("https://diceyk6a7voy4.cloudfront.net/e78752a1-2e83-43fa-85ae-3d508be29366/hls/fitfest-sample-1_Ott_Hls_Ts_Avc_Aac_16x9_1280x720p_30Hz_6.0Mbps_qvbr.m3u8")

        val adapter = PlayerFragmentAdapter(this, mediaList)
        viewPager?.adapter = adapter
    }
}