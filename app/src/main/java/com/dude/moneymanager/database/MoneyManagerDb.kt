package com.dude.moneymanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Exchange::class], version = 4 , exportSchema = false )
abstract class MoneyManagerDb : RoomDatabase(){
    abstract val theDao : TheDao
    companion object {
        @Volatile
        private var INSTANCE : MoneyManagerDb? = null
        fun getInstance(context: Context): MoneyManagerDb{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){

                    instance = Room.databaseBuilder(context.applicationContext,
                        MoneyManagerDb::class.java,
                        "money_manager_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance

                }
                return instance
            }
        }
    }
}