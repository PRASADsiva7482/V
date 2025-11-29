package com.v.app.data.repository;

import com.v.app.data.remote.VApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class PostRepositoryImpl_Factory implements Factory<PostRepositoryImpl> {
  private final Provider<VApi> apiProvider;

  public PostRepositoryImpl_Factory(Provider<VApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public PostRepositoryImpl get() {
    return newInstance(apiProvider.get());
  }

  public static PostRepositoryImpl_Factory create(Provider<VApi> apiProvider) {
    return new PostRepositoryImpl_Factory(apiProvider);
  }

  public static PostRepositoryImpl newInstance(VApi api) {
    return new PostRepositoryImpl(api);
  }
}
