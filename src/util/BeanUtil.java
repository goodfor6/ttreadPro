package util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;



/**
 * BeanUtil工具类扩展: 简单的dto,bo转换外，同时支持List集合中的属性转换
 * @author zhengqiushi <br>
 * @version 1.0.0 2016-5-10<br>
 * @see
 * @since JDK 1.7
 */
@SuppressWarnings("unchecked")
public class BeanUtil {
	
	/**
	 * 匹配数字的正则表达式 .
	 */
	public final static Pattern NUMBERPATTERN = Pattern.compile("^(\\d*\\.)?(\\d*)$");
	
	/**
	 * 日期格式正则表达式 .
	 */
	public final static Pattern DATEPATTERN = Pattern.compile("^(?:\\d{4})((-|/|\\.)?)(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1\\d|2\\d|3[0-1])$");
	
	
	/**
	 * 年度格式正则表达式 .
	 */
	public final static Pattern YEARPATTERN = Pattern.compile("^(?:\\d{4})$");
	
	/**
	 * 日期格式化对象 .
	 */
	private final static SimpleDateFormat SDF = new SimpleDateFormat();
	
	/**
	 * Constructor.
	 */
	private BeanUtil() {

	}

	/**
	 * 日志对象 .
	 */
	private final static Logger LOG = Logger.getLogger(BeanUtil.class);
	
	/**
	 * 复制List元素的属性..
	 * 
	 * @param sourceColl
	 *            Collection
	 * @param targetColl
	 *            Collection
	 * @param targetClass
	 *            Class
	 * @param ignoreProperties
	 *            String[]
	 *            
	 */
	public static void copyCollectionProperties(final Collection sourceColl, final Collection targetColl,
			final Class targetClass, final String[] ignoreProperties) {
		try {
			if (sourceColl != null && targetColl != null) {
				for (final Object source : sourceColl) {
					if (source != null) {
						final Object target = targetClass.newInstance();
						copyProperties(source, target, ignoreProperties);
						targetColl.add(target);
					}
				}
			}
		} catch (final Throwable ex) {
			throw new RuntimeException("Could not copy properties from source to target", ex);
		}
	}

	/**
	 * 复制List元素的属性..
	 * 
	 * @param sourceColl
	 *            Collection
	 * @param targetColl
	 *            Collection
	 * @param targetClass
	 *            Class
	 */
	public static void copyCollectionProperties(final Collection sourceColl, final Collection targetColl,
			final Class targetClass) {
		copyCollectionProperties(sourceColl, targetColl, targetClass, null);
	}
	
	/**
	 * 
	 * @param source
	 *            the source bean
	 * @param target
	 *            the target bean
	 */
	public static void copyProperties(final Object source, final Object target) {
		copyProperties(source, target, null, null, true);
	}

	/**
	 * @param source
	 *            the source bean
	 * @param target
	 *            the target bean
	 * @param editable
	 *            the class (or interface) to restrict property setting to
	 */
	public static void copyProperties(final Object source, final Object target, final Class editable) {
		copyProperties(source, target, editable, null, true);
	}

	/**
	 * 
	 * @param source
	 *            the source bean
	 * @param target
	 *            the target bean
	 * @param ignoreProperties
	 *            array of property names to ignore
	 */
	public static void copyProperties(final Object source, final Object target, final String[] ignoreProperties) {
		copyProperties(source, target, null, ignoreProperties);
	}
	
	
	/**
	 * @param source
	 *            the source bean
	 * @param target
	 *            the target bean
	 * @param onlyProperties
	 *            array of property names to copy
	 */
	public static void copyPropertiesOnly(final Object source, final Object target, final String[] onlyProperties) {
		copyProperties(source, target, null, onlyProperties, false);
	}
	
	/**
	 * 
	 * @param source
	 *            the source bean
	 * @param target
	 *            the target bean
	 * @param editable
	 *            the class (or interface) to restrict property setting to
	 * @param ignoreProperties
	 *            array of property names to ignore
	 */
	private static void copyProperties(final Object source, final Object target, final Class editable,
			final String[] ignoreProperties) {
		copyProperties(source, target, editable, ignoreProperties, true);
	}

