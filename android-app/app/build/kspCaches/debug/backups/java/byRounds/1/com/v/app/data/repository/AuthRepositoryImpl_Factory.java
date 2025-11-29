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
public final class AuthRepositoryImpl_Factory implements Factory<AuthRepositoryImpl> {
  private final Provider<VApi> apiProvider;

  public AuthRepositoryImpl_Factory(Provider<VApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public AuthRepositoryImpl get() {
    return newInstance(apiProvider.get());
  }

  public static AuthRepositoryImpl_Factory create(Provider<VApi> apiProvider) {
    return new AuthRepositoryImpl_Factory(apiProvider);
  }

  public static AuthRepositoryImpl newInstance(VApi api) {
    return new AuthRepositoryImpl(api);
  }
}
