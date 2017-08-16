package com.zhenqiangli.shakespeare.util;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by zhenqiangli on 7/29/17.
 */

public class HtmlBuilder {

  private final StringBuilder html = new StringBuilder();
  private final LinkedList<String> tags = new LinkedList<>();

  public HtmlBuilder open(String element, String data) {
    tags.add(element);
    html.append('<');
    html.append(element);
    if (data != null) {
      html.append(' ').append(data);
    }
    html.append('>');
    return this;
  }

  public HtmlBuilder open(String element) {
    return open(element, null);
  }

  public HtmlBuilder close(String element) {
    html.append("</").append(element).append('>');
    for (Iterator<String> iterator = tags.iterator(); iterator.hasNext(); ) {
      if (iterator.next().equals(element)) {
        iterator.remove();
        break;
      }
    }
    return this;
  }

  public HtmlBuilder close() {
    if (tags.isEmpty()) {
      return this;
    }
    html.append("</").append(tags.removeLast()).append('>');
    return this;
  }

  public HtmlBuilder h1(String text) {
    html.append("<h1>").append(text).append("</h1>");
    return this;
  }

  public HtmlBuilder h3(String text) {
    html.append("<h3>").append(text).append("</h3>");
    return this;
  }

  public HtmlBuilder br() {
    html.append("<br/>");
    return this;
  }

  public HtmlBuilder append(String text) {
    html.append(text);
    return this;
  }

  public String build() {
    return html.toString();
  }
}
