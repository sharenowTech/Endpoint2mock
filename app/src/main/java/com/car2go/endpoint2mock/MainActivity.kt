package com.car2go.endpoint2mock

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit.RestAdapter
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var githubApi: GithubApi

    private var subscription: Subscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        githubApi = RestAdapter.Builder()
                .setClient(
                        MockableClient.build("http://${BuildConfig.BUILD_HOST_ADDRESS}")   // Assuming that you have something running at this URL
                                .mockWhen { mockToggle.isChecked }
                                .build()
                )
                .setEndpoint("https://api.github.com")
                .build()
                .create(GithubApi::class.java)

        requestButton.setOnClickListener {
            subscription?.unsubscribe()

            subscription = githubApi.getCompanyRepositories()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { repositories ->
                                result.text = repositories.toString()
                            },
                            { result.text = it.toString() }
                    )
        }
    }

    override fun onPause() {
        super.onPause()

        subscription?.unsubscribe()
    }

}