	/**
	 * @param source
	 *            the source bean
	 * @param target
	 *            the target bean
	 * @param editable
	 *            the class (or interface) to restrict property setting to
	 * @param ignoreProperties
	 *            array of property names to ignore
	 * @param ignore
	 * 			  true or false model
	 */
	private static void copyProperties(final Object source, final Object target, final Class editable,
			final String[] ignoreProperties,  final boolean ignore) {

		if(StringUtils.isNull(source)){
			LOG.warn("Source must not be null");
			return;
		}
		if(StringUtils.isNull(target)){
			LOG.warn("Target must not be null");
			return;
		}

		Class actualEditable = target.getClass();
		if (editable != null) {
			if (!editable.isInstance(target)) {
				throw new IllegalArgumentException("Target class [" + target.getClass().getName()
						+ "] not assignable to Editable class [" + editable.getName() + "]");
			}
			actualEditable = editable;
		}
		final PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
		List ignoreList = null;
		if (ignoreProperties != null) {
			ignoreList = Arrays.asList(ignoreProperties);
		}

		for (int i = 0; i < targetPds.length; i++) {
			final PropertyDescriptor targetPd = targetPds[i];
			if (targetPd.getWriteMethod() != null) {
				if (ignore) {
					// 如果是忽略模式，则只有在忽略字段的才忽略
					if (!(ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
						continue;
					}
				} else {
					// 如果是非忽略模式，则只有在忽略字段的才拷贝
					if (ignoreProperties == null || (!ignoreList.contains(targetPd.getName()))) {
						continue;
					}
				}
				
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd
						.getName());
				
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
					
						final Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						final Object value = readMethod.invoke(source, new Object[0]);
						final Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						writeMethod.invoke(target, new Object[] { sourceToTarget(value, sourcePd, targetPd) });
						
					} catch (final Throwable ex) {
						throw new RuntimeException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}
	
	/**
	 * 转换器Map.
	 */
	private static Map<String, IConverter> CONVERTERMAP = new HashMap<String, IConverter>();

	/**
	 * 注册转换器.
	 * 
	 * @param propertyName
	 *            String
	 * @param converter
	 *            IConverter
	 */
	public static void registerConverter(final String propertyName, final IConverter converter) {
		CONVERTERMAP.put(propertyName, converter);
	}

	/**
	 * 注册转换器.
	 * 
	 * @param propertiesName
	 *            String[]
	 * @param converter
	 *            IConverter
	 */
	public static void registerConverter(final String[] propertiesName, final IConverter converter) {
		for (final String propertyName : propertiesName) {
			registerConverter(propertyName, converter);
		}
	}

	/**
	 * 移除一个转换器.
	 * 
	 * @param propertyName
	 *            String
	 */
	public static void removeConverter(final String propertyName) {
		CONVERTERMAP.remove(propertyName);
	}

	/**
	 * 移除多个转换器.
	 * 
	 * @param propertiesName
	 *            String
	 */
	public static void removeConverter(final String[] propertiesName) {
		for (final String propertyName : propertiesName) {
			removeConverter(propertyName);
		}
	}

	/**
	 * 移除所有转换器.
	 */
	public static void removeAllConverter() {
		CONVERTERMAP.clear();
	}

	/**
	 * 获得待修改的bean.
	 * 
	 * @param sourceBean
	 *            待修改的bean
	 * @param targetBean
	 *            待组装的bean
	 * @return
	 */
	public static Object getModifyBean(final Object sourceBean, final Object targetBean) {
		return getModifyBean(sourceBean, targetBean, true);
	}

	/**
	 * 获得待修改的bean.
	 * 
	 * @param sourceBean
	 *            待修改的bean
	 * @param targetBean
	 *            待组装的bean
	 * @param overwrite
	 *            是否覆盖原有属性值
	 * @return
	 */
	public static Object getModifyBean(final Object sourceBean, final Object targetBean, final boolean overwrite) {
		final PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(sourceBean.getClass());
		for (int i = 0; i < pds.length; i++) {
			final PropertyDescriptor sourcePd = pds[i];
			if (sourcePd.getReadMethod() == null) {
				continue;
			}
			final PropertyDescriptor targetPd = BeanUtils.getPropertyDescriptor(targetBean.getClass(),
					sourcePd.getName());
			if (targetPd == null || targetPd.getWriteMethod() == null) {
				continue;
			}
			try {
				final Method readMethod = sourcePd.getReadMethod();
				if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
					readMethod.setAccessible(true);
				}
				final Object value = readMethod.invoke(sourceBean, new Object[0]);
				if (value == null) {
					continue;
				}

				// R227294 获得目标对象属性的原值，若原值不为空则不拷贝新的值
				final Method targetReadMethod = targetPd.getReadMethod();
				final Class<?> targetPropType = targetPd.getPropertyType();
				final Object targetValue = targetReadMethod.invoke(targetBean, new Object[0]);
				boolean hasValue = false;
				if (isPrimitiveType(targetPropType)) {
					if (targetPropType == boolean.class) {
						if (!targetValue.toString().equals("false")) {
							hasValue = true;
						}
					} else if (targetPropType == char.class) {
						Character ch = (Character) targetValue;
						if (ch.charValue() != 0) {
							hasValue = true;
						}
					} else {
						if (new BigDecimal(targetValue.toString()).compareTo(BigDecimal.ZERO) != 0) {
							hasValue = true;
						}
					}
				} else if (targetValue != null) {
					hasValue = true;
				}
				if (hasValue && !overwrite) {
					// 目标属性已经赋值
					continue;
				}

				final Method writeMethod = targetPd.getWriteMethod();
				if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
					writeMethod.setAccessible(true);
				}
				writeMethod.invoke(targetBean, new Object[] { value });
			} catch (final Exception ex) {
				LOG.warn("属性：" + pds[i].getDisplayName().toLowerCase() + "值未设置成功。");
			}
		}
		return targetBean;
	}
	
