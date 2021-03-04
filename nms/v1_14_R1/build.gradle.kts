plugins {
    id("java")
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(project(":nms:Interfaces"))
    compileOnly(files("../../tmp/spigot-1.14.4.jar"))
}
