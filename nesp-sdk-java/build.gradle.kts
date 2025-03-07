/*
 * Copyright (C) 2021 The NESP Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("java")
}


group = "com.nesp.sdk.java"
version = "1.0-alpha"

tasks.withType(JavaCompile::class.java) {
    options.encoding = "UTF-8"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("com.alibaba:fastjson:1.2.79")
    implementation("org.json:json:20211205")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")

    // org.apache.commons
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("org.apache.commons:commons-text:1.9")
    implementation("org.apache.commons:commons-compress:1.21")
    // implementation("org.apache.commons:commons-math3:3.6.1")
    // implementation("org.apache.commons:commons-pool2:2.11.1")
    implementation("org.apache.commons:commons-io:1.3.2")
    // implementation("org.apache.commons:commons-csv:1.9.0")
    // implementation("org.apache.commons:commons-configuration2:2.7")
    // implementation("org.apache.commons:commons-exec:1.3")
    implementation("org.apache.commons:commons-crypto:1.1.0")
    // implementation("org.apache.commons:commons-jexl3:3.2.1")
    // implementation("org.apache.commons:commons-vfs2:2.9.0")
    // implementation("org.apache.commons:commons-email:1.5")
    implementation("commons-codec:commons-codec:1.16.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")

}

tasks.withType(Test::class.java) {
    useJUnitPlatform()
}

val isAndroid = rootProject.subprojects.find { it.extensions.findByName("android") != null } != null
val javaVersion = if (isAndroid) {
    JavaVersion.VERSION_1_8
} else {
    JavaVersion.VERSION_17
}

println("nesp-sdk-java: set java version to $javaVersion")

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

sourceSets {
    main {
        java {
            exclude("module-info.java")
        }
    }

    create("mainModule") {
        java {
            srcDirs("src/main/java", "src/mainModule/java")
            output.dir("${layout.buildDirectory.asFile.get().path}/classes/java/main")
            destinationDirectory.set(file("${layout.buildDirectory.asFile.get().path}/classes/java/main"))
            include("module-info.java")
        }
    }
}

// sourceSets {
//     main {
//         java {
//             exclude 'module-info.java'
//         }
//     }
//     mainModule { // module-info only
//         java {
//             srcDirs = ['src/main/java', 'src/mainModule/java']
//             if (java.hasProperty('outputDir'))
//                 outputDir = file("$buildDir/classes/java/main")
//             if (java.hasProperty('destinationDir'))
//                 destinationDirectory.set(file("$buildDir/classes/java/main"))
//             include 'module-info.java'
//         }
//     }
// }
//
// if (JavaVersion.toVersion(sourceCompatibility) == JavaVersion.VERSION_17) {
//     compileJava.options.compilerArgs.addAll(['--release', '17'])
// } else {
// //    classes.dependsOn mainModuleClasses
//     compileMainModuleJava.options.compilerArgs.addAll(['--release', '16'])
// }
//
