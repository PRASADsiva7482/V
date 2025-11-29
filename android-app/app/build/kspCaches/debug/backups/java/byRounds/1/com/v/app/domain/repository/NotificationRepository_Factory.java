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
public final class NotificationRepository_Factory implements Factory<NotificationRepository> {
  private final Provider<VApi> apiProvider;

  public NotificationRepository_Factory(Provider<VApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public NotificationRepository get() {
    return newInstance(apiProvider.get());
  }

  public static NotificationRepository_Factory create(Provider<VApi> apiProvider) {
    return new NotificationRepository_Factory(apiProvider);
  }

  public static NotificationRepository newInstance(VApi api) {
    return new NotificationRepository(api);
  }
}
