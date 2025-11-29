package com.v.app.ui.dm;

import com.v.app.domain.repository.DMRepository;
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
public final class DMViewModel_Factory implements Factory<DMViewModel> {
  private final Provider<DMRepository> repositoryProvider;

  public DMViewModel_Factory(Provider<DMRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DMViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static DMViewModel_Factory create(Provider<DMRepository> repositoryProvider) {
    return new DMViewModel_Factory(repositoryProvider);
  }

  public static DMViewModel newInstance(DMRepository repository) {
    return new DMViewModel(repository);
  }
}
