package br.com.vertx.learning_vertx.chapter02;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerVerticle extends AbstractVerticle {
  private final Logger logger = LoggerFactory.getLogger(WorkerVerticle.class);

  @Override
  public void start() throws Exception {
    vertx.setPeriodic(10_000, id -> {
      try {
        logger.info("Zzzz ....");
        Thread.sleep(8000);
        logger.info("Up!");
      } catch (InterruptedException e) {
        logger.error("Woops", e);
      }
    });
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    DeploymentOptions deploymentOptions = new DeploymentOptions()
      .setInstances(2)
      .setWorker(true);
    vertx.deployVerticle(WorkerVerticle.class, deploymentOptions);
  }
}
