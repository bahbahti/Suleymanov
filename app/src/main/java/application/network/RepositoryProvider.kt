package application.network

//singleton
object RepositoryProvider {

    fun provideRepository() : Repository {
        return Repository(ApiService.create())
    }
}