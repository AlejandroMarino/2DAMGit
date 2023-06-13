package dao.impl;

import dao.auth.CacheAuthorization;
import dao.retrofit.calls.APIUser;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.User;
import okhttp3.Credentials;

public class DaoUser extends DaoGenerics {
    private final APIUser api;
    private final CacheAuthorization ca;

    @Inject
    public DaoUser(APIUser api, CacheAuthorization ca) {
        this.api = api;
        this.ca = ca;
    }

    public Single<Either<String, User>> getValidatedUser(User user) {
        ca.setUser(user.getName());
        ca.setPass(user.getPassword());
        return this.safeSingleApicall(api.getValidatedUser(user.getName(), user.getPassword()));
    }
}
