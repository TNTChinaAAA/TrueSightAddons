import org.gradle.kotlin.dsl.implementation

plugins {
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
}

val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")

group = "net.tntchina"
version = "1.1.6"

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

labyMod {
    defaultPackageName = "net.labymod.addons.truesight" //change this to your main package name (used by all modules)
    
    minecraft {
        registerVersion(versions.toTypedArray()) {
            runs {
                getByName("client") {
                    // When the property is set to true, you can log in with a Minecraft account
                    devLogin = true
                }
            }

            val file = file("./game-runner/src/${this.sourceSetName}/resources/truesight-${versionId}.accesswidener");
            accessWidener.set(file)
        }
    }

    addonInfo {
        namespace = "truesight"
        displayName = "TrueSight"
        author = "TNTChina"
        description = "A mod for true-sight"
        minecraftVersion = "1.8.9"
        version = rootProject.version.toString()
    }
}

subprojects {
    plugins.apply("net.labymod.labygradle")
    plugins.apply("net.labymod.labygradle.addon")

    group = rootProject.group
    version = rootProject.version
}

/*
dependencies {
    implementation("com.google.guava:guava:33.5.0-jre")
}*/