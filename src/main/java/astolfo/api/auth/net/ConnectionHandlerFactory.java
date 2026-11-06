package astolfo.api.auth.net;

public interface ConnectionHandlerFactory {
   ConnectionHandler createHandler(NioConnection var1);
}
