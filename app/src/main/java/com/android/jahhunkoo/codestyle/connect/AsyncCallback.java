package com.android.jahhunkoo.codestyle.connect;

/**
 * 참고 사이트 http://javacan.tistory.com/236
 * @author	: Administrator
 * @date	: 2014. 8. 27.
 * @desc	:
 * @param <T>
 */
public interface AsyncCallback<T> {

	//결과를 받는 메서드
	public void onResult(T result);

	//처리 도중 발생한 익셉션을 받는 메서드
	public void exceptionOccured(Exception e);

	//작업을 취소했음을 받는 메서드
	public void cancelled();
}
