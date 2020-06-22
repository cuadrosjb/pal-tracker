package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * notate it with @RestController.
 *
 * Implement a method called getEnv as described in the EnvControllerTest.
 *
 * Annotate the getEnv method with @GetMapping("/env").
 *
 * Declare @Value annotated constructor arguments to be populated with values of the following environment variables:
 *
 * PORT
 * MEMORY_LIMIT
 * CF_INSTANCE_INDEX
 * CF_INSTANCE_ADDR
 *
 *
 *
 */

@RestController
public class EnvController {

    private String port;
    private String memoryLimit;
    private String cfInstanceIndex;
    private String cfInstanceAddr;

    public EnvController(@Value("${port:NOT SET}") String port,
                         @Value("${memory.limit:NOT SET}")String memoryLimit,
                         @Value("${cf.instance.index:NOT SET}")String cfInstanceIndex,
                         @Value("${cf.instance.addr:NOT SET}")String cfInstanceAddr) {
        this.port = port;
        this.memoryLimit = memoryLimit;
        this.cfInstanceIndex = cfInstanceIndex;
        this.cfInstanceAddr = cfInstanceAddr;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv(){
        Map<String, String> environments = new HashMap<String, String>();
        environments.put("PORT",port);
        environments.put("MEMORY_LIMIT",memoryLimit);
        environments.put("CF_INSTANCE_INDEX",cfInstanceIndex);
        environments.put("CF_INSTANCE_ADDR",cfInstanceAddr);

        return environments;
    }
}
