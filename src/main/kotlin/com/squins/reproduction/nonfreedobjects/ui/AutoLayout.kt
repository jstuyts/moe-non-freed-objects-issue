package com.squins.reproduction.nonfreedobjects.ui

import apple.uikit.NSLayoutConstraint
import apple.uikit.NSLayoutConstraint.constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant
import apple.uikit.UIView
import apple.uikit.enums.NSLayoutAttribute.*
import apple.uikit.enums.NSLayoutRelation.*

fun UIView.centerX() {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, CenterX, Equal, this.superview(), CenterX, 1.0, 0.0))
}

fun UIView.centerY() {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, CenterY, Equal, this.superview(), CenterY, 1.0, 0.0))
}

fun UIView.alignTopToParentTop() {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Top, Equal, this.superview(), Top, 1.0, 0.0))
}

fun UIView.alignTopToParentTopWithHeight(height: Int) {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Top, Equal, this.superview(), Top, 1.0, height.toDouble()))
}

fun UIView.alignTopToParentTopWithMinHeight(minHeight: Int) {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Top, GreaterThanOrEqual, this.superview(), Top, 1.0, minHeight.toDouble()))
}

fun UIView.alignTopToBottomOf(other: UIView) {
    alignTopToBottomOf(other, 0)
}

fun UIView.alignTopToBottomOf(other: UIView, margin: Int): NSLayoutConstraint {
    assertHasSuperView()

    val result = constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Top, Equal, other, Bottom, 1.0, margin.toDouble())

    this.superview().addConstraint(result)

    return result
}

fun UIView.alignLeadingToParentLeading() {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Leading, Equal, this.superview(), Leading, 1.0, 0.0))
}

fun UIView.alignLeadingToParentLeadingWithWidth(width: Int) {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Leading, Equal, this.superview(), Leading, 1.0, width.toDouble()))
}

fun UIView.alignLeadingToParentLeadingWithMinWidth(minWidth: Int) {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Leading, GreaterThanOrEqual, this.superview(), Leading, 1.0, minWidth.toDouble()))
}

fun UIView.alignLeadingToTrailingOf(other: UIView) {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Leading, Equal, other, Trailing, 1.0, 0.0))
}

fun UIView.alignLeadingToTrailingOfWithWidth(other: UIView, width: Int) {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Leading, Equal, other, Trailing, 1.0, width.toDouble()))
}

fun UIView.alignTrailingToParentTrailing() {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this.superview(), Trailing, Equal, this, Trailing, 1.0, 0.0))
}

fun UIView.alignTrailingToParentTrailingWithWidth(width: Int) {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this.superview(), Trailing, Equal, this, Trailing, 1.0, width.toDouble()))
}

fun UIView.alignTrailingToParentTrailingWithMinWidth(minWidth: Int) {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this.superview(), Trailing, GreaterThanOrEqual, this, Trailing, 1.0, minWidth.toDouble()))
}

fun UIView.alignBottomToParentBottom() {
    alignBottomToParentBottomWithHeight(0)
}

fun UIView.alignBottomToParentBottomWithHeight(height: Int) {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this.superview(), Bottom, Equal, this, Bottom, 1.0, height.toDouble()))
}

fun UIView.alignBottomToParentBottomWithMinHeight(minHeight: Int) {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this.superview(), Bottom, GreaterThanOrEqual, this, Bottom, 1.0, minHeight.toDouble()))
}

fun UIView.alignBottomToTopOf(other: UIView) {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Bottom, Equal, other, Top, 1.0, 0.0))
}

fun UIView.alignAllSidesToParentSides() {
    alignTopToParentTop()
    alignLeadingToParentLeading()
    alignTrailingToParentTrailing()
    alignBottomToParentBottom()
}

fun UIView.forceToBeSquare() {
    this.addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Width, Equal, this, Height, 1.0, 0.0))
}

// TODO: iOS coordinate system uses Double, refactor to double
fun UIView.width(width: Int, block: NSLayoutConstraint.() -> Unit = {}) {
    removeExistingConstraintIfNeeded(Width)

    this.addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Width, Equal, null, NotAnAttribute, 1.0, width.toDouble()).apply {
        block()
    })
}

// TODO: iOS coordinate system uses Double, refactor to double
fun UIView.maxWidth(width: Int) {
    removeExistingConstraintIfNeeded(Width)

    val widthAsDouble = width.toDouble()
    this.addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Width, Equal, null, NotAnAttribute, 1.0, widthAsDouble).apply { setPriority(999F) })
    this.addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Width, LessThanOrEqual, null, NotAnAttribute, 1.0, widthAsDouble))
}

fun UIView.matchParentWidth() {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Width, Equal, this.superview(), Width, 1.0, 0.0))
}

fun UIView.matchContentViewToScrollViewParentWidth() {
    assertHasSuperView()

    val scrollViewParent = this.superview().superview()
    scrollViewParent.addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Width, Equal, scrollViewParent, Width, 1.0, 0.0))
}

/**
 * @param heightOrWidth  `apple.uikit.enums.NSLayoutAttribute.Height` or `apple.uikit.enums.NSLayoutAttribute.Width`
 */
private fun UIView.removeExistingConstraintIfNeeded(heightOrWidth: Long) {

    constraints().filter { constraint ->
        constraint.firstItem() == this
                && constraint.firstAttribute() == heightOrWidth
                && constraint.relation() == Equal &&
                constraint.multiplier() == 1.0
    }.forEach {
        removeConstraint(it)
    }
}

// TODO: iOS coordinate system uses Double, refactor to double
fun UIView.height(height: Int) {
    removeExistingConstraintIfNeeded(Height)

    this.addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Height, Equal, null, NotAnAttribute, 1.0, height.toDouble()))
}

fun UIView.minHeight(height: Int) {
    removeExistingConstraintIfNeeded(Height)

    this.addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Height, GreaterThanOrEqual, null, NotAnAttribute, 1.0, height.toDouble()))
}

fun UIView.matchParentHeight() {
    assertHasSuperView()

    this.superview().addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Height, Equal, this.superview(), Height, 1.0, 0.0))
}

fun UIView.matchHeightToHeightOfWithDelta(other: UIView, delta: Int, block: NSLayoutConstraint.() -> Unit = {}) {
    assertHasSuperView()

    other.addConstraint(constraintWithItemAttributeRelatedByToItemAttributeMultiplierConstant(this, Height, Equal, other, Height, 1.0, delta.toDouble()).apply {
        block()
    })
}

private fun UIView.assertHasSuperView() {
    checkNotNull(superview(), { "Constraints cannot be added before the view has been added to a super view." })
}
