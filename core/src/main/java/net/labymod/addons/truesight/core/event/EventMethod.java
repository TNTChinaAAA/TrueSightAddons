package net.labymod.addons.truesight.core.event;

import java.lang.reflect.Method;

public class EventMethod {

  private EventListener eventListener;
  private Method method;

  public EventMethod(EventListener eventListener, Method method) {
    this.eventListener = eventListener;
    this.method = method;
  }

  public EventListener getEventListener() {
    return this.eventListener;
  }

  public Method getMethod() {
    return this.method;
  }
}
