package com.alexey.netris.controller;

import com.alexey.netris.entity.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Решил, что не стоит выносить логику в сервис в рамках тестового задания.
 * Так же не делал проверок на некорректность данных.
 */
@Controller
public class MainController {
    private final WebClient client = WebClient.create("http://www.mocky.io/v2");
    private static final Set<CctvResponse> responseSet = new HashSet<>();

    /**
     * Возвращает сэт агрегированных данных по камерам
     *
     * @return CctvResponse
     * @see CctvResponse
     */
    @GetMapping("/response_entity")
    public ResponseEntity<Set<CctvResponse>> getSetOfCctvResponse() {
        responseSet.clear();
        getDataForResponse(getListOfCctvTemp());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseSet);
    }

    /**
     * Обращается к внешнему API и возвращает список ссылок на API + id камеры.
     * Blocked потому, что является основным запросом, который включает данные для дальнейшей работы.
     *
     * @return CctvRequest
     * @see CctvRequest
     */
    public List<CctvRequest> getListOfCctvTemp() {
        return client
                .get()
                .uri("/5c51b9dd3400003252129fb5")
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(CctvRequest.class).collectList().block();
    }

    /**
     * Запускает параллельный стрим по параметру, каждому из которых вызывает (prepareDataForResponse)
     *
     * @param cctvRequests список для агрегации
     */
    public void getDataForResponse(List<CctvRequest> cctvRequests) {
        cctvRequests.parallelStream().forEach(this::prepareDataForResponse);
    }

    /**
     * Вызывает методы для сбора данных из внешних API после чего агрегирует их в CctvResponse
     *
     * @param cctvRequest CctvRequest
     * @see CctvRequest
     */
    private void prepareDataForResponse(CctvRequest cctvRequest) {
        TokenData tokenData = fetchData(cctvRequest.getTokenDataUrl(), TokenData.class);
        SourceData sourceData = fetchData(cctvRequest.getSourceDataUrl(), SourceData.class);
        CctvResponse cctvResponse =
                new CctvResponse(
                        cctvRequest.getId(),
                        tokenData.getValue(),
                        tokenData.getTtl(),
                        sourceData.getUrlType(),
                        sourceData.getVideoUrl()
                );
        responseSet.add(cctvResponse);
    }

    /**
     * Отправляет запрос на внешний API, собирает данные согласно типу. Скорее всего этот метод можно оптимизировать
     * и сделать неблокирующим, решил не тратить на это слишком много времени, зато показать рабочий результат.
     *
     * @param url  ссылка на внешний API
     * @param type один из классов семейства "Data"
     * @param <T>  Наследники маркирующего класса DataMaker
     * @return TokenData или SourceData
     * @see com.alexey.netris.entity.DataMarker
     * @see SourceData
     * @see TokenData
     */
    public <T extends DataMarker> T fetchData(String url, Class<T> type) {
        return type.cast(client
                .get()
                .uri(url)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(type).block());
    }


    /*
     * Альтернативная реализация getDataForResponse(), с применением webFlux и Scheduler. Время работы аналогично первому
     * методу вероятно быстродействие упирается в блокирующий fetchData()
     */
//    public void getDataForResponse2(List<CctvRequest> cctvRequests) {
//        Flux.fromIterable(cctvRequests)
//                .parallel()
//                .runOn(Schedulers.boundedElastic()).sequential().doOnNext(this::prepareDataForResponse).doOnComplete(() -> {
//                }).subscribe();
//    }


}
