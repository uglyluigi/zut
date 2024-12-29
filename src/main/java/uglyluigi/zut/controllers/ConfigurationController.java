package uglyluigi.zut.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uglyluigi.zut.repositories.ConfigurationRepository;

import java.util.Optional;

@Controller
@RequestMapping("/config")
public class ConfigurationController
{
    @Autowired
    private ConfigurationRepository configRepo;

    @PostMapping("/set/{key}/{value}")
    public ResponseEntity<String> setConfigKey(@PathVariable("key") String key, @PathVariable("value") String value)
    {
        int rowsAffected = configRepo.setConfigValue(key, value);

        if (rowsAffected == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("updated config value.");
    }

    @GetMapping("/{key}")
    public ResponseEntity<String> getConfigValue(@PathVariable("key") String key)
    {
        Optional<String> configValue = configRepo.getValue(key);
        return configValue.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
