package net.labymod.addons.truesight.core.listener;

import net.labymod.addons.truesight.core.TrueSightAddon;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.SettingWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.config.SettingWidgetInitializeEvent;

public class ESPSubSettingSettingUpdateEventListener {

  private final TrueSightAddon addon;
  public SwitchWidget espTargetPlayer = null;
  public SwitchWidget espOnlyPlayer = null;

  public ESPSubSettingSettingUpdateEventListener(TrueSightAddon addon) {
    this.addon = addon;
  }

  public TrueSightAddon getAddon() {
    return this.addon;
  }

  public void info(CharSequence charSequence, Object... arg) {
    this.addon.logger().info(charSequence, arg);
  }

  @Subscribe
  public void onSettingWidgetInit(SettingWidgetInitializeEvent event) {
    //
    /*
    this.addon.logger().info("111111111111111111111111111111111111111111111111111111");

    for (int i = 0; i < event.getSettings().size(); i++) {
        Widget widget = event.getSettings().get(i);
        this.addon.logger().info("___________________________________________________");
        this.addon.logger().info("Widget: " + widget.getDefaultRendererName());
        this.addon.logger().info("Qualified Name: " + widget.getQualifiedName());
        this.addon.logger().info("Type: " + widget.getTypeName());
        this.addon.logger().info("Unique ID: " + widget.getUniqueId());
        this.addon.logger().info("___________________________________________________");

        if (widget instanceof SwitchWidget) {
            SwitchWidget switchWidget = (SwitchWidget) widget;
            this.addon.logger().info("it's switch widget! index: " + String.valueOf(i+1));
        } else {
          widget.getChildren().forEach(apWidget -> {
              if (apWidget instanceof SwitchWidget switchWidget) {
                  addon.logger().info("Yes!");
              } else {
                  addon.logger().info(apWidget.getClass().getSimpleName());
              }
          });

          this.addon.logger().info(widget.getChildren().size() + " widgets have been initialized!");
        }
    }

    this.addon.logger().info("___________________________________________________");
    this.addon.logger().info("Size: " + event.getSettings().size());
    this.addon.logger().info("_0000000000000000002221112220000000000000000000000000_");*/

    //this.addon.logger().info("___________________________________________________");
    for (int i = 0; i < event.getSettings().size(); i++) {
      Widget widget = event.getSettings().get(i);

      if (widget instanceof SettingWidget settingWidget) {

        /*
          this.info("size: " + settingWidget.getChildren().size());
          this.info("ID: " + settingWidget.setting().getId());
          this.info("TranslationKey: " + settingWidget.setting().getTranslationKey());
          this.info("toString: " + settingWidget.setting().toString());*/

          //;
          if (settingWidget.setting().getTranslationKey().equals("truesight.settings.esp.targetPlayer")) {
              for (Widget childWidget : settingWidget.setting().asElement().getWidgets()) {
                  if (childWidget instanceof SwitchWidget switchWidget) {
                      this.espTargetPlayer = switchWidget;
                      //new Color(255,255,85);
                      //this.info(new Color(switchWidget.backgroundColorTransitionDuration().get().intValue()).toString());
                  }
              }
          } else if (settingWidget.setting().getTranslationKey().equals("truesight.settings.esp.onlyPlayer")) {
              for (Widget childWidget : settingWidget.setting().asElement().getWidgets()) {
                if (childWidget instanceof SwitchWidget switchWidget) {
                  this.espOnlyPlayer = switchWidget;
                }
              }
          }
      }
    }

    //this.addon.logger().info("___________________________________________________");

    /*
    // 判断是我们关心的配置项
    if ("onlyPlayer".equals(event.setting().getId())) {
      if (event.widget() instanceof SwitchWidget sw) {
        this.onlyPlayerSwitch = sw;
        addon.logger().info("onlyPlayer SwitchWidget 初始化完成");

        // 你可以在这里直接改 UI 外观
        sw.color(java.awt.Color.RED); // 改颜色
        sw.setEnabled(true);          // 启用/禁用
      }
    }

    if ("targetPlayer".equals(event.setting().getId())) {
      if (event.widget() instanceof SwitchWidget sw) {
        // 监听 targetPlayer UI 层的值变化
        sw.addChangeListener((type, oldValue, newValue) -> {
          if (!newValue && onlyPlayerSwitch != null) {
            // 直接改另一个 Widget 的值
            onlyPlayerSwitch.setValue(false);
            // 同步数据层
            addon.configuration().getEsp().setOnlyPlayer(false);
          }
        });
      }
    }*/
  }

  /*@Subscribe
  public void onSettingUpdate(SettingUpdateEvent event) {
    if (event.phase() == Phase.POST) {
      if ("targetPlayer".equals(event.setting().getId()) && !this.addon.configuration().getEsp().getTargetPlayer().get().booleanValue()) {
        for (Setting setting : TrueSightAddon.addon.configuration().getEsp().toSettings()) {
          if (setting.getId().equals("onlyPlayer")) {
            this.addon.logger().info("----------------------------------------");

            if (setting instanceof SwitchWidget) {
                this.addon.logger().info("yes!");
            }

            Laby.labyAPI();

            //this.addon.logger().info("Get from AdvancedAccessor: " + setting.asElement().getAdvancedAccessor().get());
            //this.addon.logger().info("Get from Accessor: " + setting.asElement().getAccessor().get());
            setting.asElement().getAccessor().set(Boolean.FALSE);
            this.addon.logger().info("Type: " + setting.asElement().getAccessor().getType().getName());
            this.addon.logger().info("----------------------------------------");
          }
        }

        TrueSightAddon.addon.configuration().getEsp().setOnlyPlayer(false);
      }
    }
  }

  @Subscribe
  public void onScreenUpdateVanillaWidgetUpdate(ScreenUpdateVanillaWidgetEvent event) {
    TrueSightAddon.addon.logger().info("-----: " + event.getWidget().getUniqueId());

    if (event.phase() == Phase.POST) {
      if ("targetPlayer".equals(event.setting().getId()) && !this.addon.configuration().getEsp().getTargetPlayer().get().booleanValue()) {
        for (Setting setting : TrueSightAddon.addon.configuration().getEsp().toSettings()) {
          TrueSightAddon.addon.logger().info("----------------------------------------");
          TrueSightAddon.addon.logger().info("ID: " + setting.getId());
          TrueSightAddon.addon.logger().info("Path: " + setting.getPath());
          TrueSightAddon.addon.logger().info("Translation ID: " + setting.getTranslationId());
          TrueSightAddon.addon.logger().info("Translation Key: " + setting.getTranslationKey());
          TrueSightAddon.addon.logger().info("----------------------------------------");
        }

        TrueSightAddon.addon.configuration().getEsp().setOnlyPlayer(false);
      }
    }
  }*/
}
