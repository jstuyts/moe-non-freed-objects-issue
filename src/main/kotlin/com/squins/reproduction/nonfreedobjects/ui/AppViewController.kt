package com.squins.reproduction.nonfreedobjects.ui

import apple.c.Globals
import apple.c.Globals.dispatch_async
import apple.c.Globals.dispatch_get_main_queue
import apple.coregraphics.c.CoreGraphics.*
import apple.coregraphics.enums.CGInterpolationQuality.High
import apple.uikit.*
import apple.uikit.NSLayoutConstraint.constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant
import apple.uikit.UIColor.blueColor
import apple.uikit.UIImage.imageNamed
import apple.uikit.enums.NSLayoutAttribute.*
import apple.uikit.enums.NSLayoutRelation.Equal
import apple.uikit.enums.UIButtonType.Custom
import apple.uikit.enums.UIControlEvents
import apple.uikit.enums.UIControlEvents.TouchUpInside
import apple.uikit.enums.UIControlState.Normal
import apple.uikit.enums.UIViewContentMode.Center
import com.squins.reproduction.nonfreedobjects.Main
import org.moe.natj.general.Pointer
import org.moe.natj.general.ann.Owned
import org.moe.natj.objc.ObjCRuntime
import org.moe.natj.objc.ann.ObjCClassName
import org.moe.natj.objc.ann.Selector

@org.moe.natj.general.ann.Runtime(ObjCRuntime::class)
@ObjCClassName("AppViewController")
abstract class AppViewController protected constructor(peer: Pointer) : UIViewController(peer) {
    protected abstract val imageName: String
    protected lateinit var applicationDelegate: Main

    @Selector("init")
    override external fun init(): AppViewController

    protected abstract fun switch()

    fun initialize(applicationDelegate: Main) {
        this.applicationDelegate = applicationDelegate
    }

    override fun loadView() {
        val rootView = UIView.alloc().init()
        rootView.setTranslatesAutoresizingMaskIntoConstraints(false)

        0.until(25).forEach { imageViewIndex ->
            val imageView = createImageView()
            rootView.addSubview(imageView)
            imageView.apply {
                superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Top, Equal, superview(), Top, 1.0, imageViewIndex * 10.0))
                superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Leading, Equal, superview(), Leading, 1.0, imageViewIndex * 10.0))
                addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Width, Equal, null, NotAnAttribute, 1.0, 100.0))
                addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Height, Equal, null, NotAnAttribute, 1.0, 100.0))
            }
        }

        val button = createButton()
        rootView.addSubview(button)
        button.apply {
            superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, CenterX, Equal, superview(), CenterX, 1.0, 0.0))
            superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(superview(), Bottom, Equal, this, Bottom, 1.0, 0.0))
        }

        setView(rootView)
        Thread(Runnable {
            Thread.sleep(1000)
            dispatch_async(dispatch_get_main_queue(), { switch() })
        }).start()
    }

    private fun createImageView(): UIImageView {
        return UIImageView.alloc().initWithImage(resize(imageNamed("ui/$imageName"))).apply {
            setTranslatesAutoresizingMaskIntoConstraints(false)

            setContentMode(Center)
            setClipsToBounds(true)
        }
    }

    private fun resize(image: UIImage): UIImage {
        val result: UIImage

        val originalImageRef = image.CGImage()
        val bitmap = CGBitmapContextCreate(null, IMAGE_TARGET_SIZE, IMAGE_TARGET_SIZE, CGImageGetBitsPerComponent(originalImageRef), 0, CGImageGetColorSpace(originalImageRef), CGImageGetBitmapInfo(originalImageRef)) ?: throw IllegalStateException("Could not create the target CGBitmap for the scaled image.")
        try {
            CGContextSetInterpolationQuality(bitmap, High)
            CGContextDrawImage(bitmap, CGRectMake(0.0, 0.0, IMAGE_TARGET_SIZE_AS_DOUBLE, IMAGE_TARGET_SIZE_AS_DOUBLE), originalImageRef)
            val scaledImageRef = CGBitmapContextCreateImage(bitmap) ?: throw IllegalStateException("Could not create the target CGImage for the scaled image.")
            try {
                result = UIImage.alloc().initWithCGImage(scaledImageRef)
            } finally {
                CGImageRelease(scaledImageRef)
            }
        } finally {
            CGContextRelease(bitmap)
        }

        return result
    }

    private fun createButton() =
            UIButton.buttonWithType(Custom).apply {
                setTranslatesAutoresizingMaskIntoConstraints(false)

                setTitleForState("Switch", Normal)
                setTitleColorForState(blueColor(), Normal)
                addTargetActionForControlEvents<UIButton>({ source, eventId -> switch() }, TouchUpInside)
            }

    override fun viewDidDisappear(animated: Boolean) {
        super.viewDidDisappear(animated)
        removeSubViewsIfViewAvailable()
    }

    private fun removeSubViewsIfViewAvailable() {
        view()?.let { view -> removeSubViews(view) }
    }

    private fun removeSubViews(view: UIView) {
        view.subviews().forEach { subView ->
            removeSubViews(subView)
            (subView as? UIControl)?.removeTargetActionForControlEvents(null, null, UIControlEvents.AllEvents)
            (subView as? UIImageView)?.let { imageView ->
                imageView.setImage(null)
                imageView.setHighlightedImage(null)
            }
            subView.removeFromSuperview()
        }
    }

    companion object {
        private val IMAGE_TARGET_SIZE = 400L
        private val IMAGE_TARGET_SIZE_AS_DOUBLE = IMAGE_TARGET_SIZE.toDouble()

        @Owned
        @Selector("alloc")
        @JvmStatic external fun alloc(): AppViewController
    }
}
