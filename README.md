[![](https://jitpack.io/v/WeDoX/OneNet.svg)](https://jitpack.io/#WeDoX/OneNet)

Step 1. Add it in your root settings.gradle at the end of repositories:
~~~~~~~
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
~~~~~~~
Step 2. Add the dependency
~~~~~~~
	dependencies {
	        implementation 'com.github.WeDoX:OneNet:1.3.6'
	}
~~~~~~~
