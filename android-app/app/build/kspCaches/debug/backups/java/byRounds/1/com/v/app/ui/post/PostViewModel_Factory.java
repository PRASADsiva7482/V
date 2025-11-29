package com.v.app.ui.post;

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
public final class PostViewModel_Factory implements Factory<PostViewModel> {
  private final Provider<PostRepository> repositoryProvider;

  public PostViewModel_Factory(Provider<PostRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public PostViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static PostViewModel_Factory create(Provider<PostRepository> repositoryProvider) {
    return new PostViewModel_Factory(repositoryProvider);
  }

  public static PostViewModel newInstance(PostRepository repository) {
    return new PostViewModel(repository);
  }
}
