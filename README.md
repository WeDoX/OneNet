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

Step 3. Customize your OneNetCommonConfig(eg:[HttpConfig](https://github.com/WeDoX/OneNet/blob/master/app/src/main/java/com/onedream/onenet/http_demo/config/HttpConfig.kt)) And init in Application

~~~~~~~
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //
        OneNet.init(HttpConfig(this.applicationContext))
    }
}
~~~~~~~

Step 4. Code your ApiService and HttpDataRepository
~~~~~~~
interface ApiService {
    @GET("/other/one_net_test.php")
    suspend fun testGet(@Query("submit_content") submit_content:String): BaseResp<OneNetTestGetResp>
}

object HttpDataRepository {

    private val apiService: ApiService by lazy {
        RetrofitFactory.create(
            ApiService::class.java
        )
    }

    suspend fun testGet(submit_content: String): BaseResp<OneNetTestGetResp> {
        return apiService.testGet(submit_content)
    }
}
~~~~~~~


Step 5. Customize your BaseViewModel And Use
~~~~~~~
class MainViewModel : BaseViewModel() {
    private val _testGetSuccess = MutableEventLiveData<Pair<OneNetTestGetResp, String>>()
    val testGetSuccess: EventLiveData<Pair<OneNetTestGetResp, String>>
        get() = _testGetSuccess

    fun requestTestGet(submit_content: String) {
        launchAsyncDataAndMsg(block = {
            HttpDataRepository.testGet(submit_content)
        }, success = {
            _testGetSuccess.postValue(it)
        }, error = {
            sendLaunchErrorResp(it.message ?: "")
        })
    }

}
~~~~~~~
~~~~~~~
mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
mViewModel.launchErrorResp.observe(this){
}
mViewModel.testGetSuccess.observe(this) {
}
~~~~~~~


