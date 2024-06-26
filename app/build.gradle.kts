plugins {
    id("com.android.application")
}

android {
    namespace = "il.co.shivhit.androidproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "il.co.shivhit.androidproject"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.google.firebase:firebase-firestore:24.10.3")
    implementation(project(":VIEWMODEL"))
    implementation(project(":MODEL"))
    implementation(project(":HELPER"))
    implementation("androidx.activity:activity:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")}
    // implementation("com.google.firebase:firebase-auth:23.0.0")}
    // implementation("com.google.firebase:platform-bom:23.0.0")}