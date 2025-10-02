package com.michaelflisar.parcelize

actual interface Parcelable

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
actual annotation class Parcelize

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
actual annotation class IgnoredOnParcel

@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.BINARY)
actual annotation class RawValue




