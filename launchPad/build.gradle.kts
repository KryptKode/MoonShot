apply(from = "../ktlint.gradle")

plugins {
    id("com.android.dynamic-feature")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(ProjectProperties.compileSdk)

    defaultConfig {
        minSdkVersion(ProjectProperties.minSdk)
        targetSdkVersion(ProjectProperties.targetSdk)
        versionCode = ProjectProperties.versionCode
        versionName = ProjectProperties.versionName
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dataBinding {
        isEnabled = true
    }
    kapt {
        correctErrorTypes = true
    }
    buildTypes {
        all {
            if (project.hasProperty("MAPQUEST_API_KEY")) {
                val MAPQUEST_API_KEY: String by project
                buildConfigField("String", "MapQuestApiKey", MAPQUEST_API_KEY)
            } else {
                buildConfigField("String", "MapQuestApiKey", "\"mock-key\"")
            }
        }
    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":app"))
    implementation(project(":base"))
    implementation(project(":core"))
    implementation(project(":models"))
    implementation(project(":moonshot-repository"))

    implementation(Libs.kotlinStdLib)
    implementation(Libs.coroutines)
    implementation(Libs.coroutinesAndroid)

    implementation(Libs.vector)

    implementation(Libs.appCompat)
    implementation(Libs.lifecycle)
    implementation(Libs.materialComponents)
    implementation(Libs.ktxCore)
    implementation(Libs.constraintLayout)
    implementation(Libs.navigation)
    implementation(Libs.navigationUi)
    implementation(Libs.preference)

    implementation(Libs.koinCore)
    implementation(Libs.koinAndroid)
    implementation(Libs.koinViewModel)

    implementation(Libs.epoxy)
    implementation(Libs.epoxyDatabinding)
    kapt(Libs.epoxyProcessor)

    implementation(Libs.glide)
    kapt(Libs.glideCompiler)

    testImplementation(Libs.junit4)
    androidTestImplementation(Libs.androidxJunitExt)
    androidTestImplementation(Libs.espressoCore)
}