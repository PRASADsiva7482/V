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
public final class DMRepository_Factory implements Factory<DMRepository> {
  private final Provider<VApi> apiProvider;

  public DMRepository_Factory(Provider<VApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public DMRepository get() {
    return newInstance(apiProvider.get());
  }

  public static DMRepository_Factory create(Provider<VApi> apiProvider) {
    return new DMRepository_Factory(apiProvider);
  }

  public static DMRepository newInstance(VApi api) {
    return new DMRepository(api);
  }
}
