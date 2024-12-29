package uglyluigi.zut.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uglyluigi.zut.entities.ConfigurationEntity;

import java.util.List;
import java.util.Optional;

public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, String>
{
    @Query("SELECT config.ConfigValue FROM ConfigurationEntity config WHERE config.ConfigKey = :configKey")
    Optional<String> getValue(@Param("configKey") String configKey);

    @Query("SELECT '*' FROM ConfigurationEntity")
    List<ConfigurationEntity> getAllConfigValues();

    @Modifying
    @Transactional
    @Query("UPDATE ConfigurationEntity e SET e.ConfigValue = :value WHERE e.ConfigKey = :key")
    int setConfigValue(@Param("key") String key, @Param("value") String value);
}
