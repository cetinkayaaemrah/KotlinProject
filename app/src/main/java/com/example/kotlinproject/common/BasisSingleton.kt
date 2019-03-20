package com.example.kotlinproject.common
import android.content.Context
import android.widget.Toast

class BasisSingleton private constructor() {

    internal lateinit var context: Context

    fun init(context: Context) {
        this.context = context.applicationContext
    }

    companion object {
        @Volatile
        private var ourInstance: BasisSingleton? = null
        val instance: BasisSingleton?
            get() {
                if (ourInstance == null) {
                    synchronized(BasisSingleton::class.java) {
                        if (ourInstance == null)
                            ourInstance =
                                BasisSingleton()
                    }
                }
                return ourInstance
            }
    }

    fun toastShort(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    fun toastLong(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}