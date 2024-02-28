plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("signing")
}

group = "com.vaticai"
version = "1.0.0"


android {
    namespace = "com.vaticai.postback"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.google.code.gson:gson:2.10")
    implementation("com.google.android.gms:play-services-location:21.1.0")
    implementation("com.google.android.gms:play-services-ads:22.6.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("androidx.ads:ads-identifier:1.0.0-alpha05")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.vaticai"
            artifactId = "postback"
            version = "1.0.1"

            from(components["release"])


            pom {
                name.set("Vatic SDK")
                description.set("Vatic SDK for match back.")
                url.set("https://github.com/reversead/vatic-sdk")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("devzer01")
                        name.set("Nayana Hettiarachchi")
                        email.set("nayanah@vaticai.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/username/mylibrary.git")
                    developerConnection.set("scm:git:ssh://github.com:username/mylibrary.git")
                    url.set("https://github.com/username/mylibrary")
                }
            }

            afterEvaluate {
                from(components["release"])
            }
        }


    }
}
