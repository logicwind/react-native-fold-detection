package com.folddetection

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.core.util.Consumer
import androidx.window.java.layout.WindowInfoTrackerCallbackAdapter
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class FoldDetectionModule(private val reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  private val executor: Executor = Executors.newSingleThreadExecutor()
  private var listenerRegistered = false
  private var lastKnownOrientation: String? = null
  private var lastKnownState: String? = null
  private var hingeAngle: Float? = null

  override fun getName(): String = NAME

  // Start listening for folding feature changes
  @ReactMethod
  fun startListening() {
    val activity: Activity = reactContext.currentActivity ?: return
    if (listenerRegistered) return

    val tracker = WindowInfoTracker.getOrCreate(activity)
    val adapter = WindowInfoTrackerCallbackAdapter(tracker)

    val listener = Consumer<WindowLayoutInfo> { layoutInfo ->
      val map = processLayoutInfo(layoutInfo)
      sendEvent("onFoldChange", map)
    }

    adapter.addWindowLayoutInfoListener(activity, executor, listener)
    listenerRegistered = true
  }

  // Stop listening for folding feature changes
  @ReactMethod
  fun stopListening() {
    listenerRegistered = false
  }

  // Get current layout info once
  @ReactMethod
  fun getLayoutInfo(promise: Promise) {
    val activity: Activity = reactContext.currentActivity ?: run {
      promise.reject("NO_ACTIVITY", "No current activity found")
      return
    }

    val tracker = WindowInfoTracker.getOrCreate(activity)
    val adapter = WindowInfoTrackerCallbackAdapter(tracker)

    lateinit var listener: Consumer<WindowLayoutInfo>

    listener = Consumer { layoutInfo ->
      val map = processLayoutInfo(layoutInfo)
      adapter.removeWindowLayoutInfoListener(listener)
      promise.resolve(map)
    }

    adapter.addWindowLayoutInfoListener(activity, executor, listener)
  }

  private fun processLayoutInfo(layoutInfo: WindowLayoutInfo): WritableMap {
    val fold = layoutInfo.displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()
    val map = Arguments.createMap()

    val isHardwareFoldable = isFoldableDevice(reactContext)
    val hasFoldingFeature = fold != null
    val isFoldSupported = isHardwareFoldable || hasFoldingFeature

    val boundsMap = Arguments.createMap()
    var state = "FLAT"
    var orientation = "VERTICAL"
    var occlusionType = "NONE"
    var isSeparating = false

    if (fold != null) {
      state = when (fold.state) {
        FoldingFeature.State.FLAT -> "FLAT"
        FoldingFeature.State.HALF_OPENED -> "HALF_OPENED"
        else -> "HALF_OPENED"
      }

      orientation = when (fold.orientation) {
        FoldingFeature.Orientation.HORIZONTAL -> "HORIZONTAL"
        FoldingFeature.Orientation.VERTICAL -> "VERTICAL"
        else -> "VERTICAL"
      }

      occlusionType = when (fold.occlusionType) {
        FoldingFeature.OcclusionType.NONE -> "NONE"
        FoldingFeature.OcclusionType.FULL -> "FULL"
        else -> "NONE"
      }

      isSeparating = fold.isSeparating

      boundsMap.putInt("top", fold.bounds.top)
      boundsMap.putInt("bottom", fold.bounds.bottom)
      boundsMap.putInt("left", fold.bounds.left)
      boundsMap.putInt("right", fold.bounds.right)

      lastKnownOrientation = orientation
      lastKnownState = state

    } else {
      val fallbackOrientation = lastKnownOrientation ?: "VERTICAL"
      val fallbackState =
        if (isFoldSupported) {
          if (hingeAngle != null && hingeAngle!! < 30) "HALF_OPENED"
          else "HALF_OPENED"
        } else {
          "FLAT"
        }

      state = fallbackState
      orientation = fallbackOrientation

      boundsMap.putInt("top", 0)
      boundsMap.putInt("bottom", 0)
      boundsMap.putInt("left", 0)
      boundsMap.putInt("right", 0)
    }

    val isTableTop = (state == "HALF_OPENED" && orientation == "HORIZONTAL")
    val isBook = (state == "HALF_OPENED" && orientation == "VERTICAL")
    val isFlat = state == "FLAT"

    val layoutInfoMap = Arguments.createMap()
    layoutInfoMap.putString("state", state)
    layoutInfoMap.putString("orientation", orientation)
    layoutInfoMap.putString("occlusionType", occlusionType)
    layoutInfoMap.putBoolean("isSeparating", isSeparating)
    layoutInfoMap.putBoolean("isFoldSupported", isFoldSupported)
    layoutInfoMap.putMap("bounds", boundsMap)

    map.putMap("layoutInfo", layoutInfoMap)
    map.putBoolean("isTableTop", isTableTop)
    map.putBoolean("isBook", isBook)
    map.putBoolean("isFlat", isFlat)

    return map
  }

  private fun sendEvent(eventName: String, params: WritableMap) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }

  private fun isFoldableDevice(context: ReactApplicationContext): Boolean {
    return try {
      val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
      val hingeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HINGE_ANGLE)

      if (hingeSensor != null) {
        val listener = object : SensorEventListener {
          override fun onSensorChanged(event: SensorEvent) {
            hingeAngle = event.values[0]
          }

          override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
        sensorManager.registerListener(listener, hingeSensor, SensorManager.SENSOR_DELAY_NORMAL)
        return true
      }

      val pm = context.packageManager
      val hasSamsungFold = pm.hasSystemFeature("com.samsung.feature.FOLDABLE_DEVICE")
      val hasHuaweiFold = pm.hasSystemFeature("com.huawei.hardware.sensor.posture")

      hasSamsungFold || hasHuaweiFold
    } catch (e: Exception) {
      false
    }
  }

  @ReactMethod
  fun addListener(eventName: String?) {
    // Empty
  }

  @ReactMethod
  fun removeListeners(count: Int) {
    // Empty
  }

  companion object {
    const val NAME = "FoldDetection"
  }
}