	/**
	 * 检测一个字符串是否可转换成数字 .
	 * 
	 * @param decimalNumStr 字符串
	 * @return 是否为数字
	 */
	public static boolean isNumber(final String decimalNumStr) {
		return StringUtils.isNotNull(decimalNumStr) && NUMBERPATTERN.matcher(decimalNumStr).matches();
	}

	/**
	 * 检查指定Class是否为基本数据类型 .
	 * 
	 * @param clazz
	 *            指定Class
	 * @return boolean 是否为基本数据类型
	 */
	public static boolean isPrimitiveType(final Class<?> clazz) {
		if (clazz == byte.class || clazz == char.class || clazz == short.class || clazz == int.class
				|| clazz == long.class || clazz == float.class || clazz == double.class || clazz == boolean.class) {
			return true;
		}
		return false;
	}

	/**
	 * 根据转换器将源类型的值转换成新类型的值.
	 * 
	 * @param sourceValue
	 *            Object
	 * @param sourcePd
	 *            PropertyDescriptor
	 * @param targetPd
	 *            PropertyDescriptor
	 * @return Object
	 */
	private static Object sourceToTarget(final Object sourceValue, final PropertyDescriptor sourcePd,
			final PropertyDescriptor targetPd) {
		final IConverter converter = CONVERTERMAP.get(sourcePd.getName());
		if (converter == null || converter.getSourceClass() == null || converter.getTargetClass() == null) {
			return sourceValue;
		}
		if (converter.getSourceClass().isAssignableFrom(sourcePd.getPropertyType())
				&& converter.getTargetClass().isAssignableFrom(targetPd.getPropertyType())) {
			return converter.convert(sourceValue);
		}
		return sourceValue;
	}

	
	/**
	 * 根据转换器将源类型的值转换成新类型的值.
	 * 
	 * @param sourceValue
	 *            Object
	 * @param sourcePd
	 *            PropertyDescriptor
	 * @param targetPd
	 *            PropertyDescriptor
	 * @return Object
	 */
	private static Object sourceToTarget(final Object sourceValue, final String srcPropName, final Class srcPropType,
			final PropertyDescriptor targetPd) {
		final IConverter converter = CONVERTERMAP.get(srcPropName);
		if (converter == null || converter.getSourceClass() == null || converter.getTargetClass() == null) {
			return sourceValue;
		}
		if (converter.getSourceClass().isAssignableFrom(srcPropType)
				&& converter.getTargetClass().isAssignableFrom(targetPd.getPropertyType())) {
			return converter.convert(sourceValue);
		}
		return sourceValue;
	}
	
	
	/**
	 * 将BigDecimal转换成String .
	 * 
	 * @param decimalNum
	 *            输入数字
	 * @return String
	 */
	public static String bigDecimalToString(final BigDecimal decimalNum) {
		if (decimalNum != null) {
			return decimalNum.toPlainString();
		}
		return null;
	}
	
