package net.kartikchawla.tasks

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface Tasks_DAO {
    @Insert
    suspend fun insert(task: Tasks)

    @Update
    suspend fun update(task: Tasks)

    @Delete
    suspend fun delete(task: Tasks)

    @Query("SElECT * FROM tasks_table WHERE taskID = :taskID")
    fun get(taskID: Long): LiveData<Tasks>

    @Query("SElECT * FROM tasks_table ORDER BY taskID DESC")
    fun getAll(): LiveData<List<Tasks>>
}