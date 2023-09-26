package com.bmatjik.pokedex.core.remote

import android.app.GameState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bmatjik.pokedex.domain.model.Pokemon
import dagger.hilt.android.scopes.ViewModelScoped
import timber.log.Timber
import javax.inject.Inject

@ViewModelScoped
class PokemonPagingDataSource @Inject constructor(private val dataSource: RemoteDataSource) :
    PagingSource<Int, Pokemon>() {
    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            val position = params.key ?: 0
            val offset =
                if (params.key != null) {
                    position.minus(-1).times(30).plus(1)
                } else {
                    0
                }

            val pokemon = dataSource.getPokemons(30, offset = offset)
            pokemon.fold(onSuccess = { result ->
                return LoadResult.Page(
                    result,
                    prevKey = if (position == 0) null else position,
                    nextKey = if (result.isEmpty()) null else position+1
                )
            }, onFailure = {
                Timber.e(it)
                LoadResult.Error(it)
            })
        } catch (e: Exception) {
            Timber.e(e)
            return LoadResult.Error(e)
        }
    }
}