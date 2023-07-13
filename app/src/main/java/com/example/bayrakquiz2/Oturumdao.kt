package com.example.bayrakquiz2

import android.content.ContentValues

class Oturumdao {
    fun veriEkle(vt:VeritabaniYardimcisi, dogru:Int,  yanlis:Int,  bos:Int,  basari_orani:Int)
    {
        val db = vt.writableDatabase
        val values =ContentValues()
        values.put("dogru",dogru)
        values.put("yanlis",yanlis)
        values.put("bos",bos)
        values.put("basari_orani",basari_orani)
        db.insertOrThrow("oturum",null,values)
        db.close()
    }
    fun verileriGetir(vt:VeritabaniYardimcisi):ArrayList<Oturum>{
        val oturum_liste = ArrayList<Oturum>()
        val db = vt.writableDatabase
        val c = db.rawQuery("Select * From oturum",null)
        while (c.moveToNext())
        {
            val oturum = Oturum(
                oturum_id = c.getInt(c.getColumnIndexOrThrow("oturum_id")),
                dogru = c.getInt(c.getColumnIndexOrThrow("dogru")),
                yanlis = c.getInt(c.getColumnIndexOrThrow("yanlis")),
                bos = c.getInt(c.getColumnIndexOrThrow("bos")),
                basari_orani = c.getInt(c.getColumnIndexOrThrow("basari_orani"))
            )
            oturum_liste.add(oturum)
        }


        return oturum_liste
    }
}