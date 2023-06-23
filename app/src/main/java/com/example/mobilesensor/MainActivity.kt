package com.example.mobilesensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    lateinit var lightSensor: Sensor
    lateinit var proximitySensor: Sensor
    var sensorValues: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        findViewById<Button>(R.id.sensorResults).setOnClickListener {
            sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL)
            sensorManager.registerListener(this,proximitySensor,SensorManager.SENSOR_DELAY_NORMAL)
        }
        findViewById<Button>(R.id.stopSensor).setOnClickListener {
            sensorManager.unregisterListener(this)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
//        TODO("Not yet implemented")
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        val lightValue: Float
        val proximityValue: Float
        if(sensorEvent!!.sensor == lightSensor){
            lightValue = sensorEvent.values[0]
            sensorValues = "Light Sensor Value: $lightValue lux"
            findViewById<TextView>(R.id.textView1).text = sensorValues
            Log.d("TESTING","Light Sensor Value: $lightValue lux")
        }
        if(sensorEvent.sensor == proximitySensor){
            proximityValue = sensorEvent.values[0]
            sensorValues = "Proximity Sensor Value: $proximityValue cm"
            findViewById<TextView>(R.id.textView2).text = sensorValues
            Log.d("TESTING","Proximity Sensor Value: $proximityValue cm")
        }
    }
}