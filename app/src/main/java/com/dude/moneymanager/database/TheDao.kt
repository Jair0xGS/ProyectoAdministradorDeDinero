package com.dude.moneymanager.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

//the thing that is used to get the data from the table
@Dao
interface TheDao {
    @Insert
    fun insert(exchange: Exchange)
    @Update
    fun update(exchange: Exchange)
    @Query("SELECT *  FROM Exchange WHERE exId = :key")
    fun get(key: Long):Exchange?
    @Query("SELECT * FROM Exchange ORDER BY exId DESC")
    fun getAllExchanges(): LiveData<List<Exchange>>
    @Query("SELECT * FROM Exchange ORDER BY exId DESC LIMIT 1")
    fun getFistExchange(): Exchange?
}