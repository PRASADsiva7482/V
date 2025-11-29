package com.v.app.ui.profile;

import com.v.app.domain.repository.AuthRepository;
import com.v.app.domain.repository.PostRepository;
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
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<AuthRepository> repositoryProvider;

  private final Provider<PostRepository> postRepositoryProvider;

  public ProfileViewModel_Factory(Provider<AuthRepository> repositoryProvider,
      Provider<PostRepository> postRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.postRepositoryProvider = postRepositoryProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(repositoryProvider.get(), postRepositoryProvider.get());
  }

  public static ProfileViewModel_Factory create(Provider<AuthRepository> repositoryProvider,
      Provider<PostRepository> postRepositoryProvider) {
    return new ProfileViewModel_Factory(repositoryProvider, postRepositoryProvider);
  }

  public static ProfileViewModel newInstance(AuthRepository repository,
      PostRepository postRepository) {
    return new ProfileViewModel(repository, postRepository);
  }
}
