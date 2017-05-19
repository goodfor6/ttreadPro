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
 * ZIPѹ��
 * 
 * 
 */
public abstract class ZipUtil {

	private ZipUtil() {

	}

	/**
	 * ���Ŀ¼.
	 * 
	 * @param zipPath
	 * @param dir
	 * @param bytes
	 * @param withRootDir
	 *            �Ƿ������Ŀ¼��.
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
	 * ���ļ�ѹ��Ϊzip�ļ�.
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
	 * ���ļ�ѹ�������out.
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
	 * ���ļ� fileд�뵽 zip�������.
	 *//*
	private static void zip(final ZipOutputStream out, final File file, final String base, final Filter filter,
			byte[] bytes) throws IOException {
		String fullBase = base;
		if (!filter.needs(file)) {
			return;
		}
		if (file.isDirectory()) {
			fullBase += file.getName() + "/";
			final ZipEntry entry = new ZipEntry(fullBase); // ����һ��Ŀ¼��Ŀ [�� / ��β]
			out.putNextEntry(entry); // ���������д����һ��Ŀ¼��Ŀ
			final File[] fileArr = file.listFiles(); // �ݹ�д��Ŀ¼�µ������ļ�
			for (final File f : fileArr) {
				zip(out, f, fullBase, filter, bytes);
			}
		} else if (file.isFile()) {
			fullBase += file.getName();
			final ZipEntry entry = new ZipEntry(fullBase); // ����һ���ļ���Ŀ
			out.putNextEntry(entry); // ���������д����һ���ļ���Ŀ
			final FileInputStream in = new FileInputStream(file); // д���ļ�����
			int length = in.read(bytes);
			while (length > 0) {
				out.write(bytes, 0, length);
				length = in.read(bytes);
			}

			IOUtils.closeQuietly(in);
		}
	}

	*//**
	 * ���ļ���ѹ�������out��������ļ��б���.
	 * 
	 * @param out
	 *            �����
	 * @param dir
	 *            �ļ���
	 *//*
	public static void zipDir(final OutputStream out, final String dir) throws IOException {
		zipDir(out, dir, Filter.TRUE_FILTER);
	}

	*//**
	 * ��������ֻѹ�������������ļ�.
	 * 
	 *//*
	public static interface Filter {
		boolean needs(File file);

		Filter TRUE_FILTER = new TrueFilter();
	}

	*//**
	 * ���Ƿ���true��filter.
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
			throw new IllegalArgumentException("dir��������ָ��һ���ļ���");
		}
		ZipOutputStream zipout = null;
		try {
			zipout = new ZipOutputStream(out);
			byte[] bytes = new byte[1024];
			final File[] fileArr = file.listFiles(); // �ݹ�д��Ŀ¼�µ������ļ�
			for (final File f : fileArr) {
				zip(zipout, f, "", filter, bytes);
			}
		} finally {
			IOUtils.closeQuietly(zipout);
		}
	}*/

	/**
	 * ѹ������ļ���һ��zip�ļ�
	 * 
	 * @param files
	 * @param zipFile
	 * @param fileType
	 * @author liuchao
	 * @crateDate 2017��3��2������5:13:58
	 */
	//��ʱ��ӵ�����	
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
