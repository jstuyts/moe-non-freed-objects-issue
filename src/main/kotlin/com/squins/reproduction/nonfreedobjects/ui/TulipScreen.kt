package com.squins.reproduction.nonfreedobjects.ui

import org.moe.natj.general.Pointer
import org.moe.natj.general.ann.Owned
import org.moe.natj.objc.ObjCRuntime
import org.moe.natj.objc.ann.ObjCClassName
import org.moe.natj.objc.ann.Selector

@org.moe.natj.general.ann.Runtime(ObjCRuntime::class)
@ObjCClassName("TulipScreen")
class TulipScreen protected constructor(peer: Pointer) : AppViewController(peer) {
    override val imageName = "tulip"

    @Selector("init")
    override external fun init(): TulipScreen

    override fun switch() {
        applicationDelegate.switchToViewController(GalaxyScreen.alloc().init().apply { initialize(applicationDelegate) })
    }

    companion object {

        @Owned
        @Selector("alloc")
        @JvmStatic external fun alloc(): TulipScreen
    }
}
