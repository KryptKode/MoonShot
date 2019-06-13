package com.haroldadmin.moonshot.rocketDetails

import androidx.lifecycle.viewModelScope
import com.haroldadmin.moonshot.base.MoonShotViewModel
import com.haroldadmin.moonshotRepository.rocket.RocketsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Calendar

class RocketDetailsViewModel(
    initState: RocketDetailsState,
    private val repository: RocketsRepository
) : MoonShotViewModel<RocketDetailsState>(initState) {

    init {
        viewModelScope.launch {
            getRocketDetails(initState.rocketId)
            getLaunches(initState.rocketId, Calendar.getInstance().timeInMillis)
        }
    }

    suspend fun getRocketDetails(rocketId: String) {
        repository.flowRocketMinimal(rocketId)
            .collect { rocketResource ->
                setState { copy(rocket = rocketResource) }
            }
    }

    suspend fun getLaunches(rocketId: String, timestamp: Long) {
        repository.flowLaunchesForRocket(rocketId, timestamp)
            .collect { launchesResource ->
                setState { copy(launches = launchesResource) }
            }
    }
}