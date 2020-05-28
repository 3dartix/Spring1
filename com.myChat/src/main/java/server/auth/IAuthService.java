package server.auth;

public interface IAuthService {
    SqlQueryHelper getSqlQueryHelper();
    void disconnect();
}
