package com.onedream.onenet

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.onedream.onenet.ui.theme.OneNetTheme

class MainActivity : ComponentActivity() {
    private lateinit var mViewModel: MainViewModel
    private val mMessage = mutableStateOf("")
    private val mLoading = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OneNetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        PlayBugButton(btnTitle = "requestTestGet", onClick = {
                            requestTestGet()
                        })

                        Spacer(modifier = Modifier.height(20.dp))
                        //
                        if(mLoading.value){
                            Greeting("加载中...")
                        }else{
                            Greeting(mMessage.value)
                        }
                    }
                }
            }
        }
        //
        initViewModel()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //
        mViewModel.launchErrorResp.observe(this){
            showLoading(false)
            Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            mMessage.value = it
        }
        //
        mViewModel.testGetSuccess.observe(this) {
            showLoading(false)
            Toast.makeText(this@MainActivity, it.second, Toast.LENGTH_SHORT).show()
            mMessage.value = it.first.submit_content + "=====" + it.first.current_timer
        }
    }

    private fun requestTestGet() {
        showLoading(true)
        mViewModel.requestTestGet("我是提交内容")
    }

    private fun showLoading(isLoading : Boolean){
        mLoading.value = isLoading
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun PlayBugButton(btnTitle: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth(1.0f)
    ) {
        Text(
            text = btnTitle
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OneNetTheme {
        Greeting("Android")
    }
}