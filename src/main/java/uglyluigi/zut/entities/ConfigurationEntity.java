package uglyluigi.zut.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "zut", name = "zut_config")
public class ConfigurationEntity {
    @Id
    @Column(name = "config_key")
    public String ConfigKey;

    @Column(name = "config_value")
    public String ConfigValue;
}
