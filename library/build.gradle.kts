import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gradle.maven.publish.plugin)
}

// -------------------
// Informations
// -------------------

val description = "kmp parcelize - use parcelize inside common kmp code"

// Module
val artifactId = "library"
val androidNamespace = "com.michaelflisar.parcelize"

// Library 
val libraryName = "kmp-parcelize"
val libraryDescription = "KMP Parcelize - $artifactId module - $description"
val groupID = "io.github.mflisar.parcelize"
val release = 2016
val github = "https://github.com/MFlisar/kmp-parcelize"
val license = "Apache License 2.0"
val licenseUrl = "$github/blob/main/LICENSE"

// -------------------
// Variables for Documentation Generator
// -------------------

// # DEP + GROUP are optional arrays!

// OPTIONAL = "false"               // defines if this module is optional or not
// GROUP_ID = "core"                // defines the "grouping" in the documentation this module belongs to
// #DEP = "deps.composables.core|Compose Unstyled (core)|https://github.com/composablehorizons/compose-unstyled/"
// PLATFORM_INFO = ""               // defines a comment that will be shown in the documentation for this modules platform support

// GLOBAL DATA
// BRANCH = "master"        // defines the branch on github (master/main)
// GROUP = "core|Core|core"

// -------------------
// Setup
// -------------------

kotlin {

    // Java
    jvm()

    // Android
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            freeCompilerArgs.addAll("-P", "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=com.michaelflisar.parcelize.Parcelize")
        }
    }

    // iOS
    macosX64()
    macosArm64()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    // web
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        nodejs()
    }

    // javascript
    js(IR) {
        nodejs {}
        browser {}
    }

    // -------
    // Sources
    // -------

    kotlin {

        compilerOptions {
            freeCompilerArgs.add("-Xexpect-actual-classes")
        }

        sourceSets {

            val commonMain by getting {
                dependencies {

                }
            }

            val androidMain by getting {
                dependsOn(commonMain)
            }

            val nonAndroidMain by creating {
                dependsOn(commonMain)
            }

            val jvmMain by getting {
                dependsOn(nonAndroidMain)
            }

            val jsMain by getting {
                dependsOn(nonAndroidMain)
            }

            val wasmJsMain by getting {
                dependsOn(nonAndroidMain)
            }

            val iosArm64Main by getting {
                dependsOn(nonAndroidMain)
            }

            val iosX64Main by getting {
                dependsOn(nonAndroidMain)
            }

            val iosSimulatorArm64Main by getting {
                dependsOn(nonAndroidMain)
            }

            val macosArm64Main by getting {
                dependsOn(nonAndroidMain)
            }

            val macosX64Main by getting {
                dependsOn(nonAndroidMain)
            }
        }
    }
}

android {
    namespace = androidNamespace

    compileSdk = app.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = app.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

mavenPublishing {

    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Dokka("dokkaHtml"),
            sourcesJar = true
        )
    )

    coordinates(
        groupId = groupID,
        artifactId = artifactId,
        version = System.getenv("TAG")
    )

    pom {
        name.set(libraryName)
        description.set(libraryDescription)
        inceptionYear.set("$release")
        url.set(github)

        licenses {
            license {
                name.set(license)
                url.set(licenseUrl)
            }
        }

        developers {
            developer {
                id.set("mflisar")
                name.set("Michael Flisar")
                email.set("mflisar.development@gmail.com")
            }
        }

        scm {
            url.set(github)
        }
    }

    // Configure publishing to Maven Central
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, true)

    // Enable GPG signing for all publications
    signAllPublications()
}