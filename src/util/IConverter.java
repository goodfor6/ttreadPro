package util;

/**
 * IConverter.<br>
 * 
 * @author zhengqiushi <br>
 * @version 1.0.0 2016-5-10<br>
 * @see
 * @since JDK 1.7.0
 */
public interface IConverter {
	/**
	 * 转换.
	 * 
	 * @param value
	 *            Object
	 * @return targetValue
	 */
	Object convert(Object value);

	/**
	 * 取得源class.
	 * 
	 * @return Class
	 */
	Class<?> getSourceClass();

	/**
	 * 取得目标class.
	 * 
	 * @return Class
	 */
	Class<?> getTargetClass();
}
