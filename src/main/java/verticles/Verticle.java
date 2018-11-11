package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class Verticle extends AbstractVerticle {

    int senders = 0;
    int receivers = 0;
    int messages = 0;

    @Override
    public void start(Future<Void> fut) {
        Router router = Router.router(vertx);
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                    .putHeader("content-type", "text/html")
                    .end("<h1>webpick test</h1>");
        });

//        router.get("/message").handler(this::handleGetProduct);

        vertx
                .createHttpServer()
                .requestHandler(r -> {
                    senders = Integer.parseInt(r.getParam("senders"));
                    receivers = Integer.parseInt(r.getParam("receivers"));
                    messages = Integer.parseInt(r.getParam("messages"));
                    System.out.printf(senders + " - " + receivers + " - " + messages);
                    r.response().end("senders " + senders + " receivers "+ receivers + "messasges " + messages + "</h1>");
                    Vertx vertx = Vertx.vertx();

                    for (int i=0;i<senders; i++){
                        vertx.deployVerticle(new Sender(messages));
                    }

                    for (int i=0;i<receivers; i++){
                        vertx.deployVerticle(new Reciever());
                    }
                })
                .listen(8080, result -> {
                    if (result.succeeded()) {
                        fut.complete();
                    } else {
                        fut.fail(result.cause());
                    }
                });
    }


}


// router.route().handler(BodyHandler.create());
//         router.get("/products/:productID").handler(this::handleGetProduct);
//         router.put("/products/:productID").handler(this::handleAddProduct);
//         router.get("/products").handler(this::handleListProducts);
