package com.squins.reproduction.nonfreedobjects.ui

import apple.uikit.*
import apple.uikit.enums.UIInterfaceOrientationMask.Portrait
import apple.uikit.enums.UIInterfaceOrientationMask.PortraitUpsideDown
import apple.uikit.enums.UIViewContentMode
import org.moe.natj.general.Pointer
import org.moe.natj.general.ann.Owned
import org.moe.natj.objc.ann.Selector

class SplashScreen private constructor(peer: Pointer) : UIViewController(peer) {


    @Selector("init")
    external override fun init(): SplashScreen


    override fun loadView() {
        val rootView = UIView.alloc().init().apply {
            setTranslatesAutoresizingMaskIntoConstraints(false)
        }

        addBackgroundImage(rootView)
        addLogoAndSlogan(rootView)

        setView(rootView)
        println("Loaded SplashScreen view")
    }

    private fun addBackgroundImage(container: UIView) {
        val backgroundImage = UIImage.imageNamed("ui/splashScreenBackground")
        val backgroundImageView = UIImageView.alloc().initWithImage(backgroundImage).apply {
            setTranslatesAutoresizingMaskIntoConstraints(false)
            setContentMode(UIViewContentMode.ScaleAspectFill)
        }
        container.addSubview(backgroundImageView)

        backgroundImageView.apply { alignAllSidesToParentSides() }
    }

    private fun addLogoAndSlogan(container: UIView) {
        val logoAndSloganView = UIView.alloc().init().apply {
            setTranslatesAutoresizingMaskIntoConstraints(false)
        }
        container.addSubview(logoAndSloganView)

        val verticallyCenteringLogoView = UIView.alloc().init().apply {
            setTranslatesAutoresizingMaskIntoConstraints(false)

            val logoImage = UIImage.imageNamed("ui/splashScreenLogo-4x")
            val logoView = UIImageView.alloc().initWithImage(logoImage).apply {
                setTranslatesAutoresizingMaskIntoConstraints(false)
                setContentMode(UIViewContentMode.ScaleAspectFit)
            }

            addSubview(logoView)

        }
        logoAndSloganView.addSubview(verticallyCenteringLogoView)

        val youCanTrustLine = createSloganLine(logoAndSloganView, "YOU ", "CAN", " TRUST")
        val weCanDeliverLine = createSloganLine(logoAndSloganView, "WE ", "CAN", " DELIVER")

        logoAndSloganView.apply { alignAllSidesToParentSides() }

        youCanTrustLine.apply {
            centerX()
            alignBottomToTopOf(weCanDeliverLine)
        }
        weCanDeliverLine.apply {
            centerX()
            alignBottomToParentBottomWithMinHeight(scaledValue(40F))
        }
    }

    fun scaledValue(value: Float): Int = (value * 1.0F + 0.5F).toInt()


    private fun createSloganLine(logoAndSloganView: UIView, leftText: String, centerText: String, rightText: String): UIView {
        val result = UIView.alloc().init().apply {
            setTranslatesAutoresizingMaskIntoConstraints(false)
        }
        logoAndSloganView.addSubview(result)

        val leftLabel = addLabel(result, leftText).apply { layOutLeftLabel() }
        val centerLabel = addLabel(result, centerText).apply { layOutCenterLabel(leftLabel) }
        addLabel(result, rightText).apply { layOutRightLabel(centerLabel) }

        return result
    }

    fun addLabel(container: UIView, text: String): UILabel {
        val label = UILabel.alloc().init()
        label.setTranslatesAutoresizingMaskIntoConstraints(false)

        label.setText(text)
        label.setTextColor(UIColor.whiteColor())

        container.addSubview(label)

        return label
    }

    private fun UILabel.layOutLeftLabel() {
        alignLeadingToParentLeading()
        alignTopToParentTop()
        alignBottomToParentBottom()
        centerY()
    }

    private fun UILabel.layOutCenterLabel(labelToTheLeft: UILabel) {
        alignLeadingToTrailingOf(labelToTheLeft)
        alignTopToParentTop()
        alignBottomToParentBottom()
        centerY()
    }

    private fun UILabel.layOutRightLabel(labelToTheLeft: UILabel) {
        alignLeadingToTrailingOf(labelToTheLeft)
        alignTrailingToParentTrailing()
        alignTopToParentTop()
        alignBottomToParentBottom()
        centerY()
    }

    override fun finalize() {
        println("SplashScreen.finalize()")
        println("SplashScreen.finalize()")
        println("SplashScreen.finalize()")
        super.finalize()
    }

    override fun supportedInterfaceOrientations(): Long {
        return Portrait or PortraitUpsideDown
    }

    companion object {
        @Selector("alloc")
        @Owned
        @JvmStatic external fun alloc(): SplashScreen
    }
}
