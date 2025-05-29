package com.michaelflisar.parcelize

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
actual annotation class Parcelize

actual interface Parcelable

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
actual annotation class IgnoredOnParcel




