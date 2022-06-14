package com.eliseubatista.pocketdex.workers

import android.app.Application
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.eliseubatista.pocketdex.database.getDatabase
import com.eliseubatista.pocketdex.repository.PocketdexRepository

class RefreshDataWorker(
    private val appContext: Context,
    params: WorkerParameters
) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = PocketdexRepository(database)

        return try {
            repository.refreshTypes(appContext)
            repository.refreshPokemons(appContext, 5000, 0)

            Result.success()
        } catch (exception: Exception) {
            Result.retry()
        }
    }

}