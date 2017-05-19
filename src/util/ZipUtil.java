package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
//import org.apache.tomcat.util.http.fileupload.IOUtils;

/**
 * ZIP压缩
 * 
 * 
 */
public abstract class ZipUtil {

	private ZipUtil() {

	}

	/**
	 * 打包目录.
	 * 
	 * @param zipPath
	 * @param dir
	 * @param bytes
	 * @param withRootDir
	 *            是否包含根目录名.
	 * @throws IOException
	 */
	public static void zipDir(final String zipPath, final String dir, byte[] bytes, boolean withRootDir)
			throws IOException {
/*		
		if (bytes == null) {
			bytes = new byte[1024];
		}
		FileOutputStream fos = null;
		ZipOutputStream zipout = null;
		try {
			fos = new FileOutputStream(zipPath);
			zipout = new ZipOutputStream(fos);
			final File file = new File(dir);
			if (withRootDir) {
				zip(zipout, file, "", Filter.TRUE_FILTER, bytes);
			} else {
				File[] files = file.listFiles();
				if (files != null) {
					for (File f : files) {
						zip(zipout, f, "", Filter.TRUE_FILTER, bytes);
					}
				}
			}
		} finally {
			IOUtils.closeQuietly(zipout);
			IOUtils.closeQuietly(fos);
		}
	}

	/**
	 * 将文件压缩为zip文件.
	 *//*
	public static void zip(final String zipPath, final String dir) throws IOException {
		FileOutputStream fos = null;
		ZipOutputStream zipout = null;
		try {
			fos = new FileOutputStream(zipPath);
			zipout = new ZipOutputStream(fos);
			final File file = new File(dir);
			byte[] bytes = new byte[1024];
			zip(zipout, file, "", Filter.TRUE_FILTER, bytes);
		} finally {
			IOUtils.closeQuietly(zipout);
			IOUtils.closeQuietly(fos);
		}
	}

	*//**
	 * 将文件压缩输出到out.
	 *//*
	public static void zip(final OutputStream out, final String dir) throws IOException {
		ZipOutputStream zipout = null;
		try {
			zipout = new ZipOutputStream(out);
			byte[] bytes = new byte[1024];
			zip(zipout, new File(dir), "", Filter.TRUE_FILTER, bytes);
		} finally {
			IOUtils.closeQuietly(zipout);
		}
	}

	*//**
	 * 将文件 file写入到 zip输出流中.
	 *//*
	private static void zip(final ZipOutputStream out, final File file, final String base, final Filter filter,
			byte[] bytes) throws IOException {
		String fullBase = base;
		if (!filter.needs(file)) {
			return;
		}
		if (file.isDirectory()) {
			fullBase += file.getName() + "/";
			final ZipEntry entry = new ZipEntry(fullBase); // 创建一个目录条目 [以 / 结尾]
			out.putNextEntry(entry); // 向输出流中写入下一个目录条目
			final File[] fileArr = file.listFiles(); // 递归写入目录下的所有文件
			for (final File f : fileArr) {
				zip(out, f, fullBase, filter, bytes);
			}
		} else if (file.isFile()) {
			fullBase += file.getName();
			final ZipEntry entry = new ZipEntry(fullBase); // 创建一个文件条目
			out.putNextEntry(entry); // 向输出流中写入下一个文件条目
			final FileInputStream in = new FileInputStream(file); // 写入文件内容
			int length = in.read(bytes);
			while (length > 0) {
				out.write(bytes, 0, length);
				length = in.read(bytes);
			}

			IOUtils.closeQuietly(in);
		}
	}

	*//**
	 * 将文件夹压缩输出到out（不输出文件夹本身）.
	 * 
	 * @param out
	 *            输出流
	 * @param dir
	 *            文件夹
	 *//*
	public static void zipDir(final OutputStream out, final String dir) throws IOException {
		zipDir(out, dir, Filter.TRUE_FILTER);
	}

	*//**
	 * 过滤器，只压缩符合条件的文件.
	 * 
	 *//*
	public static interface Filter {
		boolean needs(File file);

		Filter TRUE_FILTER = new TrueFilter();
	}

	*//**
	 * 总是返回true的filter.
	 * 
	 * 
	 *//*
	public static class TrueFilter implements Filter {
		public boolean needs(final File file) {
			return true;
		}
	}

	public static void zipDir(final OutputStream out, final String dir, final Filter filter) throws IOException {
		final File file = new File(dir);
		if (!file.isDirectory()) {
			throw new IllegalArgumentException("dir参数必须指定一个文件夹");
		}
		ZipOutputStream zipout = null;
		try {
			zipout = new ZipOutputStream(out);
			byte[] bytes = new byte[1024];
			final File[] fileArr = file.listFiles(); // 递归写入目录下的所有文件
			for (final File f : fileArr) {
				zip(zipout, f, "", filter, bytes);
			}
		} finally {
			IOUtils.closeQuietly(zipout);
		}
	}*/

	/**
	 * 压缩多个文件成一个zip文件
	 * 
	 * @param files
	 * @param zipFile
	 * @param fileType
	 * @author liuchao
	 * @crateDate 2017年3月2日下午5:13:58
	 */
	//临时添加的括号	
	}
	public static void zipFile(File[] files, File zipFile) {
		byte[] buf = new byte[1024];
		ZipOutputStream out = null;
		FileInputStream in = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFile));
			for (int i = 0; i < files.length; i++) {
				in = new FileInputStream(files[i]);
				out.putNextEntry(new ZipEntry(files[i].getName()));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
}
