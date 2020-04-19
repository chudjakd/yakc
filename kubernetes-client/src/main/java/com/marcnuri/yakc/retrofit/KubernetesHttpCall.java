/*
 * KubernetesHttpCall.java
 *
 * Created on 2020-04-12, 19:33
 */
package com.marcnuri.yakc.retrofit;

import com.marcnuri.yakc.KubernetesClient;
import com.marcnuri.yakc.api.KubernetesException;
import com.marcnuri.yakc.api.KubernetesListCall;
import com.marcnuri.yakc.api.WatchEvent;
import com.marcnuri.yakc.model.ListModel;
import com.marcnuri.yakc.reactivex.WatchOnSubscribe;
import io.reactivex.Observable;
import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.stream.Stream;

/**
 * Created by Marc Nuri on 2020-04-12.
 */
public class KubernetesHttpCall<T, W> implements KubernetesListCall<T, W> {

  private final Type responseType;
  private final Call<T> delegate;
  private final KubernetesClient kubernetesClient;

  KubernetesHttpCall(Type responseType, Call<T> delegate, KubernetesClient kubernetesClient) {
    this.responseType = responseType;
    this.delegate = delegate;
    this.kubernetesClient = kubernetesClient;
  }

  @SuppressWarnings("unchecked")
  @Override
  public T get() throws IOException {
    return get((Class<T>)responseType);
  }

  @Override
  public <O> O get(Class<O> returnType) throws IOException {
    final okhttp3.Response response = kubernetesClient.getRetrofit().callFactory().newCall(request()).execute();
    if (response.isSuccessful()) {
      return kubernetesClient.getRetrofit().<O>responseBodyConverter(returnType, new Annotation[0]).convert(response.body());
    }
    throw KubernetesException.forResponse(
        response.body() == null ? "" : response.body().string(),
        response);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Stream<W> stream() throws IOException {
    return ((ListModel<W>)get()).getItems().stream();
  }

  @Override
  public Observable<WatchEvent<W>> watch() throws KubernetesException {
    return Observable.create(new WatchOnSubscribe<>(responseType, request(), kubernetesClient));
  }

  @Override
  public Response<T> execute() throws IOException {
    return delegate.execute();
  }

  @Override
  public void enqueue(final Callback<T> callback) {
    delegate.enqueue(callback);
  }

  @Override
  public boolean isExecuted() {
    return delegate.isExecuted();
  }

  @Override
  public void cancel() {
    delegate.cancel();
  }

  @Override
  public boolean isCanceled() {
    return delegate.isCanceled();
  }

  @SuppressWarnings({"CloneDoesntCallSuperClone", "squid:S1182", "squid:S2975"})
  @Override
  public KubernetesHttpCall<T, W> clone() {
    return new KubernetesHttpCall<>(responseType, delegate.clone(), kubernetesClient);
  }

  @Override
  public Request request() {
    return delegate.request();
  }

  @Override
  public Timeout timeout() {
    return delegate.timeout();
  }
}
