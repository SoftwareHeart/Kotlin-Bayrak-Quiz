package com.example.bayrakquiz2

class Bayraklardao {

    fun rastgele5BayrakGetir(vt:VeritabaniYardimcisi):ArrayList<Bayraklar>
    {
        val bayraklarListe = ArrayList<Bayraklar>()

        val db = vt.writableDatabase

        val c = db.rawQuery("SELECT * FROM bayraklar order by RANDOM() LIMIT 5",null)

        while(c.moveToNext())
        {
            val bayraklar = Bayraklar(
                bayrak_id = c.getInt(c.getColumnIndexOrThrow("bayrak_id")),
                c.getString(c.getColumnIndexOrThrow("bayrak_ad")),
                bayrak_resim = c.getString(c.getColumnIndexOrThrow("bayrak_resim"))
                )
            bayraklarListe.add(bayraklar)
        }
        return bayraklarListe
    }
    fun yanlisSecenekGetir(vt:VeritabaniYardimcisi,bayrak_id:Int):ArrayList<Bayraklar>
    {
        val bayraklarListe = ArrayList<Bayraklar>()

        val db = vt.writableDatabase

        val c = db.rawQuery("SELECT * FROM bayraklar where bayrak_id !=$bayrak_id order by RANDOM() LIMIT 3 ",null)

        while(c.moveToNext())
        {
            val bayraklar = Bayraklar(
                bayrak_id = c.getInt(c.getColumnIndexOrThrow("bayrak_id")),
                c.getString(c.getColumnIndexOrThrow("bayrak_ad")),
                bayrak_resim = c.getString(c.getColumnIndexOrThrow("bayrak_resim"))
            )
            bayraklarListe.add(bayraklar)
        }
        return bayraklarListe
    }


}