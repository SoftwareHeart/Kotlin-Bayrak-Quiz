package com.example.bayrakquiz2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class VeritabaniYardimcisi(context:Context):SQLiteOpenHelper(context,"bayrakquiz.sqlite",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE if not exists bayraklar (\n" +
                "\t`bayrak_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`bayrak_ad`\tTEXT,\n" +
                "\t`bayrak_resim`\tTEXT\n" +
                ");")
       db?.execSQL("CREATE TABLE if not exists oturum (\n" +
               "\t\'oturum_id\'\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
               "\t\'dogru\'\tINTEGER,\n" +
               "\t\'yanlis\'\tINTEGER,\n" +
               "\t\'bos\'\tINTEGER,\n" +
               "\t\'basari_orani\'\tINTEGER)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

        db?.execSQL("DROP TABLE if exists bayraklar ")
        db?.execSQL("DROP TABLE if exists oturum")
        onCreate(db)

    }
}