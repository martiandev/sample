package team.standalone.core.ui.viewmodel

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.usecase.GetLocaleUseCase
import team.standalone.core.data.domain.usecase.GetUserUseCase
import team.standalone.core.data.domain.usecase.firebase.GetTokenUseCase

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseWebViewModel<UiEvent> (
    private val getLocaleUseCase: GetLocaleUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    getUserUseCase: GetUserUseCase
): BaseViewModel<UiEvent>() {

    private val _idTokenResult = Channel<LoadResult<String>>()
    val idTokenResult = _idTokenResult.receiveAsFlow()

    val progress: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    var token: String = ""

    /**
     * Get user information.
     * */
    val uiState = getUserUseCase(shouldFetch = true)
        .mapLatest { result ->
            UserUiState(result)
        }.stateIn(
            scope = viewModelScope,
            started = whileSubscribed,
            initialValue = UserUiState(LoadResult.Loading())
        )

    /**
     * Call getIdTokenTask on view model initialization.
     * */
    init {
        getIdTokenTask()
    }

    /**
     * Get IdToken.
     * */
    suspend fun getIdToken(): String {
        val result = getTokenUseCase().asLoadResult()
        result.data?.let { return it }
        return ""
    }

    /**
     * Get IdToken via task.
     * */
    fun getIdTokenTask() = runUseCase {
        _idTokenResult.trySend(LoadResult.Loading())
        _idTokenResult.trySend(
            when (val result = getTokenUseCase()) {
                is Result.Success -> saveToken(LoadResult.Success(result.data))
                is Result.Error -> LoadResult.Error(
                    result.exception.message,
                    result.exception
                )
            }
        )
    }

    /**
     * Get locale query param.
     * */
    fun getLocaleQueryParam(): String {
        return "&localization=${getLocaleUseCase().language}"
    }

    /**
     * Set progress visibility of progress bar for webView pages.
     * */
    fun setProgress(visibility: Int) {
        progress.postValue(visibility)
    }

    /**
     * Intent navigation.
     * */
    fun navigateToActivity(fragment: Fragment, intent: Intent) {
        fragment.startActivity(
            intent.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }

    /**
     * Save current token from LoadResults data.
     * */
    private fun saveToken(result: LoadResult<String>): LoadResult<String> {
        result.data?.let { token = it }
        return result
    }
}

data class UserUiState(
    val userState: LoadResult<User>
)