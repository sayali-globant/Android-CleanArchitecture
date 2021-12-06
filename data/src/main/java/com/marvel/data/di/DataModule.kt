package com.marvel.data.di

import com.marvel.data.characters.repository.CharacterRepositoryImpl
import com.marvel.data.details.repository.CharacterDetailsRepositoryImpl
import com.marvel.data.mapper.characterdetails.CharacterDetailsResponseMapperImpl
import com.marvel.data.mapper.charcters.CharactersResponseMapperImpl
import com.marvel.data.source.characterdetails.CharacterDetailsDataSourceImpl
import com.marvel.data.source.characterlist.CharactersDataSourceImpl


object DataModule {
    val networkModule by lazy {
        NetworkModule()
    }

    @Volatile
    var characterListRepository: CharacterRepositoryImpl? = null

    @Volatile
    var characterDetailsRepository: CharacterDetailsRepositoryImpl? = null

    /**
     * This method provides character list repository
     */
    fun provideCharacterListRepository(): CharacterRepositoryImpl {
        // useful because this method can be accessed by multiple threads
        synchronized(this) {
            return characterListRepository ?: createCharacterRepository()
        }
    }

    /**
     * This method provides character details repository
     */
    fun provideCharacterDetailsRepository(): CharacterDetailsRepositoryImpl {
        // useful because this method can be accessed by multiple threads
        synchronized(this) {
            return characterDetailsRepository ?: createCharacterDeatilsRepository()
        }
    }

    /**
     * This method creates character repository
     */
    private fun createCharacterRepository(): CharacterRepositoryImpl {
        val newRepo =
            CharacterRepositoryImpl(
                CharactersDataSourceImpl(
                    networkModule.createCharacterApi(),
                    CharactersResponseMapperImpl()
                )
            )
        characterListRepository = newRepo
        return newRepo
    }

    /**
     * This method creates character details repository
     */
    private fun createCharacterDeatilsRepository(): CharacterDetailsRepositoryImpl {
        val newRepo =
            CharacterDetailsRepositoryImpl(
                CharacterDetailsDataSourceImpl(
                    networkModule.createCharacterApi(),
                    CharacterDetailsResponseMapperImpl()
                )
            )
        characterDetailsRepository = newRepo
        return newRepo
    }
}
