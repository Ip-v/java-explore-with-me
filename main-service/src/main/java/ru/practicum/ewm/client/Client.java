package ru.practicum.ewm.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.client.dto.EndPointHit;
import ru.practicum.ewm.client.dto.ViewStats;

import java.util.List;

@FeignClient(value = "stats",  url = "${feign.url}")
public interface Client {
    @PostMapping("/hit")
    void hit(@RequestBody EndPointHit hit);

    @GetMapping("/stats")
    List<ViewStats> getStats(@RequestParam String start,
                             @RequestParam String end,
                             @RequestParam(required = false) List<String> uris,
                             @RequestParam(defaultValue = "false") Boolean unique);
}
