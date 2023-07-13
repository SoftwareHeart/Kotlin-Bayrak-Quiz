package com.example.bayrakquiz2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bayrakquiz2.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var vt:VeritabaniYardimcisi
    private lateinit var oturumlar : ArrayList<Oturum>
    private lateinit var adapter: OturumAdapter
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        vt = VeritabaniYardimcisi(this)
        oturumlar = ArrayList<Oturum>()

        val dogruSayac = intent.getIntExtra("dogruSayac",0)
        val yanlisSayac = intent.getIntExtra("yanlisSayac",0)
        val bosSoruSayac = intent.getIntExtra("bosSoruSayac",0)
        val basari = (dogruSayac*100)/5
        val toplamSoru = dogruSayac+yanlisSayac
        Oturumdao().veriEkle(vt,dogruSayac,yanlisSayac,(4-bosSoruSayac),basari.toInt())
        //var ot1 = Oturum(oturum_id = 1,2,3,4,50)
        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(this)
        oturumlar = Oturumdao().verileriGetir(vt)

        adapter =OturumAdapter(this@ResultActivity,oturumlar)
        binding.rv.adapter = adapter

        binding.textViewSonuc.text = "$dogruSayac DOĞRU $yanlisSayac YANLIŞ ${4-bosSoruSayac} BOŞ"

        binding.textViewYuzdeSonuc.text = "Başarı Oranı : %$basari"

        binding.buttonTekrar.setOnClickListener {
            startActivity(Intent(this,QuizActivity::class.java))
            finish()
        }
        binding.buttonAnaSayfa.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        binding.buttonCikisResult.setOnClickListener {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}