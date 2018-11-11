package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class Reciever extends AbstractVerticle {
    private long time;
    private long messages=0;
    @Override
    public void start(Future<Void> fut) {
        time = System.nanoTime();
        vertx
                .createHttpServer()
                .requestHandler(r -> {
                    messages ++;
                    r.response().end("<h1>" + messages + "in " + time + " in nano </h1>");
                })
                .listen(
                        // Retrieve the port from the configuration,
                        // default to 8080.
                        config().getInteger("http.port", 8080),
                        result -> {
                            if (result.succeeded()) {
                                fut.complete();
                            } else {
                                fut.fail(result.cause());
                            }
                        }
                );
    }
}
