package com.example.a2zapplication.repository.requestAccess

import com.google.android.gms.tasks.Task

interface BaseDbAccess {
    suspend fun setRequestForAccess(accessValue : HashMap<String,Boolean>) : Task<Void>?
}