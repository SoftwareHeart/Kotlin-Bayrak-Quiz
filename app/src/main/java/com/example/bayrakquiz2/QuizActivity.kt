package com.example.bayrakquiz2



import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.bayrakquiz2.databinding.ActivityQuizBinding


class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    private lateinit var sorular : ArrayList<Bayraklar>
    private lateinit var yanlisSecenekler:ArrayList<Bayraklar>
    private lateinit var dogruSoru:Bayraklar
    private lateinit var tumSecenekler:HashSet<Bayraklar>
    private lateinit var vt:VeritabaniYardimcisi
    private var durum =true
    private lateinit var dogruButton :Button
    private var soruSayac = 0
    private var dogruSayac = 0
    private var yanlisSayac = 0
    private var bosSoruSayac = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        buttonlariBoya()
        //30 saniyelik sayaç
        object : CountDownTimer(30000, 1000) {

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                binding.textViewSure.setText("Süre: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                Toast.makeText(applicationContext,"Maalesef süreniz bitti :((",Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext,ResultActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()

        vt = VeritabaniYardimcisi(this)

        sorular = Bayraklardao().rastgele5BayrakGetir(vt)

        soruYukle()
        dogruButton = dogruCevapRenklendirmeKontrol()

        binding.buttonA.setOnClickListener {
           dogruKontrol(binding.buttonA)
            Handler().postDelayed(Runnable {
                butonlariActiveEt()
                buttonlariBoya()
                soruSayacKontrol()
            }, 1400)

            butonlariInActiveEt()
            if (!durum){
                dogruCevapRenklendirme(dogruCevapRenklendirmeKontrol())
            }

        }
        binding.buttonB.setOnClickListener {
            dogruKontrol(binding.buttonB)
            Handler().postDelayed(Runnable {
                buttonlariBoya()
                butonlariActiveEt()
                soruSayacKontrol()
            }, 1400)
            butonlariInActiveEt()

            if (!durum){
                dogruCevapRenklendirme(dogruCevapRenklendirmeKontrol())
            }

        }
        binding.buttonC.setOnClickListener {
            dogruKontrol(binding.buttonC)
            Handler().postDelayed(Runnable {
                butonlariActiveEt()
                buttonlariBoya()
                soruSayacKontrol()
            }, 1400)

            butonlariInActiveEt()
            if (!durum){
                dogruCevapRenklendirme(dogruCevapRenklendirmeKontrol())
            }

        }
        binding.buttonD.setOnClickListener {
            dogruKontrol(binding.buttonD)

            Handler().postDelayed(Runnable {
                butonlariActiveEt()
                buttonlariBoya()
                soruSayacKontrol()
            }, 1400)
            butonlariInActiveEt()

            if (!durum){
                dogruCevapRenklendirme(dogruCevapRenklendirmeKontrol())
            }

        }
    }

    fun butonlariInActiveEt()
    {
        for (otherView in listOf(binding.buttonA, binding.buttonB, binding.buttonC,binding.buttonD)) {
                otherView.isEnabled = false // Diğer butonları devre dışı bırak
        }
    }
    fun butonlariActiveEt(){
        for (otherView in listOf(binding.buttonA, binding.buttonB, binding.buttonC,binding.buttonD)) {
            otherView.isEnabled = true // Diğer butonları devre dışı bırak
        }
    }
    fun buttonlariBoya()
    {
        for (otherView in listOf(binding.buttonA, binding.buttonB, binding.buttonC,binding.buttonD)) {
            otherView.setBackgroundColor(getColor(R.color.purple_500)) // Diğer butonları boya
        }
    }



    fun soruYukle(){
        binding.textViewSoruSayi.text = "${soruSayac+1}. Soru"

        dogruSoru = sorular.get(soruSayac)

        binding.imageViewBayrak.setImageResource(resources.getIdentifier(dogruSoru.bayrak_resim,"drawable",packageName))

        yanlisSecenekler = Bayraklardao().yanlisSecenekGetir(vt,dogruSoru.bayrak_id)

        tumSecenekler = HashSet()

        tumSecenekler.add(dogruSoru)
        tumSecenekler.add(yanlisSecenekler.get(0))
        tumSecenekler.add(yanlisSecenekler.get(2))
        tumSecenekler.add(yanlisSecenekler.get(1))

        binding.buttonA.text = tumSecenekler.elementAt(0).bayrak_ad
        binding.buttonB.text = tumSecenekler.elementAt(1).bayrak_ad
        binding.buttonC.text = tumSecenekler.elementAt(2).bayrak_ad
        binding.buttonD.text = tumSecenekler.elementAt(3).bayrak_ad
    }



    fun soruSayacKontrol(){
        soruSayac++

        if (soruSayac!=5)
        {
            bosSoruSayac++
            soruYukle()
            dogruButton = dogruCevapRenklendirmeKontrol()
        }else{
            val intent = Intent(this,ResultActivity::class.java)
            intent.putExtra("dogruSayac",dogruSayac)
            intent.putExtra("yanlisSayac",yanlisSayac)
            intent.putExtra("bosSoruSayac",bosSoruSayac)
            startActivity(intent)
            finish()
        }
    }

    fun dogruKontrol(button:Button){

        val buttonYazi = button.text.toString()

        val dogruCevap = dogruSoru.bayrak_ad

        if (buttonYazi == dogruCevap){
            dogruSayac++
            durum = true
            button.setBackgroundColor(getColor(R.color.teal_200))

        }else{
            yanlisSayac++
            durum = false
            button.setBackgroundColor(getColor(R.color.red))
        }
        binding.textViewDogru.text = "Doğru : $dogruSayac"
        binding.textViewYanlis.text = "Yanlış : ${yanlisSayac}"

    }
    fun dogruCevapRenklendirme(button:Button){
        button.setBackgroundColor(getColor(R.color.teal_200))
    }
    fun dogruCevapRenklendirmeKontrol():Button{
        val button = ArrayList<Button>()
        var dogruButton = Button(this)
        button.add(binding.buttonA)
        button.add(binding.buttonB)
        button.add(binding.buttonC)
        button.add(binding.buttonD)
        for (s in button){
            if (s.text == dogruSoru.bayrak_ad)
            {
               dogruButton = s
                break
            }
        }
        return dogruButton

    }

}