package com.squins.moe.non.freed.objects.issue

import apple.NSObject
import apple.c.Globals
import apple.foundation.NSDictionary
import apple.foundation.c.Foundation
import apple.uikit.*
import apple.uikit.c.UIKit
import apple.uikit.protocol.UIApplicationDelegate
import com.squins.reproduction.nonfreedobjects.ui.SecondScreenFactory
import com.squins.reproduction.nonfreedobjects.ui.SplashScreen
import org.moe.natj.general.Pointer
import org.moe.natj.objc.ann.Selector
import java.io.File

class MoeNonFreedObjectsApplicationDelegate protected constructor(peer: Pointer) : NSObject(peer), UIApplicationDelegate {
    private lateinit var theWindow: UIWindow

    private var launchAction: (() -> Unit)? = null

    override fun window() = theWindow

    override fun setWindow(value: UIWindow?) {
        throw IllegalStateException()
    }

    override fun applicationDidFinishLaunchingWithOptions(application: UIApplication?, launchOptions: NSDictionary<*, *>?): Boolean {
        theWindow = UIWindow.alloc().initWithFrame(UIScreen.mainScreen().bounds())
        theWindow.setBackgroundColor(UIColor.whiteColor())

        theWindow.setRootViewController(splashScreen())

        theWindow.makeKeyAndVisible()

        // Set "launchAction" from the start-up graph. This launch action will initialize the main graph in the
        // background, and switch to the initial screen (by invoking the start step in the EHT)
        launchAction = { Thread(launchApplicationAndSetSecondScreen()).start() }

        return true
    }

    override fun applicationDidBecomeActive(application: UIApplication?) {
        val currentLaunchAction = launchAction
        launchAction = null
        currentLaunchAction?.invoke()
    }


    private fun launchApplicationAndSetSecondScreen(): Runnable = Runnable {
        println("launchApplicationAndSetSecondScreen(): running")
        try {
            Thread.sleep(3000L);
        } catch(e: Exception) {
            throw RuntimeException(e)
        }

        val secondScreenFactory: SecondScreenFactory = SecondScreenFactory()

        Globals.dispatch_async(Globals.dispatch_get_main_queue(), {
            println("setRootViewController called to set SecondScreen...")

            theWindow.setRootViewController(secondScreenFactory.createScreen())

            println("setRootViewController call completed, SecondScreen active")
        })
    }

    private fun splashScreen(): UIViewController {
        return SplashScreen.alloc().init()
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            try {
                Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
                    writeStackTrace(Thread.currentThread(), throwable)
                }

                UIKit.UIApplicationMain(0, null, null, MoeNonFreedObjectsApplicationDelegate::class.java.name)
            } catch(e: Throwable) {
                writeStackTrace(Thread.currentThread(), e)
                e.printStackTrace(System.out)
                e.printStackTrace(System.out)
                e.printStackTrace(System.out)
                throw e
            }
        }

        private fun writeStackTrace(thread: Thread, throwable: Throwable) {
            File(File(Foundation.NSHomeDirectory(), "Library"), "exception.log").printWriter().use { writer ->
                writer.println(thread)
                throwable.printStackTrace(writer)
            }
        }

        @Selector("alloc")
        @JvmStatic external fun alloc(): MoeNonFreedObjectsApplicationDelegate
    }
}

