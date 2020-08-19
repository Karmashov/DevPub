package com.skillbox.devpub.model.enumerated;

public enum GlobalSettingsValues {
    MULTIUSER_MODE ("Многопользовательский режим", true),
    POST_PREMODERATION ("Премодерация постов", true),
    STATISTICS_IS_PUBLIC("Показывать всем статистику блога", true);

    private final String name;
    private Boolean value;
    private String valueText;

    GlobalSettingsValues(String name, Boolean value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public String getValueText() {
        if (value)
            return "YES";
        else
            return "NO";
    }
}
