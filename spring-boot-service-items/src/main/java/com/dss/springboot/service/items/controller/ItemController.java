package com.dss.springboot.service.items.controller;

import com.dss.springboot.service.items.model.domain.Item;
import com.dss.springboot.service.items.model.domain.Product;
import com.dss.springboot.service.items.model.service.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RefreshScope
@RestController
@RequestMapping("/api/v1")
public class ItemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Value("${configuration.text}")
    private String text;
    private final ItemService itemService;
    private final CircuitBreakerFactory circuitBreakerFactory;

    private final Environment env;

    public ItemController(@Qualifier("itemServiceFeign") ItemService itemService, CircuitBreakerFactory circuitBreakerFactory, Environment env) {
        this.itemService = itemService;
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.env = env;
    }

    @GetMapping("/list")
    public List<Item> findAll(@RequestParam(name = "name", required = false) String name, @RequestHeader(name = "token-request", required = false) String token) {
        System.out.println(name);
        System.out.println(token);
        return itemService.findAll();
    }

    @GetMapping("/{id}/amount/{amount}")
    public Item getById(@PathVariable Long id, @PathVariable Integer amount) {
        return circuitBreakerFactory.create("items").run(() -> itemService.findById(id, amount), e -> alternativeMethod(id, amount, e));
    }

    @CircuitBreaker(name = "items", fallbackMethod = "alternativeMethod")
    @GetMapping("/other/{id}/amount/{amount}")
    public Item getByIdOther(@PathVariable Long id, @PathVariable Integer amount) {
        return itemService.findById(id, amount);
    }

    @CircuitBreaker(name = "items", fallbackMethod = "anotherAlternativeMethod")
    @TimeLimiter(name = "items")
    @GetMapping("/another/{id}/amount/{amount}")
    public CompletableFuture<Item> getByIdAnother(@PathVariable Long id, @PathVariable Integer amount) {
        return CompletableFuture.supplyAsync(() -> itemService.findById(id, amount));
    }

    @GetMapping("/alternative/{id}/amount/{amount}")
    public Item getByIdAlternative(@PathVariable Long id, @PathVariable Integer amount) {
        return getAlternativeItem(id, amount, new Throwable("Using alternative method from the other side..."));
    }

    @GetMapping("/config")
    public ResponseEntity<?> config(@Value("${server.port}") String port) {
        Map<String, String> json = new HashMap<>();
        json.put("text", text);
        json.put("port", port);

        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            json.put("author", env.getProperty("configuration.author.name"));
            json.put("mail", env.getProperty("configuration.author.mail"));
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return itemService.save(product);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product update(@RequestBody Product product, @PathVariable Long id) {
        return itemService.update(product, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }
    public Item alternativeMethod(@PathVariable Long id, @PathVariable Integer amount, Throwable e) {
        return getAlternativeItem(id, amount, e);
    }

    public CompletableFuture<Item> anotherAlternativeMethod(@PathVariable Long id, @PathVariable Integer amount, Throwable e) {
        return CompletableFuture.supplyAsync(() -> getAlternativeItem(id, amount, e));
    }

    private static Item getAlternativeItem(Long id, Integer amount, Throwable e) {
        LOGGER.error("Error Communication: {}", e.getMessage());
        Item item = new Item();
        Product product = new Product();

        item.setAmount(amount);
        product.setName("ALTERNATIVE");
        product.setPrice(0.0);
        product.setId(id);
        item.setProduct(product);
        return item;
    }
}
