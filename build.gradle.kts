import kr.entree.spigradle.kotlin.paper
import kr.entree.spigradle.kotlin.papermc

// declare plugins
plugins {
    kotlin("jvm") version "1.4.31"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    // useful plugin for quickly making plugins
    id("kr.entree.spigradle") version "2.2.3"
}

// define repos to fetch dependencies from
repositories {
    mavenCentral()
    // paper repository
    papermc()

    // repositories for command api
    maven { url = uri("https://raw.githubusercontent.com/JorelAli/CommandAPI/mvn-repo/") }
    maven { url = uri("https://repo.codemc.org/repository/maven-public/") }
}

// module information
version = "1.0.1"
group = "me.yiffed.veinminer"

// define the actual dependencies
dependencies {
    // compileOnly - don't include in the final jar
    // implementation - include in the final jar

    // want to include the kotlin stdlib - doesn't work without!
    implementation(kotlin("stdlib"))

    // papermc
    compileOnly(paper("1.16.5-R0.1-SNAPSHOT"))

    // plugin specific deps - command api
    compileOnly("dev.jorel", "commandapi-core", "5.8")
    implementation("dev.jorel", "commandapi-shade", "5.8")
}

tasks {
    // shades plugin dependencies into the plugin jar so we can use them
    // at runtime.
    shadowJar {
        archiveClassifier.set("")
    }

    build {
        // ensure we shade in dependencies at runtime
        dependsOn("shadowJar")
    }

    spigot {
        name = "VeinMiner"
        authors = listOf("Nanofaux", "SkyezerFox")
        apiVersion = "1.16"
    }
}
