package com.v.app.domain.repository;

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
public final class SearchRepository_Factory implements Factory<SearchRepository> {
  private final Provider<VApi> apiProvider;

  public SearchRepository_Factory(Provider<VApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public SearchRepository get() {
    return newInstance(apiProvider.get());
  }

  public static SearchRepository_Factory create(Provider<VApi> apiProvider) {
    return new SearchRepository_Factory(apiProvider);
  }

  public static SearchRepository newInstance(VApi api) {
    return new SearchRepository(api);
  }
}
