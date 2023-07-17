package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.Util
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.LoadControl
import androidx.media3.exoplayer.hls.DefaultHlsExtractorFactory
import androidx.media3.exoplayer.hls.HlsExtractorFactory
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.trackselection.TrackSelector
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter
import androidx.media3.extractor.ts.DefaultTsPayloadReaderFactory
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentVideoBinding


class VideoFragment : Fragment(R.layout.fragment_video) {

    lateinit var binding: FragmentVideoBinding
    lateinit var player: ExoPlayer
    lateinit var mediaURI: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mediaURI =
//            "https://diceyk6a7voy4.cloudfront.net/e78752a1-2e83-43fa-85ae-3d508be29366/hls/fitfest-sample-1_Ott_Hls_Ts_Avc_Aac_16x9_1280x720p_30Hz_6.0Mbps_qvbr.m3u8"
        mediaURI = "https://vt-hls.stg13.loconav.dev/hls/864281043977629_back.m3u8"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVideoPlayer()
    }

    private fun getHlsMediaSource(urlStream: String): MediaSource {
        val userAgent =
            Util.getUserAgent(requireContext(), requireContext().getString(R.string.app_name))

        val dataSourceFactory: DataSource.Factory =
            DefaultHttpDataSource.Factory().setUserAgent(userAgent)

//        val mediaItem =
//            MediaItem.Builder().setUri(urlStream).setMimeType(MimeTypes.APPLICATION_M3U8).build()

        val flags = (DefaultTsPayloadReaderFactory.FLAG_ALLOW_NON_IDR_KEYFRAMES
                or DefaultTsPayloadReaderFactory.FLAG_DETECT_ACCESS_UNITS)
        val extractorFactory: HlsExtractorFactory = DefaultHlsExtractorFactory(flags, true)

        // Create a HLS media source pointing to a playlist uri.
        val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .setExtractorFactory(extractorFactory)
//            .setAllowChunklessPreparation(false)
//            .setTimestampAdjusterInitializationTimeoutMs(3000)
            .createMediaSource(MediaItem.fromUri(urlStream))
        return hlsMediaSource
    }

    private fun initVideoPlayer() {
        val trackSelector: TrackSelector = DefaultTrackSelector(requireContext())
        val bandwidthMeter: DefaultBandwidthMeter = DefaultBandwidthMeter.Builder(requireContext()).build()
        val loadControl: LoadControl = DefaultLoadControl()
//        val renderersFactory = DefaultRenderersFactory(requireContext())
//            .forceEnableMediaCodecAsynchronousQueueing()

        player =
            ExoPlayer.Builder(requireContext(),
//                renderersFactory
            )
//                .setTrackSelector(trackSelector)
//                .setBandwidthMeter(bandwidthMeter)
//                .setLoadControl(loadControl)
                .build().apply {
//                    trackSelectionParameters = trackSelectionParameters.buildUpon().setMaxVideoSizeSd().build()
                }

        // Creating the media source for streaming through HTTP
//        val mediaSource = ProgressiveMediaSource.Factory(
//            CacheDataSource.Factory()
//                .setCache(SimpleMediaPlayer.simpleCache)
//                .setUpstreamDataSourceFactory(
//                    DefaultHttpDataSource.Factory()
//                        .setUserAgent("ExoPlayer")
//                )
//                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
//        ).createMediaSource(MediaItem.fromUri(mediaURI))

        // Attach the ExoPlayer instance to the StyledPlayer View in the XML
        binding.videoStyledPlayerView.player = player
        player.setMediaSource(getHlsMediaSource(mediaURI))
        player.prepare()
        player.playWhenReady = true
//        player.volume = 0f

//        player.setVideoFrameMetadataListener { presentationTimeUs, releaseTimeNs, format, mediaFormat ->
//            Log.i("presentationTimeUs", "presentationTimeUs $presentationTimeUs")
//            Log.i("format", "format $format")
//            Log.i("mediaFormat", "mediaFormat $mediaFormat")
//        }

        player.addListener(object : Player.Listener {

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                Log.i("state state", "isPlaying $isPlaying")
            }

            override fun onEvents(p: Player, events: Player.Events) {
                Log.i("player", "player ${p.videoSize} events ${events}")
            }


            override fun onRenderedFirstFrame() {
                Log.i("onRenderedFirstFrame", "onRenderedFirstFrame")
            }

            override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                Log.i("onMediaMetadataChanged", "mediaMetadata $mediaMetadata")
            }

            override fun onPlaybackStateChanged(@Player.State state: Int) {
                Log.i("state state", "state $state")
            }

            override fun onPlayerError(error: PlaybackException) {
                Log.i("PlaybackException", "error $error")
                Log.i("EXO-ERROR", "error.cause.message ${error.cause?.message}")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}