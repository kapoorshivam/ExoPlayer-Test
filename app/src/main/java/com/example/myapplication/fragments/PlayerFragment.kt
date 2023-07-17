package com.example.myapplication.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.Util
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.ui.PlayerView
import com.example.myapplication.R

class PlayerFragment : Fragment(R.layout.fragment_player) {

    var playerView: PlayerView? = null
    private var text: TextView? = null

    private var player: ExoPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mediaUrl = arguments?.getString("url")

        playerView = view.findViewById(R.id.player)
        text = view.findViewById(R.id.video_url_tv)

        setUpPlayer()
        if (mediaUrl != null) {
            addMediaItem(mediaUrl)
            text?.text = mediaUrl
        }
    }

    private fun addMediaItem(mediaUrl: String) {

        //Creating a media item of HLS Type
//        val mediaItem = MediaItem.Builder()
//            .setUri(mediaUrl)
//            .setMimeType(MimeTypes.APPLICATION_M3U8) //m3u8 is the extension used with HLS sources
//            .build()

        val userAgent =
            Util.getUserAgent(requireContext(), requireContext().getString(R.string.app_name))

        val dataSourceFactory: DataSource.Factory =
            DefaultHttpDataSource.Factory().setUserAgent(userAgent)


        val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
//            .setExtractorFactory(extractorFactory)
//            .setAllowChunklessPreparation(false)
//            .setTimestampAdjusterInitializationTimeoutMs(3000)
            .createMediaSource(MediaItem.fromUri(mediaUrl))

        player?.setMediaSource(hlsMediaSource)
        player?.prepare()
//        player?.repeatMode = Player.REPEAT_MODE_ONE //repeating the video from start after it's over
        player?.play()
    }

    private fun  setUpPlayer(){

        //initializing exoplayer
        player = ExoPlayer.Builder(requireContext()).build()

        //set up audio attributes
//        val audioAttributes = AudioAttributes.Builder()
//            .setUsage(C.USAGE_MEDIA)
//            .setContentType(C.CONTENT_TYPE_MOVIE)
//            .build()
//        player?.setAudioAttributes(audioAttributes, false)

        playerView?.player = player

        //hiding all the ui StyledPlayerView comes with

        //setting the scaling mode to scale to fit
//        player?.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
    }

    companion object {
        fun newInstance(url: String) = PlayerFragment()
            .apply {
                arguments = Bundle().apply {
                    putString("url", url)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        playerView?.player?.play()
    }

    override fun onPause() {
        super.onPause()
        playerView?.player?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        playerView?.player?.stop()
        playerView?.player?.release()
    }
}