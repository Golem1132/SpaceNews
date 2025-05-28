package pl.golem.spacenews.data


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import pl.golem.spacenews.ResultQueries
import pl.golem.spacenews.data.remote.Result
import pl.golem.spacenews.data.remote.SpaceNewsResult

@OptIn(ExperimentalPagingApi::class)
class ResultMediator(
    private val call: suspend (Int) -> SpaceNewsResult,
    private val queries: ResultQueries
) : RemoteMediator<Int, Result>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = state.anchorPosition?.let { offset ->
                        state.closestItemToPosition(offset)?.id.let { id ->
                            1
                        }
                    }
//                    remoteKey?.nextKey.minus(1) ?: 1
                    1
                }
                LoadType.PREPEND -> {
                    val remoteKey = state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { result ->
                        result.id //remoteKey class
                    }
                    val prevPage = remoteKey?: 1 // .prevKey <- return MediatorResult.Success(remoteKey != null)
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKey = state.pages.lastOrNull { it.data.isEmpty() }?.lastOrNull()?.let { result ->
                        1 //remoteKey class
                    }
                    val nextPage = remoteKey?: 1  //.nextKey <- return MediatorResult.Success(remoteKey != null)
                    nextPage
                }
            }
            val endOfPaginationReached = call(1).results.isNotEmpty()
            queries.transactionWithResult {

            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
