repositories {
    maven("https://maven.minecraftforge.net/")
}

dependencies {
    compileOnly(rootProject)
    compileOnly("net.minecraftforge:forge:1.16.4-35.1.37:universal")
}

tasks.withType<Jar> {
    from(rootProject.tasks.named<Jar>("jar").get().archiveFile) {
        rename { "META-INF/jars/Felt-ASM-${project.version}.jar" }
    }
    from(rootProject.file("LICENSE")) {
        rename { "${it}_FeltASM" }
    }
    manifest.attributes(
            "MixinConfigs" to "feltasm.init.mixins.json",
            "FMLModType" to "GAMELIBRARY",
    )
}

tasks.withType<ProcessResources> {
    inputs.property("version", version)

    filesMatching(listOf("META-INF/mods.toml", "META-INF/jarjar/metadata.json")) {
        expand("version" to version)
    }
}