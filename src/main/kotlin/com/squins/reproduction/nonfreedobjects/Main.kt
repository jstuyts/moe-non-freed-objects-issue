package com.squins.reproduction.nonfreedobjects

import apple.NSObject
import apple.foundation.NSDictionary
import apple.foundation.NSFileManager
import apple.foundation.enums.NSSearchPathDirectory
import apple.foundation.enums.NSSearchPathDomainMask
import apple.uikit.*
import apple.uikit.c.UIKit
import apple.uikit.protocol.UIApplicationDelegate
import com.squins.reproduction.nonfreedobjects.ui.GalaxyScreen
import dalvik.system.VMDebug

import org.moe.natj.general.Pointer
import org.moe.natj.general.ann.RegisterOnStartup
import org.moe.natj.objc.ann.Selector
import java.io.IOException

@RegisterOnStartup
class Main protected constructor(peer: Pointer) : NSObject(peer), UIApplicationDelegate {

    private var window: UIWindow? = null

    override fun applicationDidFinishLaunchingWithOptions(application: UIApplication?, launchOptions: NSDictionary<*, *>?): Boolean {
        window = UIWindow.alloc().initWithFrame(UIScreen.mainScreen().bounds()).apply {
            setBackgroundColor(UIColor.whiteColor())
            setRootViewController(GalaxyScreen.alloc().init().apply { initialize(this@Main) })
            makeKeyAndVisible()
        }

        return true
    }

    override fun setWindow(value: UIWindow?) {
        window = value
    }

    override fun window(): UIWindow? {
        return window
    }

    fun switchToViewController(viewController: UIViewController) {
        window?.setRootViewController(viewController)
        Thread(Runnable {
            (0..10).forEach { System.gc() }
            val l = NSFileManager.defaultManager().
                    URLsForDirectoryInDomains(
                            NSSearchPathDirectory.DocumentDirectory,
                            NSSearchPathDomainMask.UserDomainMask)
            val docDirURL = l.get(0); // Error handling is for cowards :)
            val fsPath = docDirURL.fileSystemRepresentation()
            try {
                VMDebug.dumpHprofData(fsPath + "/MOE-Dump.hprof")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }).start()
    }

    companion object {

        @JvmStatic fun main(args: Array<String>) {
            UIKit.UIApplicationMain(0, null, null, Main::class.java.getName())
        }

        @Selector("alloc")
        @JvmStatic external fun alloc(): Main
    }
}
