package com.pallaw.swipeandlearnf.di

import com.pallaw.swipeandlearnf.data.FakeGameRepositoryImp
import com.pallaw.swipeandlearnf.domain.GameRepository
import org.koin.dsl.module

val appModule = module {
    single<GameRepository> { FakeGameRepositoryImp() }
}