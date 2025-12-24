package com.example.myapplication

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MediaPlayerActivity : AppCompatActivity() {

    private var player: MediaPlayer? = null
    private var index = 0

    private val songs = listOf(
        R.raw.song_1, R.raw.song_2, R.raw.song_3, R.raw.song_4,
        R.raw.song_5, R.raw.song_6, R.raw.song_7, R.raw.song_8,
        R.raw.song_9, R.raw.song_10, R.raw.song_11, R.raw.song_12,
        R.raw.song_13, R.raw.song_14, R.raw.song_15, R.raw.song_16,
        R.raw.song_17
    )

    private val names = mutableListOf(
        "1000 – ATL", "Не надо меня узнавать – Скриптонит", "Архитектор – ATL",
        "Астронавт – ATL", "В унисон – ATL", "Вороний грай – ATL",
        "Гори ясно – ATL", "Майк – ATL", "Обратно – ATL",
        "Священный рэйв – ATL", "Коньяк – Скриптонит", "Космос – Скриптонит",
        "Положение – Скриптонит", "Чистый – Скриптонит", "Любовь – Скриптонит",
        "Танцуйте – ATL", "Шаман – ATL"
    )

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player)

        val btnBack: Button = findViewById(R.id.btnBackToMenu)
        val btnPlay: Button = findViewById(R.id.btnPlayPause)
        val btnPrev: Button = findViewById(R.id.btnPrevious)
        val btnNext: Button = findViewById(R.id.btnNext)
        val btnSort: Button = findViewById(R.id.btnSort)

        val tvName: TextView = findViewById(R.id.tvTrackName)
        val tvTime: TextView = findViewById(R.id.tvTime)

        val seek: SeekBar = findViewById(R.id.seekBar)
        val vol: SeekBar = findViewById(R.id.volumeSeekBar)

        val list: ListView = findViewById(R.id.lvPlaylist)

        val audio = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        vol.max = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        vol.progress = audio.getStreamVolume(AudioManager.STREAM_MUSIC)

        vol.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, p: Int, fromUser: Boolean) {
                if (fromUser) {
                    audio.setStreamVolume(AudioManager.STREAM_MUSIC, p, 0)
                }
            }

            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        val lvPlaylist: ListView = findViewById(R.id.lvPlaylist)

        val adapter = ArrayAdapter(
            this,
            R.layout.item_track,
            R.id.tvItem,
            names
        )

        lvPlaylist.adapter = adapter

        player = MediaPlayer.create(this, songs[index])
        tvName.text = names[index]
        seek.max = player!!.duration

        btnBack.setOnClickListener {
            finish()
        }

        btnPlay.setOnClickListener {
            if (player!!.isPlaying) {
                player!!.pause()
                btnPlay.text = "▶"
            } else {
                player!!.start()
                btnPlay.text = "⏸"
            }
        }

        btnNext.setOnClickListener {
            player!!.stop()
            player!!.release()

            index++
            if (index >= songs.size) index = 0

            player = MediaPlayer.create(this, songs[index])
            tvName.text = names[index]
            seek.max = player!!.duration
            seek.progress = 0

            player!!.start()
            btnPlay.text = "⏸"
        }

        btnPrev.setOnClickListener {
            player!!.stop()
            player!!.release()

            index--
            if (index < 0) index = songs.size - 1

            player = MediaPlayer.create(this, songs[index])
            tvName.text = names[index]
            seek.max = player!!.duration
            seek.progress = 0

            player!!.start()
            btnPlay.text = "⏸"
        }

        btnSort.setOnClickListener {
            names.sort()
            adapter.notifyDataSetChanged()
            tvName.text = names[index]
        }

        list.setOnItemClickListener { _, _, pos, _ ->
            player!!.stop()
            player!!.release()

            index = pos
            player = MediaPlayer.create(this, songs[index])
            tvName.text = names[index]
            seek.max = player!!.duration
            seek.progress = 0

            player!!.start()
            btnPlay.text = "⏸"
        }

        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, p: Int, fromUser: Boolean) {
                if (fromUser) {
                    player!!.seekTo(p)
                }
            }

            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        handler.post(object : Runnable {
            override fun run() {
                if (player != null) {
                    seek.progress = player!!.currentPosition

                    val cur = player!!.currentPosition / 1000
                    val dur = player!!.duration / 1000
                    tvTime.text = "${cur / 60}:${cur % 60} / ${dur / 60}:${dur % 60}"
                }
                handler.postDelayed(this, 500)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }
}
