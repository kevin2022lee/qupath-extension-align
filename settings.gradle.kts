pluginManagement {
	repositories {
		maven { url = uri("https://maven.aliyuncs.com/repository/public") }
		maven { url = uri("https://maven.scijava.org/content/repositories/releases") }
		gradlePluginPortal()
	}
}

qupath {
	version = "0.7.0"
}

dependencyResolutionManagement {
	repositories {
		maven { url = uri("https://maven.aliyuncs.com/repository/public") }
		maven { url = uri("https://maven.scijava.org/content/repositories/releases") }
	}
}

// Apply QuPath Gradle settings plugin to handle configuration
plugins {
	id("io.github.qupath.qupath-extension-settings") version "0.2.1"
}
