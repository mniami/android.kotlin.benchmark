package android.benchmark.helpers.dataservices.datasource

import android.benchmark.auth.Auth
import android.benchmark.domain.User
import android.benchmark.helpers.cache.LocalDataCache
import android.benchmark.helpers.dataservices.databases.Database
import io.reactivex.Observable

class UserDataSource(private val database: Database, private val auth : Auth) : ObservableDataSource<User>{
    companion object {
        val ID = KeyDataSourceId("current.user.name")
    }

    override val data: ObservableData<User>
        get() {
            return ObservableDataImpl(database.getCurrentUserAsync())
        }
    override val id: DataSourceId
        get() {
            return ID
        }

    fun setUser(user : User) : Observable<User>{
        return database.setUser(user)
    }

}