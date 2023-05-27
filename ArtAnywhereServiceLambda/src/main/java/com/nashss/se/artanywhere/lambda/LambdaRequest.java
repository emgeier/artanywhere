package com.nashss.se.artanywhere.lambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.function.Function;

/**
 * Represents a generic "APIGateway" request made to a lambda function.
 * @param <T> The type of the concrete request that should be created from this LambdaRequest
 */
public class LambdaRequest<T> extends APIGatewayProxyRequestEvent {

    protected static final ObjectMapper MAPPER = new ObjectMapper();
    protected final Logger log = LogManager.getLogger();

    /**
     * Deserialize a T (aka 'requestClass') from the body of the request.
     * @param requestClass The type that should be created from the body of this LambdaRequest
     * @return A new instance of T that contains data from the request body
     */
    public T fromBody(Class<T> requestClass) {
        log.info("fromBody");
        System.out.println("fromBody");
        try {System.out.println(super.getBody());
            return MAPPER.readValue(super.getBody(), requestClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    String.format("Unable to deserialize %s from request body", requestClass.getSimpleName()),
                    e);
        }
    }

    /**
     * Use the given converter to create an instance of T from the request's query string.
     * @param converter Contains the conversion code
     * @return An instance of T that contains data from the request's query string
     */
//    public T fromQuery(Function<Map<String, String>, T> converter) {
//        Map<String, String> query = ifNull(super.getQueryStringParameters(), Map.of());
//        return converter.apply(query);
//    }

    /**
     * Use the given converter to create an instance of T from the request's path parameters.
     * @param converter Contains the conversion code
     * @return A instance of T that contains data from the request's path parameters
     */
    public T fromPath(Function<Map<String, String>, T> converter) {
        log.info("fromPath");
        Map<String, String> path = ifNull(super.getPathParameters(), Map.of());
        return converter.apply(path);
    }

    /**
     * Use the given converter to create an instance of T from the request's path parameters
     * and query string parameters.
     * @param converter Contains the conversion code
     * @return A instance of T that contains data from the request's path parameters
     */
//    public T fromPathAndQuery(BiFunction<Map<String, String>, Map<String, String>, T> converter) {
//        log.info("fromPathAndQuery");
//        Map<String, String> path = ifNull(super.getPathParameters(), Map.of());
//        Map<String, String> query = ifNull(super.getQueryStringParameters(), Map.of());
//        return converter.apply(path, query);
//    }
    /**
     * If obj is null, return valIfNull, otherwise return obj.
     * @param obj The object to check for null.
     * @param valIfNull The value to return if obj is null.
     * @param <T> The type of obj and valIfNull.
     * @return obj or valIfNull.
     */
    public static <T> T ifNull(T obj, T valIfNull) {
        return obj != null ? obj : valIfNull;
    }

//    /**
//     * If obj is null, return value supplied by valIfNullSupplier.
//     * @param obj The object to check for null.
//     * @param valIfNullSupplier The supplier of the value to return if obj is null.
//     * @param <T> The type of obj and the supplier.
//     * @return obj or value returned by valIfNullSupplier.
//     */
//    public static <T> T ifNull(T obj, Supplier<T> valIfNullSupplier) {
//        return obj != null ? obj : valIfNullSupplier.get();
//    }
}

