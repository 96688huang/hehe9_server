package cn.hehe9.common.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 该类封装了用户注册与注销某种类型的对象的功能
 *
 * <b>线程安全</b> 该类线程安全,它的所有方法都进行了同步
 *
 * @author Scofield
 * @version 1.0
 *
 * @param <K>
 *            被请求注册或注销的对象,如Security,Page等
 * @param <V>
 *            请求者,如IoSession等
 */
public class Subscription<K, V> {

	/** 用于持有请求者与被请求对象之间的对应关系,通常是一个被请求对象对应一组请求者 */
	private Map<K, Set<V>> subscriptions = new HashMap<K, Set<V>>();

	/**
	 * 某对象被请求者请求.只有当请求者第一次请求某对象时,才返回true,否则返回false.
	 *
	 * 如果任一参数为null,则直接返回false
	 *
	 * @param k
	 *            被请求的对象
	 * @param v
	 *            请求者
	 * @return 是否成功新增了该请求关系
	 */
	public synchronized boolean subscribe(K k, V v) {
		if (k == null || v == null)
			return false;

		Set<V> vSet = this.subscriptions.get(k);
		if (vSet == null) {
			vSet = new CopyOnWriteArraySet<V>();
			this.subscriptions.put(k, vSet);
		}
		return vSet.add(v);
	}

	/**
	 * 某对象被请求者注销.只有当请求者已经注册了该对象,才能成功注销,返回true,其它情况返回false.
	 *
	 * 如果任一参数为null,则直接返回false
	 *
	 * @param k
	 *            被注销的对象
	 * @param v
	 *            请求注销者
	 * @return 请求者是否成功的注销了该对象
	 */
	public synchronized boolean unsubscribe(K k, V v) {
		if (k == null || v == null)
			return false;

		if (!this.containsKey(k))
			return false;
		Set<V> vSet = this.subscriptions.get(k);
		boolean removed = vSet.remove(v);
		if (removed && vSet.isEmpty()) {
			this.subscriptions.remove(k);
		}
		return removed;
	}

	/**
	 * 被请求的所有对象中是否包含指定的对象. 如果参数为null,直接返回false
	 *
	 * @param k
	 *            被请求的对象,可为null
	 * @return 被请求的所有对象中是否包含指定的对象
	 */
	public synchronized boolean containsKey(K k) {
		return k == null ? false : this.subscriptions.containsKey(k);
	}

	/**
	 * 是否包含参数中的对应请求关系
	 *
	 * @param k
	 *            被请求对象
	 * @param v
	 *            请求者
	 * @return 是否包含参数中的对应请求关系
	 */
	public synchronized boolean containsValue(K k, V v) {
		if (containsKey(k)) {
			return v == null ? false : get(k).contains(v);
		}
		return false;
	}

	/**
	 * 得到某对象的请求者.如果参数为null,或者找不到,则返回null
	 *
	 * @param k
	 *            指定的被请求对象,可为null
	 * @return 该对象的请求者. 如果找不到,则返回null
	 */
	public synchronized Set<V> get(K k) {
		if (k == null)
			return null;
		Set<V> values = this.subscriptions.get(k);
		return values == null ? null : values;
	}

	/**
	 * 得到所有的被请求对象. 如果没有,返回空Set
	 *
	 * @return 所有的被请求对象
	 */
	public synchronized Set<K> keys() {
		return new HashSet<K>(this.subscriptions.keySet());

	}

	/**
	 * 得到所有的请求者. 如果找不到,则返回空Set
	 *
	 * @return 得到所有的请求者
	 */
	public synchronized Set<V> allValues() {
		Set<V> allValues = new HashSet<V>();
		for (Set<V> values : this.subscriptions.values()) {
			allValues.addAll(values);
		}
		return allValues;
	}
}
