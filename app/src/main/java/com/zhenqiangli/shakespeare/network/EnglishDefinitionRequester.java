package com.zhenqiangli.shakespeare.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zhenqiangli.shakespeare.data.dictionary.Definition;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by zhenqiangli on 7/30/17.
 */
// https://developer.android.com/training/volley/requestqueue.html
public class EnglishDefinitionRequester {

  private static final String TAG = "EnglishDefinitionRequester";
  private static EnglishDefinitionRequester INSTANCE;
  private Context context;
  private RequestQueue requestQueue;
  private ImageLoader imageLoader;

  private EnglishDefinitionRequester(Context context) {
    this.context = context;
    this.requestQueue = getRequestQueue();
    this.imageLoader = new ImageLoader(requestQueue,
        new ImageLoader.ImageCache() {
          private final LruCache<String, Bitmap>
              cache = new LruCache<String, Bitmap>(20);

          @Override
          public Bitmap getBitmap(String url) {
            return cache.get(url);
          }

          @Override
          public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url, bitmap);
          }
        });
  }

  public static synchronized EnglishDefinitionRequester getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE = new EnglishDefinitionRequester(context);
    }
    return INSTANCE;
  }

  public <T> void addToRequestQueue(Request<T> req) {
    getRequestQueue().add(req);
  }

  public RequestQueue getRequestQueue() {
    if (requestQueue == null) {
      requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }
    return requestQueue;
  }

  public void getDefinition(String word,
      Response.Listener<Definition[]> listener,
      Response.ErrorListener errorListener) {
    String url = "http://api.wordnik.com:80/v4/word.json/" + word + "/definitions?" +
        "limit=200&" +
        "includeRelated=true&" +
        "sourceDictionaries=all&" +
        "useCanonical=true&" +
        "includeTags=true&" +
        "api_key=a2a73e7b926c924fad7001ca3111acd55af2ffabf50eb4ae5"; // My api of wordnik
    GsonRequest<Definition[]> request = new GsonRequest<>(url, Definition[].class, null,
        listener,
        errorListener);
    addToRequestQueue(request);
  }

  public ImageLoader getImageLoader() {
    return imageLoader;
  }

  // https://developer.android.com/training/volley/request-custom.html
  private class GsonRequest<T> extends Request<T> {

    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequest(String url, Class<T> clazz, Map<String, String> headers,
        Response.Listener<T> listener, Response.ErrorListener errorListener) {
      super(Method.GET, url, errorListener);
      this.clazz = clazz;
      this.headers = headers;
      this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
      return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
      listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
      try {
        String json = new String(
            response.data,
            HttpHeaderParser.parseCharset(response.headers));
        return Response.success(
            gson.fromJson(json, clazz),
            HttpHeaderParser.parseCacheHeaders(response));
      } catch (UnsupportedEncodingException e) {
        return Response.error(new ParseError(e));
      } catch (JsonSyntaxException e) {
        return Response.error(new ParseError(e));
      }
    }
  }
}
