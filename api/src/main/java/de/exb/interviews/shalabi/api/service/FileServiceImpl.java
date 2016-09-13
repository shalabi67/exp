package de.exb.interviews.shalabi.api.service;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileServiceImpl implements FileService {
	public static File create(final URL path) throws FileServiceException {
		return new FileServiceImpl().createFile(path);
	}
	/*
	this function is needed for testing inorder to mock file.
	 */
	public File createFile(final URL path) throws FileServiceException {
		return new File(decodePath(path));
	}

	@Override
	public OutputStream openForWriting( final File ioFile, final boolean aAppend)
			throws FileServiceException {
		try {
			return new FileOutputStream(ioFile, aAppend);
		} catch (final FileNotFoundException e) {
			throw new FileServiceException("cannot open file", e);
		}
	}

	@Override
	public InputStream openForReading( final File ioFile) throws FileServiceException {
		try {
			return new FileInputStream(ioFile);
		} catch (final FileNotFoundException e) {
			throw new FileServiceException("cannot open file", e);
		}
	}

	@Override
	public URL construct( final String aPath) throws FileServiceException {
		try {
			return new File(aPath).toURI().toURL();
		} catch (final MalformedURLException e) {
			throw new FileServiceException("cannot construct file url", e);
		}
	}

	@Override
	public URL construct( final URL aParentPath, final String aChildPath)
			throws FileServiceException {
		try {
			return new File(new File(decodePath(aParentPath)), aChildPath).toURI().toURL();
		} catch (final MalformedURLException e) {
			throw new FileServiceException("cannot construct file url", e);
		}
	}

	@Override
	public boolean createNewFile( final File ioFile) throws FileServiceException {
		try {
			return ioFile.createNewFile();
		} catch (final IOException e) {
			throw new FileServiceException("cannot create new file", e);
		}
	}

	@Override
	public boolean mkdir( final File ioFile)  {
		return ioFile.mkdir();
	}

	@Override
	public boolean mkdirs( final File ioFile)  {
		return ioFile.mkdirs();
	}

	@Override
	public List<de.exb.interviews.shalabi.api.storage.File> list(final File ioFile) throws FileServiceException {
		
			final List<de.exb.interviews.shalabi.api.storage.File> files = new ArrayList<de.exb.interviews.shalabi.api.storage.File>();
			for (final File file : ioFile.listFiles()) {
				de.exb.interviews.shalabi.api.storage.File newFile = de.exb.interviews.shalabi.api.storage.File.create(file);
				files.add(newFile);
			}
			return files;
		
	}

	@Override
	public void delete( final File ioFile, final boolean aRecursive)
			throws FileServiceException {

		final File f = ioFile;
		if (aRecursive) {
			final Path path = Paths.get(f.getAbsolutePath());
			try {
				Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(final Path aFile, final BasicFileAttributes aAttrs)
							throws IOException {
						Files.delete(aFile);
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(final Path aDir, final IOException aExc)
							throws IOException {
						Files.delete(aDir);
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (final IOException e) {
				throw new FileServiceException("cannot delete files", e);
			}
		} else {
			if (!f.delete()) {
				throw new FileServiceException("cannot delete file/directory");
			}
		}
	}

	@Override
	public boolean exists( final File ioFile) throws FileServiceException {
		return ioFile.exists();
	}

	@Override
	public boolean isFile( final File ioFile) throws FileServiceException {
		return ioFile.isFile();
	}

	@Override
	public boolean isDirectory( final File ioFile) throws FileServiceException {
		return ioFile.isDirectory();
	}

	@Override
	public long getSize( final File ioFile) throws FileServiceException {
		final File f = ioFile;
		if (!f.exists()) {
			throw new IllegalArgumentException(f + " does not exist");
		}
		if (f.isDirectory()) {
			return sizeOfDirectory(f);
		}
		return f.length();
	}

	@Override
	public URL getParent( final File ioFile) throws FileServiceException {
		try {
			return ioFile.getParentFile().toURI().toURL();
		} catch (final MalformedURLException e) {
			throw new FileServiceException("cannot construct file url", e);
		}
	}

	@Override
	public String getName( final File ioFile) throws FileServiceException {
		return ioFile.getName();
	}

	private long sizeOfDirectory(final File aDirectory) {
		final File[] files = aDirectory.listFiles();
		if (files == null) {
			return 0;
		}
		long size = 0;
		for (final File file : files) {
			if (file.isDirectory()) {
				size += sizeOfDirectory(file);
			} else {
				size += file.length();
			}
			if (size < 0) {
				break;
			}
		}
		return size;
	}

	public static String decodePath(final URL aPath) throws FileServiceException {
		try {
			return URLDecoder.decode(aPath.getPath(), "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			throw new FileServiceException("cannot decode path", e);
		}
	}
}
