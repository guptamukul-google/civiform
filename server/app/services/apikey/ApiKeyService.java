package services.apikey;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.inject.Inject;
import java.util.Optional;
import models.ApiKey;
import play.data.DynamicForm;
import repository.ApiKeyRepository;

public class ApiKeyService {

  private final ApiKeyRepository repository;

  @Inject
  public ApiKeyService(ApiKeyRepository repository) {
    this.repository = checkNotNull(repository);
  }

  public ApiKeyMutationResult createApiKey(DynamicForm form) {
    ApiKey apiKey = new ApiKey();

    // generate ID
    // generate secret

    apiKey = repository.insert(apiKey).toCompletableFuture().join();

    // encode the creds into a credentials string
    // return both the API key and the credentials string
    return new ApiKeyMutationResult(apiKey, "test");
  }

  public static class ApiKeyMutationResult {
    private final ApiKey apiKey;
    private final Optional<String> credentials;

    public ApiKeyMutationResult(ApiKey apiKey, String credentials) {
      this.apiKey = checkNotNull(apiKey);
      this.credentials = Optional.of(credentials);
    }

    public ApiKey getApiKey() {
      return apiKey;
    }

    public String getCredentials() {
      return credentials.get();
    }
  }
}
