package com.example.marvels.modules

import com.marvel.data.di.DataModule
import com.marvel.domain.usecase.characters.GetCharactersUseCase
import com.marvel.domain.usecase.characters.GetCharactersUseCaseImpl
import com.marvel.domain.usecase.details.GetCharacterDetailsUseCase
import com.marvel.domain.usecase.details.GetCharacterDetailsUseCaseImpl
import com.marvel.domain.usecase.repository.CharacterDetailsRepository
import com.marvel.domain.usecase.repository.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Get UseCase Data
 */
@Module
@InstallIn(SingletonComponent::class)
class CharacterModule {
    private val characterListRepository: CharactersRepository
        get() = DataModule.provideCharacterListRepository()

    private val characterDetailsRepository: CharacterDetailsRepository
        get() = DataModule.provideCharacterDetailsRepository()

    @Provides
    @Singleton
    fun getCharacterListUseCase(): GetCharactersUseCase =
        GetCharactersUseCaseImpl(characterListRepository)

    @Provides
    @Singleton
    fun getCharacterDetailsUseCase(): GetCharacterDetailsUseCase =
        GetCharacterDetailsUseCaseImpl(characterDetailsRepository)
}
