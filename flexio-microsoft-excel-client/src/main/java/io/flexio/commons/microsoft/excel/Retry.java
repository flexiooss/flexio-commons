package io.flexio.commons.microsoft.excel;

import org.codingmatters.poom.services.logging.CategorizedLogger;

import java.io.IOException;

public class Retry <Response> {

    @FunctionalInterface
    public interface Requester<Response> {
        Response request() throws IOException;
    }

    @FunctionalInterface
    public interface ResponseValidator<Response> {
        boolean validate(Response response);
    }

    @FunctionalInterface
    public interface Reporter<Response> {
        void report(Response response, int tried, boolean success);
    }

    private final Requester<Response> requester;
    private final ResponseValidator<Response> success;
    private final Reporter<Response> reporter;
    private final int maxTriesAmount;

    private CategorizedLogger logger = CategorizedLogger.getLogger(Retry.class);

    public Retry(Requester requester, ResponseValidator responseValidator) {
        this(requester, responseValidator,2);
    }

    public Retry(Requester requester, ResponseValidator responseValidator, int triesAmount) {
        this(requester, responseValidator, (o, tried, success) -> {
            CategorizedLogger logger = CategorizedLogger.getLogger(Retry.class);
            if (!success) {
                logger.error("failed request after {} attempts", tried);
            }
        }, triesAmount);
    }

    public Retry(Requester requester, ResponseValidator success, Reporter reporter, int triesAmount) {
        this.requester = requester;
        this.success = success;
        this.reporter = reporter;
        this.maxTriesAmount = triesAmount;
    }

    public Response attempt() {
        return this.attempt(0);
    }

    private Response attempt(int tries) {
        try {
            Response response = this.requester.request();
            boolean success = this.success.validate(response);
            if (success) {
                this.reporter.report(response, tries, true);
                return response;
            }
            if (tries >= this.maxTriesAmount) {
                this.reporter.report(response, tries, false);
                return response;
            }
            Thread.sleep(250);
            return this.attempt(++tries);
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

}