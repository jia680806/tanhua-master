package com.jia.tanhua.dubbo.api;

import com.jia.tanhua.domain.Settings;

public interface SettingsApi {
    public void update(Settings settings);

    public Settings findSettingByUserId(Long userId);


}
