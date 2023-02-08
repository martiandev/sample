package team.standalone.core.network.firebase.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.standalone.core.network.firebase.util.ArtistCollection
import team.standalone.core.network.firebase.util.FirebasePath
import team.standalone.core.network.firebase.util.UserCollection
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object FirebaseModule {
    @Singleton
    @Provides
    fun providesFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Singleton
    @Provides
    fun providesFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Singleton
    @Provides
    fun providesFirebaseStorage(): FirebaseStorage = Firebase.storage

    @UserCollection
    @Provides
    fun providesUserCollection(firebaseFirestore: FirebaseFirestore): CollectionReference =
        firebaseFirestore.collection(FirebasePath.USERS)

    @ArtistCollection
    @Provides
    fun providesArtistCollection(firebaseFirestore: FirebaseFirestore): CollectionReference =
        firebaseFirestore.collection(FirebasePath.ARTIST)

    @Singleton
    @Provides
    fun providesFirebaseFunctions(): FirebaseFunctions = Firebase.functions
}