plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "vm.caatsoft.simplelogin"
    compileSdk = 35

    defaultConfig {
        applicationId = "vm.caatsoft.simplelogin"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"

        }
    }
}

val versions = mapOf(
    "coreKtx" to "1.15.0",
    "lifecycleRuntime" to "2.8.7",
    "activityCompose" to "1.9.3",
    "composeUi" to "1.7.5",
    "material3" to "1.3.1",
    "koin" to "3.5.0",
    "retrofit" to "2.9.0",
    "coroutines" to "1.9.0",
    "navigationCompose" to "2.8.4",
    "coil" to "3.0.3",
    "mockito" to "5.1.1",
    "mockitoKotlin" to "4.0.0",
    "kotlinTest" to "1.6.10",
    "junit4" to "4.13.2",
    "mockk" to "1.13.3",
    "junit5" to "5.8.2",
    "kluent" to "1.68",
    "archCoreTesting" to "2.2.0",
    "androidxJunit" to "1.2.1",
    "espressoCore" to "3.6.1",
    "composeBom" to "2023.03.00"
)

dependencies {
    implementation("androidx.core:core-ktx:${versions["coreKtx"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${versions["lifecycleRuntime"]}")
    implementation("androidx.activity:activity-compose:${versions["activityCompose"]}")
    implementation("androidx.compose.ui:ui:${versions["composeUi"]}")
    implementation("androidx.compose.ui:ui-graphics:${versions["composeUi"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${versions["composeUi"]}")
    implementation("androidx.compose.material3:material3:${versions["material3"]}")
    implementation("io.insert-koin:koin-android:${versions["koin"]}")
    implementation("io.insert-koin:koin-androidx-compose:3.4.0")
    implementation("io.insert-koin:koin-androidx-workmanager:3.4.0")
    implementation("com.squareup.retrofit2:retrofit:${versions["retrofit"]}")
    implementation("com.squareup.retrofit2:converter-gson:${versions["retrofit"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${versions["coroutines"]}")
    implementation("androidx.navigation:navigation-compose:${versions["navigationCompose"]}")
    implementation("io.coil-kt.coil3:coil-compose:${versions["coil"]}")
    implementation("io.coil-kt.coil3:coil-network-okhttp:${versions["coil"]}")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${versions["coroutines"]}")
    testImplementation("org.mockito:mockito-core:${versions["mockito"]}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${versions["mockitoKotlin"]}")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${versions["kotlinTest"]}")
    testImplementation("junit:junit:${versions["junit4"]}")
    testImplementation("io.mockk:mockk:${versions["mockk"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${versions["junit5"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${versions["junit5"]}")
    testImplementation("org.amshove.kluent:kluent:${versions["kluent"]}")
    testImplementation("androidx.arch.core:core-testing:${versions["archCoreTesting"]}")

    androidTestImplementation("androidx.test.ext:junit:${versions["androidxJunit"]}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${versions["espressoCore"]}")
    androidTestImplementation(platform("androidx.compose:compose-bom:${versions["composeBom"]}"))
    debugImplementation("androidx.compose.ui:ui-tooling:${versions["composeUi"]}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${versions["composeUi"]}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${versions["composeUi"]}")
    androidTestImplementation("androidx.test:core:${versions["androidxJunit"]}")
}