	/**
	 * 将BigDecimal转换成Integer .
	 * 
	 * @param decimalNum
	 *            输入数字
	 * @return Integer
	 */
	public static Integer bigDecimalToInteger(final BigDecimal decimalNum) {
		if (decimalNum != null) {
			return decimalNum.intValue();
		}
		return null;
	}
	
	/**
	 * 将String转换成BigDecimal .
	 * 
	 * @param decimalNumStr
	 *            输入数字字符串
	 * @param multiple
	 *            倍数
	 * @return BigDecimal
	 */
	public static BigDecimal stringToBigDecimal(final String decimalNumStr, final int multiple) {
		return stringToBigDecimal(decimalNumStr, new BigDecimal(multiple));
	}
	
	/**
	 * 将String转换成BigDecimal .
	 * 
	 * @param decimalNumStr
	 *            输入数字字符串
	 * @return BigDecimal
	 */
	public static BigDecimal stringToBigDecimal(final String decimalNumStr) {
		return stringToBigDecimal(decimalNumStr, null);
	}
	
	/**
	 * 将String转换成BigDecimal .
	 * 
	 * @param decimalNumStr
	 *            输入数字字符串
	 * @param multiple
	 *            倍数
	 * @return BigDecimal
	 */
	public static BigDecimal stringToBigDecimal(final String decimalNumStr, final BigDecimal multiple) {
		BigDecimal result = null;
		if (StringUtils.isNotNull(decimalNumStr)) {
			boolean b = isNumber(decimalNumStr);
			if (b) {
				if (multiple != null) {
					result = new BigDecimal(decimalNumStr).multiply(multiple);
				} else {
					result = new BigDecimal(decimalNumStr);
				}
			}
		}
		return result;
	}

	/**
	 * 将String转换成Date .
	 * 
	 * @param dateStr
	 *            输入日期字符串
	 * @return Date
	 */
	public static Date stringToDate(final String dateStr) {
		Date result = null;
		if (dateStr != null) {
			Matcher matcher = DATEPATTERN.matcher(dateStr);
			if (matcher.matches()) {
				String separator = matcher.group(matcher.groupCount());
				if (separator == null) {
					separator = "";
				}
				SDF.applyPattern("yyyy" + separator + "MM" + separator + "dd");
				try {
					result = SDF.parse(dateStr);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return result;
	}
	
	/**
	 * 将String转换成Year Date .
	 * 
	 * @param dateStr
	 *            输入日期字符串
	 * @return Date
	 */
	public static Date stringToYearDate(final String dateStr) {
		Date result = null;
		if (dateStr != null) {
			Matcher matcher = YEARPATTERN.matcher(dateStr);
			if (matcher.matches()) {
				SDF.applyPattern("yyyy-MM-dd");
				try {
					result = SDF.parse(dateStr+"-01-01");
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return result;
	}
	
	/**
	 * 将String转换成Year Date .
	 * 
	 * @param str
	 *            输入字符串
	 * @return String
	 */
	 public static String strToHex(final String str){
		  char[] chars = str.toCharArray();
		  StringBuffer hex = new StringBuffer();
		  for(int i = 0; i < chars.length; i++){
		    hex.append(Integer.toHexString((int)chars[i]));
		  }
		  return hex.toString();
	}
}
