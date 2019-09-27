package com.fivestars.fivestarsadbhelper.util

import android.util.Log
import java.io.*


object AdbUtil {

    private const val LOG_TAG = "AdbUtil"

    val libs = "LD_LIBRARY_PATH=/vendor/lib64:/system/lib64:/vendor/lib:/system/lib "

    @Throws(IOException::class, InterruptedException::class)
    private fun executeAsRoot(vararg commands: String): Int {
        // Do the magic
        val p = Runtime.getRuntime().exec("su")
        val es = p.errorStream
        val os = DataOutputStream(p.outputStream)

        try {
            for (command in commands) {
                os.writeBytes(command + "\n")
            }
            os.writeBytes("exit\n")
            os.flush()
        } finally {
            os.close()
        }

        var line: String? = null
        // we typically don't get any error output here so instantiating a default-sized StringBuilder
        val outputStringBuilder = StringBuilder()
        val errorReader = BufferedReader(InputStreamReader(es))
        line = errorReader.readLine()
        while (line != null) {
            outputStringBuilder.append(line)
            line = errorReader.readLine()
        }
        val output = outputStringBuilder.toString()
        p.waitFor()
        Log.e(LOG_TAG, output.trim { it <= ' ' } + " (" + p.exitValue() + ")")
        return p.exitValue()
    }

    fun setBatteryLevel(batteryLevel: Int) {


            // Install the new apk over the old apk via "pm install -r"
            val command =
                libs + "dumpsys battery set level $batteryLevel"
            Log.d(LOG_TAG, "Executing command 1: $command")
            val result = executeAsRoot(command)
            if (result != 0) {
                Log.e(LOG_TAG, "Could not execute command $command as root")
            }
    }
}