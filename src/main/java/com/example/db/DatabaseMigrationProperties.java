package com.example.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

@ConfigurationProperties(prefix = "application.db")
public class DatabaseMigrationProperties {

  private String initScript;

  private String cleanScript;

  public String getInitScript() {
    return initScript;
  }

  public void setInitScript(String initScript) {
    this.initScript = initScript;
  }

  public boolean hasInitScript() {
    return !StringUtils.isEmpty(initScript);
  }

  public String getCleanScript() {
    return cleanScript;
  }

  public void setCleanScript(String cleanScript) {
    this.cleanScript = cleanScript;
  }

  public boolean hasCleanScript() {
    return !StringUtils.isEmpty(cleanScript);
  }

}
