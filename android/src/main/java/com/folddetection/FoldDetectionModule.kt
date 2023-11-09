package com.folddetection

import android.content.pm.PackageManager
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import androidx.core.util.Consumer
import androidx.window.java.layout.WindowInfoTrackerCallbackAdapter
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.facebook.react.bridge.Promise
import java.util.concurrent.Executors

class FoldDetectionModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  private var windowInfoTracker: WindowInfoTrackerCallbackAdapter = WindowInfoTrackerCallbackAdapter(WindowInfoTracker.getOrCreate(reactContext))
  private val layoutStateChangeCallback = LayoutStateChangeCallback()

  override fun getName(): String {
    return "FoldingFeature"
  }

  @ReactMethod
  fun isFoldSupported(promise: Promise) {
    val packageManager = reactApplicationContext.packageManager
    val featureSupported = packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_HINGE_ANGLE)
    promise.resolve(featureSupported)
  }

  @ReactMethod
  fun startListening() {
    windowInfoTracker.addWindowLayoutInfoListener(currentActivity!!, Executors.newSingleThreadExecutor(), layoutStateChangeCallback)
  }

  @ReactMethod
  fun stopListening() {
    windowInfoTracker.removeWindowLayoutInfoListener(layoutStateChangeCallback)
  }

  inner class LayoutStateChangeCallback : Consumer<WindowLayoutInfo> {
    override fun accept(newLayoutInfo: WindowLayoutInfo) {
      val event: WritableMap = Arguments.createMap()

      try {
        val displayFeaturesList = newLayoutInfo.displayFeatures

        if (displayFeaturesList.isNotEmpty()) {
          val feature = displayFeaturesList[0] // Assuming there's only one feature

          val featureObject = Arguments.createMap()

          if (feature is FoldingFeature) {
            val foldingFeature = feature as FoldingFeature
            featureObject.putString("State", foldingFeature.state.toString())
            featureObject.putString("Orientation", foldingFeature.orientation.toString())
            featureObject.putBoolean("IsSeparating", foldingFeature.isSeparating)
            featureObject.putString("OcclusionType", foldingFeature.occlusionType.toString())

            // Parse and include detailed bounds information
            val bounds = parseBoundsString(foldingFeature.bounds.toString())
            featureObject.putMap("Bounds", bounds)
          }

          event.putMap("displayFeatures", featureObject)
        }
      } catch (e: Exception) {
        event.putString("displayFeatures", "Error parsing displayFeatures")
      }

      sendEvent(reactApplicationContext, "onLayoutInfoChange", event)
    }

    private fun parseBoundsString(boundsString: String): WritableMap {
      val bounds = Arguments.createMap()
      val regex = Regex("Rect\\((\\d+), (\\d+) - (\\d+), (\\d+)\\)")
      val matchResult = regex.find(boundsString)

      if (matchResult != null && matchResult.groupValues.size == 5) {
        val left = matchResult.groupValues[1].toInt()
        val top = matchResult.groupValues[2].toInt()
        val right = matchResult.groupValues[3].toInt()
        val bottom = matchResult.groupValues[4].toInt()

        bounds.putInt("left", left)
        bounds.putInt("top", top)
        bounds.putInt("right", right)
        bounds.putInt("bottom", bottom)
      }

      return bounds
    }
  }
  private fun sendEvent(reactContext: ReactApplicationContext, eventName: String, params: WritableMap) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }
}
